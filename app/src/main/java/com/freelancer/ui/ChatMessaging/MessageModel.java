package com.freelancer.ui.ChatMessaging;

/**
 * used to hold the attributes of a message
 */

public class MessageModel {
    private String msgId;
    private String senderId;
    private String message;

    /**
     * constructor to initialize values
     * @param msgId
     * @param senderId
     * @param message
     */
    public MessageModel(String msgId, String senderId, String message) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.message = message;
    }

    /**
     * default constructor
     */
    public MessageModel() {
    }

    /**
     * return messages id
     * @return
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * set the message ID
     * @param msgId
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * return sender ID
     * @return
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * set sender ID
     * @param senderId
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * return the message
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * set the message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
