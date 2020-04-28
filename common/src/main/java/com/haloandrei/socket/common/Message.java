package com.haloandrei.socket.common;


import java.io.Serializable;

public class Message<T> implements Serializable {
    public static final int PORT = 5431;
    public static final String HOST = "localhost";

    private String header;
    private T body;
    private String error;

    public Message() {
    }

    public Message(String header, T body) {
        this.header = header;
        this.body = body;
    }

    public Message(String header, T body, String error) {
        this.header = header;
        this.error = error;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Message{" +
                "header='" + header + '\'' +
                ", body=" + body +
                ", error='" + error + '\'' +
                '}';
    }
}
