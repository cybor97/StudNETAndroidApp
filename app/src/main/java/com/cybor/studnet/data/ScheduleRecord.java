package com.cybor.studnet.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

import static com.cybor.studnet.Utils.getCurrentTime;

public class ScheduleRecord extends RealmObject {
    @PrimaryKey
    private int id;
    @Expose(serialize = false, deserialize = false)
    private Schedule schedule;
    private String scheduleRecordName;
    private int weekday;
    private int isEven;
    @SerializedName("scheduleRecordStartTime")
    private String _scheduleRecordStartTime;
    private String auditoryNumber;
    @Ignore
    private transient DateTime scheduleRecordStartTime;

    public static ScheduleRecord getCurrent(List<ScheduleRecord> scheduleRecords, Configuration configuration) {
        Duration lessonDuration = configuration.getLessonDuration();
        for (ScheduleRecord current : scheduleRecords)
            if (current.isCurrent(lessonDuration) || current.isCurrentBreak(lessonDuration, configuration.getBreakDuration()))
                return current;
        return null;
    }

    //region Getters and setters
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
        return isEven == 1;
    }

    public ScheduleRecord setEven(boolean even) {
        Realm.getDefaultInstance().executeTransaction(realm -> isEven = even ? 1 : 0);
        return this;
    }

    public String getAuditoryNumber() {
        return auditoryNumber;
    }

    public ScheduleRecord setAuditoryNumber(String auditoryNumber) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.auditoryNumber = auditoryNumber);
        return this;
    }

    public DateTime getStartTime() {
        if (scheduleRecordStartTime == null && _scheduleRecordStartTime != null)
            scheduleRecordStartTime = DateTime.parse(_scheduleRecordStartTime,
                    //FIXME:Weak place(as for optimization). Replace with static field.
                    new DateTimeFormatterBuilder()
                            .appendHourOfDay(1)
                            .appendLiteral(':')
                            .appendMinuteOfHour(1)
                            .toFormatter());
        return scheduleRecordStartTime;
    }

    public ScheduleRecord setStartTime(DateTime startTime) {
        this.scheduleRecordStartTime = startTime;
        this._scheduleRecordStartTime = startTime == null ? null :
                String.format("%s:%s", startTime.getHourOfDay(), startTime.getMinuteOfHour());
        return this;
    }

    //endregion

    public DateTime getEndTime(Duration lessonDuration) {
        return getStartTime().plus(lessonDuration);
    }

    public boolean isCurrent(Duration lessonDuration) {
        DateTime nowTime = getCurrentTime();
        return getStartTime().isBefore(nowTime) && getEndTime(lessonDuration).isAfter(nowTime);
    }

    public boolean isCurrentBreak(Duration lessonDuration, Duration breakDuration) {
        DateTime endTime = getEndTime(lessonDuration);
        DateTime nowTime = getCurrentTime();
        return endTime.isBefore(nowTime) && endTime.plus(breakDuration).isAfter(nowTime);
    }
}
