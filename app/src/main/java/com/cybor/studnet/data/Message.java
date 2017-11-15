package com.cybor.studnet.data;

import io.realm.Realm;
import io.realm.RealmObject;

public class Message extends RealmObject {
    private int id;

    private Account sender;
    private Account receiver;
    private Conversation conversation;
    private String messageText;
    private long messageDate;
    private int messageDateMillisecond;
    private boolean isNotification;
    private boolean seen;

    public int getId() {
        return id;
    }

    public Message setId(int id) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.id = id);
        return this;
    }

    public Account getSender() {
        return sender;
    }

    public Message setSender(Account sender) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.sender = sender);
        return this;
    }

    public Account getReceiver() {
        return receiver;
    }

    public Message setReceiver(Account receiver) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.receiver = receiver);
        return this;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public Message setConversation(Conversation conversation) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.conversation = conversation);
        return this;
    }

    public String getMessageText() {
        return messageText;
    }

    public Message setMessageText(String messageText) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.messageText = messageText);
        return this;
    }

    public long getMessageDate() {
        return messageDate;
    }

    public Message setMessageDate(long messageDate) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.messageDate = messageDate);
        return this;
    }

    public int getMessageDateMillisecond() {
        return messageDateMillisecond;
    }

    public Message setMessageDateMillisecond(int messageDateMillisecond) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.messageDateMillisecond = messageDateMillisecond);
        return this;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public Message setNotification(boolean notification) {
        Realm.getDefaultInstance().executeTransaction(realm -> isNotification = notification);
        return this;
    }

    public boolean isSeen() {
        return seen;
    }

    public Message setSeen(boolean seen) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.seen = seen);
        return this;
    }
}
