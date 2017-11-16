package com.cybor.studnet.data;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class ScheduleRecord extends RealmObject {
    private int id;

    private Schedule schedule;
    private String scheduleRecordName;
    private boolean isEven;
    private long _scheduleRecordStartTime;
    private String auditory;
    @Ignore
    private DateTime scheduleRecordStartTime;

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
        return isEven;
    }

    public ScheduleRecord setEven(boolean even) {
        Realm.getDefaultInstance().executeTransaction(realm -> isEven = even);
        return this;
    }

    public String getAuditory() {
        return auditory;
    }

    public ScheduleRecord setAuditory(String auditory) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.auditory = auditory);
        return this;
    }

    public DateTime getStartTime() {
        if (scheduleRecordStartTime == null && _scheduleRecordStartTime != -1)
            scheduleRecordStartTime = new DateTime(_scheduleRecordStartTime);
        return scheduleRecordStartTime;
    }

    public ScheduleRecord setStartTime(DateTime startTime) {
        this.scheduleRecordStartTime = startTime;
        this._scheduleRecordStartTime = startTime == null ? -1 : startTime.getMillis();
        return this;
    }

    //endregion

    public DateTime getEndTime(Duration lessonDuration) {
        return getStartTime().plus(lessonDuration);
    }

    public boolean isCurrent(Duration lessonDuration) {
        DateTime nowTime = DateTime
                .now(DateTimeZone.forTimeZone(Calendar.getInstance().getTimeZone()))
                .withYear(1970).withMonthOfYear(1).withDayOfMonth(1).plusHours(1);
        return getStartTime().isBefore(nowTime) && getEndTime(lessonDuration).isAfter(nowTime);
    }

    public boolean isCurrentBreak(Duration lessonDuration, Duration breakDuration) {
        DateTime endTime = getEndTime(lessonDuration);
        DateTime nowTime = DateTime
                .now(DateTimeZone.forTimeZone(Calendar.getInstance().getTimeZone()))
                .withYear(1970).withMonthOfYear(1).withDayOfMonth(1).plusHours(1);
        return endTime.isBefore(nowTime) && endTime.plus(breakDuration).isAfter(nowTime);
    }

//    public static void generateLessons(final int count, Configuration configuration, Realm realm) {
//        Duration breakDuration = configuration.getBreakDuration();
//        for (int i = 0; i < count; i++) {
//            final int i1 = i;
//            Lesson lesson = realm.where(Lesson.class).equalTo("number", i1 + 1).findFirst();
//            if (lesson == null)
//                lesson = new Lesson().setNumber(i1 + 1);
//            final Lesson _lesson = lesson;
//
//            realm.executeTransaction(_realm -> _realm.copyToRealmOrUpdate(_lesson
//                    .setStartTime(configuration.getLessonsBeginTime()
//                            .plus(configuration.getLessonDuration().multipliedBy(i1))
//                            .plus(i1 >= configuration.getLongBreakAfter() + 1 ?
//                                    breakDuration.multipliedBy(i1 - 1)
//                                            .plus(breakDuration.multipliedBy(configuration.getLongBreakCoefficient())) :
//                                    breakDuration.multipliedBy(i1))))
//                    .setBreakDuration(configuration.getBreakDuration()));
//        }
//    }
}
