package com.cybor.studnet.data;

import io.realm.Realm;
import io.realm.RealmObject;

public class Conversation extends RealmObject {
    //CST = ConverSation Type
    public static final int CST_BASE = 0, CST_WALL = 1;

    private int id;
    private UniGroup group;
    private int conversationType;
    private String title;

    public int getId() {
        return id;
    }

    public Conversation setId(int id) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.id = id);
        return this;
    }

    public UniGroup getGroup() {
        return group;
    }

    public Conversation setGroup(UniGroup group) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.group = group);
        return this;
    }

    public int getConversationType() {
        return conversationType;
    }

    public Conversation setConversationType(int conversationType) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.conversationType = conversationType);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Conversation setTitle(String title) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.title = title);
        return this;
    }
}
