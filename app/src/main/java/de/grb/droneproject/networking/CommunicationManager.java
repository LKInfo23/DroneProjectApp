package de.grb.droneproject.networking;

public class CommunicationManager {

    /**
     * need:
     * 1. thread to receive packets
     * 2. thread to send packets
     * 3. thread to display battery status
     *
     * structure:
     * 1. queue to send packets
     * 2. queue for send packets, to add the response to
     * 3. queue for finished packets
     *
     */

    private NetworkQueue sendQueue;
    private NetworkQueue receiveQueue;
    private NetworkQueue finishedQueue;

    public static CommunicationManager instance;

    public static CommunicationManager getInstance() {
        if (instance == null) {
            instance = new CommunicationManager();
        }
        return instance;
    }

    private CommunicationManager() {
        sendQueue = new NetworkQueue();
        receiveQueue = new NetworkQueue();
        finishedQueue = new NetworkQueue();
    }

    public void addRequest(RequestResponse requestResponse) {
        sendQueue.add(requestResponse);
    }

    public void addRequest(String request) {
        sendQueue.add(new RequestResponse(request));
    }

    public void addResponse(String response) {
        try {
            receiveQueue.add(sendQueue.take().setResponse(response));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public NetworkQueue getSendQueue() {
        return sendQueue;
    }

    public NetworkQueue getReceiveQueue() {
        return receiveQueue;
    }

    public NetworkQueue getFinishedQueue() {
        return finishedQueue;
    }
}
