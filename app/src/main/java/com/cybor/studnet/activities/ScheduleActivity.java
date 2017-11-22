package com.cybor.studnet.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cybor.studnet.R;
import com.cybor.studnet.ScheduleAdapter;
import com.cybor.studnet.Utils;
import com.cybor.studnet.data.ScheduleRecord;

import org.joda.time.DateTime;

import io.realm.Realm;

public class ScheduleActivity extends BaseActivity implements View.OnClickListener {
    private ListView lessonsLV;
    private TextView dateTV;
    private DateTime date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        Button prevButton = findViewById(R.id.prev_button);
        prevButton.setOnClickListener(this);
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);

        lessonsLV = findViewById(R.id.lessons_lv);
        dateTV = findViewById(R.id.date_tv);

        date = DateTime.now();
        switchTo(date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prev_button:
                switchTo(date.minusDays(1));
                break;
            case R.id.next_button:
                switchTo(date.plusDays(1));
                break;
        }
    }

    private void switchTo(DateTime date) {
        lessonsLV.setAdapter(new ScheduleAdapter(this, Realm.getDefaultInstance()
                .where(ScheduleRecord.class)
                .equalTo("weekday", date.getDayOfWeek())
                .beginGroup()
                .equalTo("isEven", Utils.isEvenWeek(date) ? 1 : 0)
                .or()
                .equalTo("isEven", 2)
                .endGroup()
                .findAll()));
        dateTV.setText(date.toString("E, dd.MM.yyyy"));
        this.date = date;
    }
}
