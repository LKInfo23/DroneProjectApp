package de.grb.droneproject.networking;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class DroneCommunicator {

    private final String host;
    private final int port;
    private DatagramSocket droneSocket;
    private long lastSent = System.currentTimeMillis();

    private boolean connected = false;

    private Context appContext;

    /**
     * This class represents the connection to the drone. It may be used to send and receive data from and to the drone.
     *
     * @param host The host of the drone/where to connect to.
     * @param port The port to connect to. Note: this is UDP.
     * @throws SocketException should the creation of the DatagramSocket fail
     */
    public DroneCommunicator(String host, int port) {
        this.host = host;
        this.port = port;
        connectToDrone();
        // this is technically not the way according to java convention, but it is more readable
        new Thread(() -> {
            while (true) {
                if (connected && (System.currentTimeMillis() - lastSent) > 3000) {
                    if (droneSocket.isConnected()) {
                        send("battery?");
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public DroneCommunicator(String host, int port, Context appContext) {
        this(host, port);
        this.appContext = appContext;
    }

    public void connectToDrone() {

        if (connected && droneSocket.isConnected()) return;
        try {
            droneSocket = new DatagramSocket(8890);
            droneSocket.connect(InetAddress.getByName(this.host), this.port);
            connected = droneSocket.isConnected();
        } catch (SocketException | UnknownHostException e) {
            connected = false;
        }
        send("command");
    }

    /**
     * This function sends a message to the drone. Keep in mind this method just sends the message and won't return
     * any response. For that please use {@link #sendAndReceive(String)}
     *
     * @param message The message that should be sent to the drone.
     */
    public void send(String message) {
        try {
            DatagramPacket dp = new DatagramPacket(message.getBytes(StandardCharsets.UTF_8), message.length(), InetAddress.getByName(host), port);
            droneSocket.send(dp);

            lastSent = System.currentTimeMillis();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function sends a message to the drone and returns the response if given.
     *
     * @param message The message that should be sent to the drone.
     * @return The response sent by the drone.
     */
    public String sendAndReceive(String message) {
        send(message);
        return receive();
    }

    /**
     * This method returns the first new response from the drone. Should mostly be used for debugging etc.
     * Please use {@link #sendAndReceive(String)} mostly
     *
     * @return The next response from the drone.
     */
    private String receive() {
        byte[] buffer = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        try {
            droneSocket.receive(dp);
            return new String(dp.getData(), 0, dp.getLength(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isConnected() {
        if (droneSocket == null) return false;
        return droneSocket.isConnected();
    }

    private String getCurrentSSID() {
        WifiManager wifiManager = (WifiManager) appContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo().getSSID();
        }
        return null;
    }

}
