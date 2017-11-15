package com.cybor.studnet.data;

import io.realm.Realm;
import io.realm.RealmObject;

public class UniGroup extends RealmObject {
    private int id;
    private int specialtyId;
    private String groupName;

    public int getId() {
        return id;
    }

    public UniGroup setId(int id) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.id = id);
        return this;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public UniGroup setSpecialtyId(int specialtyId) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.specialtyId = specialtyId);
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public UniGroup setGroupName(String groupName) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.groupName = groupName);
        return this;
    }
}
