package com.cybor.studnet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cybor.studnet.R;
import com.cybor.studnet.data.Configuration;
import com.cybor.studnet.data.Lesson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import io.realm.Realm;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private View lessonDurationExpandedContainer, buttonsContainer;
    private ImageView topPanelCollapseButton, buttonsPanelCollapseButton;
    private TextView lessonDurationCollapsedTV, currentLessonTV, timeToEndTV, buttonsCollapsedTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        topPanelCollapseButton = (ImageView) findViewById(R.id.top_panel_collapse_button);
        topPanelCollapseButton.setOnClickListener(this);

        buttonsPanelCollapseButton = (ImageView) findViewById(R.id.buttons_panel_collapse_button);
        buttonsPanelCollapseButton.setOnClickListener(this);

        lessonDurationExpandedContainer = findViewById(R.id.lesson_duration_expanded_container);
        lessonDurationCollapsedTV = (TextView) findViewById(R.id.lesson_duration_collapsed_tv);
        lessonDurationCollapsedTV.setOnClickListener(this);

        buttonsContainer = findViewById(R.id.buttons_container);
        buttonsCollapsedTV = (TextView) findViewById(R.id.buttons_collapsed_tv);
        buttonsCollapsedTV.setOnClickListener(this);

        currentLessonTV = (TextView) findViewById(R.id.current_lesson_tv);
        timeToEndTV = (TextView) findViewById(R.id.time_to_end_tv);

        findViewById(R.id.messenger_button).setOnClickListener(this);
        findViewById(R.id.schedule_button).setOnClickListener(this);
        findViewById(R.id.settings_button).setOnClickListener(this);

        final String shortState = getString(R.string.short_state);
        final String nLesson = getString(R.string.n_lesson);
        final String timeToEndStr = getString(R.string.time_to_end);
        final String lessonBreakStr = getString(R.string.lesson_break);
        final String lessonStr = getString(R.string.lesson);

        Executors.newSingleThreadExecutor()
                .execute(() ->
                {

                    PeriodFormatter shortFormatter = new PeriodFormatterBuilder()
                            .printZeroAlways()
                            .appendHours()
                            .appendSeparator(":")
                            .appendMinutes()
                            .toFormatter();
                    PeriodFormatter formatter = new PeriodFormatterBuilder()
                            .appendHours()
                            .appendSeparator(":")
                            .appendMinutes()
                            .appendSeparator(":")
                            .appendSeconds()
                            .toFormatter();

                    Realm _realm = Realm.getDefaultInstance();
                    Configuration configuration = Configuration.getInstance(_realm);
                    Duration lessonDuration = configuration.getLessonDuration();
                    List<Lesson> lessons = _realm.where(Lesson.class).findAll();
                    while (!Thread.interrupted()) {
                        Lesson currentLesson = Lesson.getCurrent(lessons, configuration);
                        if (currentLesson == null) {
                            runOnUiThread(() -> {
                                if (lessonDurationExpandedContainer.getVisibility() == VISIBLE)
                                    collapseLessonStatePanel();
                                lessonDurationCollapsedTV.setText(getString(R.string.no_lesson));
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String lessonNumber = Integer.toString(currentLesson.getNumber() + 1);
                            Duration timeToEnd = new Duration(DateTime
                                    .now(DateTimeZone.forTimeZone(Calendar.getInstance().getTimeZone()))
                                    .withYear(1970)
                                    .withMonthOfYear(1)
                                    .withDayOfMonth(1)
                                    .plusHours(1),
                                    currentLesson.getEndTime(lessonDuration));

                            runOnUiThread(() -> {
                                if (lessonDurationCollapsedTV.getVisibility() == VISIBLE) {
                                    lessonDurationCollapsedTV.setText(shortState
                                            .replace("{TIME}", shortFormatter.print(timeToEnd.toPeriod()))
                                            .replace("{LESSON_NUMBER}", lessonNumber)
                                            .replace("{STATE}", getString(currentLesson.isCurrentBreak(lessonDuration) ?
                                                    R.string.lesson_break :
                                                    R.string.lesson)));
                                } else {
                                    currentLessonTV.setText(nLesson.replace("{LESSON_NUMBER}", lessonNumber));

                                    timeToEndTV.setText(timeToEndStr
                                            .replace("{TIME}", formatter.print(timeToEnd.toPeriod()))
                                            .replace("{STATE}", currentLesson.isCurrentBreak(lessonDuration) ?
                                                    lessonBreakStr : lessonStr));
                                }
                            });
                            try {
                                Thread.sleep(250);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.messenger_button:
                startActivity(new Intent(this, MessengerActivity.class));
                break;
            case R.id.schedule_button:
                startActivity(new Intent(this, ScheduleActivity.class));
                break;
            case R.id.settings_button:
                startActivity(new Intent(this, SettingsActivity.class));
                break;

            case R.id.top_panel_collapse_button:
            case R.id.lesson_duration_collapsed_tv:
                if (lessonDurationExpandedContainer.getVisibility() == VISIBLE)
                    collapseLessonStatePanel();
                else expandLessonStatePanel();
                break;
            case R.id.buttons_panel_collapse_button:
            case R.id.buttons_collapsed_tv:
                if (buttonsContainer.getTranslationY() == 0)
                    buttonsContainer.animate()
                            .translationY(buttonsContainer.getHeight() - buttonsPanelCollapseButton.getHeight())
                            .withEndAction(() -> buttonsPanelCollapseButton.setImageResource(R.mipmap.up_icon))
                            .start();
                else
                    buttonsContainer.animate()
                            .translationY(0)
                            .withEndAction(() -> buttonsPanelCollapseButton.setImageResource(R.mipmap.down_icon))
                            .start();
                break;
        }
    }

    void collapseLessonStatePanel() {
        lessonDurationExpandedContainer.animate()
                .alpha(0)
                .withEndAction(() -> {
                    lessonDurationExpandedContainer.setVisibility(GONE);
                    lessonDurationCollapsedTV.setVisibility(VISIBLE);
                    topPanelCollapseButton.setImageResource(R.mipmap.down_icon);
                });
    }

    void expandLessonStatePanel() {
        lessonDurationExpandedContainer.setVisibility(VISIBLE);
        lessonDurationCollapsedTV.setVisibility(GONE);
        lessonDurationExpandedContainer.animate()
                .alpha(1)
                .withEndAction(() -> topPanelCollapseButton.setImageResource(R.mipmap.up_icon))
                .start();
    }
}
