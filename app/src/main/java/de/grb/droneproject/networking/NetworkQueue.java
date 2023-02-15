package de.grb.droneproject.networking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkQueue {

    private BlockingQueue<RequestResponse> queue = new LinkedBlockingQueue<>();

    public void add(RequestResponse requestResponse) {
        queue.add(requestResponse);
    }

    public RequestResponse take() throws InterruptedException {
        return queue.take();
    }

    public int size() {
        return queue.size();
    }


    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
