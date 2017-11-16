package com.cybor.studnet.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cybor.studnet.R;
import com.cybor.studnet.data.Configuration;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import io.realm.Realm;

public class PreloaderActivity extends Activity {
    private static final int LESSONS_COUNT = 7;
    private static final String HOST_URL = "http://192.168.0.106";
    private static final String AUTH_URL = HOST_URL + "/auth/signIn";
    private static final String SCHEDULE_URL = HOST_URL + "/data/getScheduleRecords";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preloader_layout);

        AsyncHttpResponseHandler authResponseHandler = new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

            }
        };
        JsonHttpResponseHandler scheduleResponseHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFinish() {
//                            startActivity(new Intent(PreloaderActivity.this, MainActivity.class)
//                                    .addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP));
                super.onFinish();
//                            PreloaderActivity.this.finish();
            }
        };
        SyncHttpClient client = new SyncHttpClient();

        Executors.newSingleThreadExecutor()
                .execute(() ->
                {
                    Realm.init(getApplicationContext());

                    Realm realm = Realm.getDefaultInstance();
                    Configuration configuration = Configuration.getInstance(realm);
                    //if (realm.where(Lesson.class).count() == 0)
                    //TODO:Implement data fetch
                    try {
                        client.post(PreloaderActivity.this, AUTH_URL,
                                new StringEntity("{\"userName\":\"Cybor\", \"password\":\"unknown0\"}"),
                                "application/json", authResponseHandler);
                    } catch (UnsupportedEncodingException e) {
                    }

                    client.get(SCHEDULE_URL, new RequestParams(
                            "scheduleId", 1, "weekday", 2, "isEven", 0), scheduleResponseHandler);
                });
    }
}
