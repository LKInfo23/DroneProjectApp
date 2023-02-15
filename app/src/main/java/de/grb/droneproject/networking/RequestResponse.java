package de.grb.droneproject.networking;

public class RequestResponse {

    private String request;
    private String response;

    private boolean hasResponded = false;

    public RequestResponse(String request, String response) {
        this.request = request;
        this.response = response;
        this.hasResponded = true;
    }

    public RequestResponse(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }


    public RequestResponse setResponse(String response) {
        this.response = response;
        this.hasResponded = true;
        return this;
    }
}
