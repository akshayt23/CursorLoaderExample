package com.rubberduck.flatchatassignment;

/**
 * Created by akshayt on 05/05/15.
 */
public class Message {
    private int type;
    private String data;
    private String timestamp;

    public Message(int type, String data, String timestamp) {
        this.type = type;
        this.data = data;
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
