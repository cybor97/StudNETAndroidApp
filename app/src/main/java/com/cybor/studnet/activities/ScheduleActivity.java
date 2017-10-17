package com.cybor.studnet.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.cybor.studnet.LessonsAdapter;
import com.cybor.studnet.R;
import com.cybor.studnet.data.Lesson;

import io.realm.Realm;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);
        ((ListView) findViewById(R.id.lessons_lv))
                .setAdapter(new LessonsAdapter(this, Realm.getDefaultInstance()
                        .where(Lesson.class)
                        .findAll()));
    }

    @Override
    public void onClick(View view) {

    }
}
