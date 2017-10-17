package com.cybor.studnet.data;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Configuration extends RealmObject {
    private static Configuration instance = new Configuration();
    public long _lessonDuration = -1;
    public long _breakDuration = -1;
    public long _lessonsBeginTime = -1;
    private boolean topCollapsed;
    @Ignore
    private Duration lessonDuration;
    @Ignore
    private Duration breakDuration;
    @Ignore
    private DateTime lessonsBeginTime;

    private int longBreakCoefficient = 4;
    private int longBreakAfter = 2;

    public Configuration() {
    }

    public static Configuration getInstance(Realm realm) {
        if (realm.where(Configuration.class).count() == 0)
            realm.executeTransaction(_realm -> _realm.copyToRealm(instance = new Configuration()
                    .setLessonsBeginTime(new DateTime(1970, 1, 1, 8, 30))
                    .setLessonDuration(Duration.standardMinutes(90))
                    .setBreakDuration(Duration.standardMinutes(10))));
        else instance = realm.where(Configuration.class).findFirst();

        return instance;
    }

    public boolean isTopCollapsed() {
        return topCollapsed;
    }

    public Configuration setTopCollapsed(boolean topCollapsed) {
        this.topCollapsed = topCollapsed;
        return this;
    }

    public Duration getLessonDuration() {
        if (lessonDuration == null && _lessonDuration != -1)
            lessonDuration = new Duration(_lessonDuration);
        return lessonDuration;
    }

    public Configuration setLessonDuration(Duration lessonDuration) {
        this.lessonDuration = lessonDuration;
        this._lessonDuration = lessonDuration == null ? -1 : lessonDuration.getMillis();
        return this;
    }

    public Duration getBreakDuration() {
        if (breakDuration == null && _breakDuration != -1)
            breakDuration = new Duration(_breakDuration);
        return breakDuration;
    }

    public Configuration setBreakDuration(Duration breakDuration) {
        this.breakDuration = breakDuration;
        this._breakDuration = breakDuration == null ? -1 : breakDuration.getMillis();
        return this;
    }

    public DateTime getLessonsBeginTime() {
        if (lessonsBeginTime == null && _lessonsBeginTime != -1)
            lessonsBeginTime = new DateTime(_lessonsBeginTime);
        return lessonsBeginTime;
    }

    public Configuration setLessonsBeginTime(DateTime lessonsBeginTime) {
        this.lessonsBeginTime = lessonsBeginTime;
        this._lessonsBeginTime = lessonsBeginTime == null ? -1 : lessonsBeginTime.getMillis();
        return this;
    }

    public int getLongBreakCoefficient() {
        return longBreakCoefficient;
    }

    public Configuration setLongBreakCoefficient(int longBreakCoefficient) {
        this.longBreakCoefficient = longBreakCoefficient;
        return this;
    }

    public int getLongBreakAfter() {
        return longBreakAfter;
    }

    public Configuration setLongBreakAfter(int longBreakAfter) {
        this.longBreakAfter = longBreakAfter;
        return this;
    }
}
