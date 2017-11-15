package com.cybor.studnet.data;

import io.realm.Realm;
import io.realm.RealmObject;

public class ScheduleRecord extends RealmObject {
    private int id;

    private Schedule schedule;
    private String scheduleRecordName;
    private boolean isEven;
    private int scheduleRecordStartTime;
    private String auditory;

    public int getId() {
        return id;
    }

    public ScheduleRecord setId(int id) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.id = id);
        return this;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public ScheduleRecord setSchedule(Schedule schedule) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.schedule = schedule);
        return this;
    }

    public String getScheduleRecordName() {
        return scheduleRecordName;
    }

    public ScheduleRecord setScheduleRecordName(String scheduleRecordName) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.scheduleRecordName = scheduleRecordName);
        return this;
    }

    public boolean isEven() {
        return isEven;
    }

    public ScheduleRecord setEven(boolean even) {
        Realm.getDefaultInstance().executeTransaction(realm -> isEven = even);
        return this;
    }

    public int getScheduleRecordStartTime() {
        return scheduleRecordStartTime;
    }

    public ScheduleRecord setScheduleRecordStartTime(int scheduleRecordStartTime) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.scheduleRecordStartTime = scheduleRecordStartTime);
        return this;
    }

    public String getAuditory() {
        return auditory;
    }

    public ScheduleRecord setAuditory(String auditory) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.auditory = auditory);
        return this;
    }
}
