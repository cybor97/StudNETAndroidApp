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
import com.cybor.studnet.data.ScheduleRecord;

import org.joda.time.format.DateTimeFormat;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;


public class ScheduleAdapter extends ArrayAdapter<ScheduleRecord> {
    private List<ScheduleRecord> scheduleRecords;

    public ScheduleAdapter(@NonNull Context context, @NonNull ScheduleRecord[] lessons) {
        super(context, R.layout.lesson_vh, lessons);
        this.scheduleRecords = Arrays.asList(lessons);
    }

    public ScheduleAdapter(@NonNull Context context, @NonNull List<ScheduleRecord> lessons) {
        super(context, R.layout.lesson_vh, lessons);
        this.scheduleRecords = lessons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Context context = getContext();
        if (convertView == null)
            convertView = View.inflate(context, R.layout.lesson_vh, null);

        ScheduleRecord scheduleRecord = scheduleRecords.get(position);
        ((TextView) convertView.findViewById(R.id.number_tv))
                .setText(context.getString(R.string.n_lesson).replace("{LESSON_NUMBER}", String.format("%s", position + 1)));

        String auditory = scheduleRecord.getAuditoryNumber();
        TextView auditoryTV = convertView.findViewById(R.id.auditory_tv);
        auditoryTV.setText(auditory == null || auditory.isEmpty() ? context.getString(R.string.auditory_blank) : auditory);

        String lessonName = scheduleRecord.getScheduleRecordName();
        TextView lessonNameTV = convertView.findViewById(R.id.name_tv);
        lessonNameTV.setText(lessonName == null || lessonName.isEmpty() ? context.getString(R.string.lesson_name_blank) : lessonName);

        ((TextView) convertView.findViewById(R.id.lesson_start_time_tv))
                .setText(scheduleRecord.getStartTime().toString(DateTimeFormat.mediumTime()));

        ((TextView) convertView.findViewById(R.id.lesson_end_time_tv))
                .setText(scheduleRecord.getEndTime(Configuration
                        .getInstance(Realm.getDefaultInstance())
                        .getLessonDuration()).toString(DateTimeFormat.mediumTime()));

        convertView.setOnClickListener(v -> context.startActivity(new Intent(context, EditLessonActivity.class)
                .putExtra("id", scheduleRecord.getId())));
        return convertView;
    }
}
