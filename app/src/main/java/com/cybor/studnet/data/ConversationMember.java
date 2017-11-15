package com.cybor.studnet.data;

import io.realm.Realm;
import io.realm.RealmObject;

public class ConversationMember extends RealmObject {
    private int id;

    private Account account;
    private Conversation conversation;
    private boolean readOnly;

    public int getId() {
        return id;
    }

    public ConversationMember setId(int id) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.id = id);
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public ConversationMember setAccount(Account account) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.account = account);
        return this;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public ConversationMember setConversation(Conversation conversation) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.conversation = conversation);
        return this;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public ConversationMember setReadOnly(boolean readOnly) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.readOnly = readOnly);
        return this;
    }
}
