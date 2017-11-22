package com.cybor.studnet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cybor.studnet.APIClient;
import com.cybor.studnet.APIResponseHandler;
import com.cybor.studnet.R;
import com.cybor.studnet.data.Configuration;
import com.cybor.studnet.data.ScheduleRecord;
import com.loopj.android.http.RequestParams;

import java.util.Arrays;
import java.util.concurrent.Executors;

import io.realm.Realm;

import static com.cybor.studnet.APIClient.GET_SCHEDULE;

public class PreloaderActivity extends Activity implements APIResponseHandler {
    private APIClient apiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preloader_layout);
        apiClient = APIClient.init(getApplicationContext());

        Executors.newSingleThreadExecutor()
                .execute(() ->
                {
                    Realm.init(getApplicationContext());

                    Realm realm = Realm.getDefaultInstance();
                    Configuration.getInstance(realm);

                });
        apiClient.get(0, GET_SCHEDULE,
                new RequestParams("scheduleId", 1), this);
    }

    @Override
    public void onSuccess(int requestId, int statusCode, String response) {
        if (requestId == 0 && statusCode == 200) {
            Realm.getDefaultInstance().executeTransaction(_realm -> _realm
                    .copyToRealmOrUpdate(Arrays.asList(apiClient.getGson().fromJson(response, ScheduleRecord[].class))));
            startActivity(new Intent(this, MainActivity.class)
                    .putExtra("upToDate", true));
        }
    }

    @Override
    public void onError(int requestId, int statusCode, String response, Throwable error) {
        if (statusCode == 403)
            startActivity(new Intent(this, AuthActivity.class));
    }
}
