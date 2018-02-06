package com.gxecard.customerservice.entity;


import java.util.regex.Pattern;

public class MessageHead {

    public static final int MESSAGE_HEAD_SIZE = 7;
    public static final Pattern PATTERN_MESSAGE_TYPE = Pattern.compile("[A-Fa-f0-9]{4}");

    private String MessageType;
    private byte[] version;
    private byte[] encryption;
    private int bodyLength;

    public MessageHead(String messageType, int bodyLength) {
        MessageType = messageType;
        // this.version = version;
        // this.encryption = encryption;
        this.bodyLength = bodyLength;
    }

    public MessageHead(String messageType, int bodyLength, byte[] version, byte[] encryption) {
        MessageType = messageType;
        this.version = version;
        this.encryption = encryption;
        this.bodyLength = bodyLength;
    }


    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public byte[] getVersion() {
        return version;
    }

    public void setVersion(byte[] version) {
        this.version = version;
    }

    public byte[] getEncryption() {
        return encryption;
    }

    public void setEncryption(byte[] encryption) {
        this.encryption = encryption;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }
}
