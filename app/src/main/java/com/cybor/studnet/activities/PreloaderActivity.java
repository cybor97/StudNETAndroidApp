package com.cybor.studnet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cybor.studnet.R;
import com.cybor.studnet.data.Configuration;
import com.cybor.studnet.data.Lesson;

import java.util.concurrent.Executors;

import io.realm.Realm;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PreloaderActivity extends Activity {
    private static final int LESSONS_COUNT = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preloader_layout);

        Executors.newSingleThreadExecutor()
                .execute(() ->
                {
                    Realm.init(getApplicationContext());

                    Realm realm = Realm.getDefaultInstance();
                    //if (realm.where(Lesson.class).count() == 0)
                    Lesson.generateLessons(LESSONS_COUNT, Configuration.getInstance(realm), realm);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(this, MainActivity.class)
                            .addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                });
    }
}
