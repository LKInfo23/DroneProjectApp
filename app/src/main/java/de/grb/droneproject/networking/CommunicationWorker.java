package de.grb.droneproject.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class CommunicationWorker {

    private String host;
    private int port;
    private DatagramSocket droneSocket;

    public CommunicationWorker(String host, int port) {
        this.host = host;
        this.port = port;
        connect();
    }

    public void connect() {
        if (droneSocket == null) {
            try {
                droneSocket = new DatagramSocket(8890);
                droneSocket.setReuseAddress(true);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        CommunicationManager.getInstance().addRequest("command");
    }

    public void receiver() {
        Thread receiver = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    try {
                        droneSocket.receive(packet);
                        String message = new String(buffer, 0, packet.getLength());
                        System.out.println("Received: " + message);
                        CommunicationManager.getInstance().addResponse(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sender() {
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (CommunicationManager.getInstance().getSendQueue().isEmpty()) continue;
                    try {
                        RequestResponse requestResponse = CommunicationManager.getInstance().getSendQueue().take();
                        byte[] buffer = requestResponse.getRequest().getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(host), port);
                        droneSocket.send(packet);
                        System.out.println("Sent: " + requestResponse.getRequest());
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void disconnect() {
        if (droneSocket != null) {
            droneSocket.disconnect();
            droneSocket.close();
            droneSocket = null;
        }
    }


}
