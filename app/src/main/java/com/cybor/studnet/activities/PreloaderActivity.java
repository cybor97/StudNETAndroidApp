package com.cybor.studnet.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cybor.studnet.APIClient;
import com.cybor.studnet.APIResponseHandler;
import com.cybor.studnet.R;
import com.cybor.studnet.data.Configuration;
import com.loopj.android.http.RequestParams;

import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;
import io.realm.Realm;

public class PreloaderActivity extends Activity implements APIResponseHandler {
    private static final int LESSONS_COUNT = 7;
    private static final String HOST_URL = "http://192.168.0.106";
    private static final String AUTH_URL = HOST_URL + "/auth/signIn";
    private static final String SCHEDULE_URL = HOST_URL + "/data/getScheduleRecords";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preloader_layout);
        APIClient.init(getApplicationContext());

        Executors.newSingleThreadExecutor()
                .execute(() ->
                {
                    Realm.init(getApplicationContext());

                    Realm realm = Realm.getDefaultInstance();
                    Configuration configuration = Configuration.getInstance(realm);

                });
        APIClient.getInstance().get(SCHEDULE_URL,
                new RequestParams("scheduleId", 1, "weekday", 2, "isEven", 0), this);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String response) {

    }

    @Override
    public void onError(int statusCode, Header[] headers, String response, Throwable error) {

    }
}
