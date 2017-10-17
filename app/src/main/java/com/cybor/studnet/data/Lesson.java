package com.cybor.studnet.data;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Lesson extends RealmObject {
    public long _startTime = -1;
    public long _breakDuration = -1;
    @PrimaryKey
    private int number;
    //TODO:Implement weekday
    private String lessonName;
    private String auditory;
    @Ignore
    private DateTime startTime;
    @Ignore
    private Duration breakDuration;

    public static Lesson getCurrent(List<Lesson> lessons, Configuration configuration) {
        Duration lessonDuration = configuration.getLessonDuration();
        for (Lesson lesson : lessons)
            if (lesson.isCurrent(lessonDuration) || lesson.isCurrentBreak(lessonDuration))
                return lesson;
        return null;
    }

    public static void generateLessons(final int count, Configuration configuration, Realm realm) {
        Duration breakDuration = configuration.getBreakDuration();
        for (int i = 0; i < count; i++) {
            final int i1 = i;
            Lesson lesson = realm.where(Lesson.class).equalTo("number", i1 + 1).findFirst();
            if (lesson == null)
                lesson = new Lesson().setNumber(i1 + 1);
            final Lesson _lesson = lesson;

            realm.executeTransaction(_realm -> _realm.copyToRealmOrUpdate(_lesson
                    .setStartTime(configuration.getLessonsBeginTime()
                            .plus(configuration.getLessonDuration().multipliedBy(i1))
                            .plus(i1 >= configuration.getLongBreakAfter() + 1 ?
                                    breakDuration.multipliedBy(i1 - 1)
                                            .plus(breakDuration.multipliedBy(configuration.getLongBreakCoefficient())) :
                                    breakDuration.multipliedBy(i1))))
                    .setBreakDuration(configuration.getBreakDuration()));
        }
    }

    public int getNumber() {
        return number;
    }

    public Lesson setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getLessonName() {
        return lessonName;
    }

    public Lesson setLessonName(String lessonName) {
        this.lessonName = lessonName;
        return this;
    }

    public String getAuditory() {
        return auditory;
    }

    public Lesson setAuditory(String auditory) {
        this.auditory = auditory;
        return this;
    }

    public DateTime getStartTime() {
        if (startTime == null && _startTime != -1)
            startTime = new DateTime(_startTime);
        return startTime;
    }

    public Lesson setStartTime(DateTime startTime) {
        this.startTime = startTime;
        this._startTime = startTime == null ? -1 : startTime.getMillis();
        return this;
    }

    public Duration getBreakDuration() {
        if (breakDuration == null && _breakDuration != -1)
            breakDuration = new Duration(_breakDuration);
        return breakDuration;
    }

    public Lesson setBreakDuration(Duration breakDuration) {
        this.breakDuration = breakDuration;
        this._breakDuration = breakDuration == null ? -1 : breakDuration.getMillis();
        return this;
    }

    public DateTime getEndTime(Duration lessonDuration) {
        return getStartTime().plus(lessonDuration);
    }

    public boolean isCurrent(Duration lessonDuration) {
        DateTime nowTime = DateTime.now(DateTimeZone.forTimeZone(Calendar.getInstance().getTimeZone())).withYear(1970).withMonthOfYear(1).withDayOfMonth(1).plusHours(1);
        return getStartTime().isBefore(nowTime) && getEndTime(lessonDuration).isAfter(nowTime);
    }

    public boolean isCurrentBreak(Duration lessonDuration) {
        DateTime endTime = getEndTime(lessonDuration);
        DateTime nowTime = DateTime.now(DateTimeZone.forTimeZone(Calendar.getInstance().getTimeZone())).withYear(1970).withMonthOfYear(1).withDayOfMonth(1).plusHours(1);
        return endTime.isBefore(nowTime) && endTime.plus(breakDuration).isAfter(nowTime);
    }
}