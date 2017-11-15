package com.cybor.studnet.data;

import io.realm.Realm;
import io.realm.RealmObject;

public class Account extends RealmObject {
    //AL = Access Level
    private static final int AL_CHANGE_DENIAL = 0x1,
            AL_GUEST = 0x2, AL_STUDENT = 0x4, AL_GROUP_HEAD = 0x8, AL_EDITOR = 0x10, AL_ADMIN = 0x20;

    private long id;
    private UniGroup group;
    private byte[] profileImage;
    private String userName;
    //private String password... but it's unsafe to store them out of server's DB.
    private String fullName;
    private long birthDate;
    private int applyYear;
    private boolean approved;
    private int accessLevel;
    private int newAccessLevel;
    private int rating;

    public long getId() {
        return id;
    }

    public Account setId(long id) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.id = id);
        return this;
    }

    public UniGroup getGroup() {
        return group;
    }

    public Account setGroup(UniGroup group) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.group = group);
        return this;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public Account setProfileImage(byte[] profileImage) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.profileImage = profileImage);
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Account setUserName(String userName) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.userName = userName);
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Account setFullName(String fullName) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.fullName = fullName);
        return this;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public Account setBirthDate(long birthDate) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.birthDate = birthDate);
        return this;
    }

    public int getApplyYear() {
        return applyYear;
    }

    public Account setApplyYear(int applyYear) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.applyYear = applyYear);
        return this;
    }

    public boolean isApproved() {
        return approved;
    }

    public Account setApproved(boolean approved) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.approved = approved);
        return this;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public Account setAccessLevel(int accessLevel) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.accessLevel = accessLevel);
        return this;
    }

    public int getNewAccessLevel() {
        return newAccessLevel;
    }

    public Account setNewAccessLevel(int newAccessLevel) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.newAccessLevel = newAccessLevel);
        return this;
    }

    public int getRating() {
        return rating;
    }

    public Account setRating(int rating) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.rating = rating);
        return this;
    }
}
