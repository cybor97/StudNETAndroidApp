package com.cybor.studnet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cybor.studnet.activities.EditLessonActivity;
import com.cybor.studnet.data.Configuration;
import com.cybor.studnet.data.Lesson;

import org.joda.time.format.DateTimeFormat;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;


public class LessonsAdapter extends ArrayAdapter<Lesson> {
    private List<Lesson> lessons;

    public LessonsAdapter(@NonNull Context context, @NonNull Lesson[] lessons) {
        super(context, R.layout.lesson_vh, lessons);
        this.lessons = Arrays.asList(lessons);
    }

    public LessonsAdapter(@NonNull Context context, @NonNull List<Lesson> lessons) {
        super(context, R.layout.lesson_vh, lessons);
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Context context = getContext();
        if (convertView == null)
            convertView = View.inflate(context, R.layout.lesson_vh, null);

        Lesson lesson = lessons.get(position);
        ((TextView) convertView.findViewById(R.id.number_tv))
                .setText(context.getString(R.string.n_lesson).replace("{LESSON_NUMBER}", String.format("%s", position + 1)));

        String auditory = lesson.getAuditory();
        TextView auditoryTV = convertView.findViewById(R.id.auditory_tv);
        auditoryTV.setText(auditory == null || auditory.isEmpty() ? context.getString(R.string.auditory_blank) : auditory);

        String lessonName = lesson.getLessonName();
        TextView lessonNameTV = convertView.findViewById(R.id.name_tv);
        lessonNameTV.setText(lessonName == null || lessonName.isEmpty() ? context.getString(R.string.lesson_name_blank) : lessonName);

        ((TextView) convertView.findViewById(R.id.lesson_start_time_tv))
                .setText(lesson.getStartTime().toString(DateTimeFormat.mediumTime()));

        ((TextView) convertView.findViewById(R.id.lesson_end_time_tv))
                .setText(lesson.getEndTime(Configuration
                        .getInstance(Realm.getDefaultInstance())
                        .getLessonDuration()).toString(DateTimeFormat.mediumTime()));

        convertView.setOnClickListener(v -> context.startActivity(new Intent(context, EditLessonActivity.class)
                .putExtra("number", lesson.getNumber())));
        return convertView;
    }
}
