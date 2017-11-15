package com.cybor.studnet.data;

import io.realm.Realm;
import io.realm.RealmObject;

public class Schedule extends RealmObject {
    //SCS = Schedule Current State
    private static final int SCS_SANDBOXED = 0x40, SCS_PUBLISHED = 0x80, SCS_PRIMARY = 0x100;

    private int id;

    private UniGroup group;
    private String scheduleName;
    private int currentState;

    public int getId() {
        return id;
    }

    public Schedule setId(int id) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.id = id);
        return this;
    }

    public UniGroup getGroup() {
        return group;
    }

    public Schedule setGroup(UniGroup group) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.group = group);
        return this;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public Schedule setScheduleName(String scheduleName) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.scheduleName = scheduleName);
        return this;
    }

    public int getCurrentState() {
        return currentState;
    }

    public Schedule setCurrentState(int currentState) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.currentState = currentState);
        return this;
    }
}
