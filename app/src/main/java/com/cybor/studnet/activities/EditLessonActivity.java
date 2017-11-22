package com.cybor.studnet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cybor.studnet.R;
import com.cybor.studnet.data.ScheduleRecord;

import io.realm.Realm;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class EditLessonActivity extends AppCompatActivity implements View.OnClickListener {
    private ScheduleRecord scheduleRecord;
    private TextView nameTV, auditoryTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_lesson_layout);
        getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);

        nameTV = findViewById(R.id.name_tv);
        auditoryTV = findViewById(R.id.auditory_tv);

        Intent intent = getIntent();
        scheduleRecord = Realm.getDefaultInstance()
                .where(ScheduleRecord.class)
                .equalTo("id", intent.getIntExtra("id", -1))
                .findFirst();
        if (scheduleRecord != null) {
            nameTV.setText(scheduleRecord.getScheduleRecordName());
            auditoryTV.setText(scheduleRecord.getAuditoryNumber());
        } else onBackPressed();

        findViewById(R.id.save_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save_button) {
            Realm.getDefaultInstance().executeTransaction(_realm -> {
                scheduleRecord.setScheduleRecordName(nameTV.getText().toString());
                scheduleRecord.setAuditoryNumber(auditoryTV.getText().toString());
            });
            onBackPressed();
        }
    }
}
