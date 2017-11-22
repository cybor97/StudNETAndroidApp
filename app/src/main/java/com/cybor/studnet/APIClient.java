package com.cybor.studnet;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;

public class APIClient {

    public static final String HOST_URL = "http://192.168.0.106";
    public static final String SIGN_IN = "/auth/signIn";
    public static final String SIGN_UP = "/auth/signUp";
    public static final String GET_SCHEDULE = "/data/getScheduleRecords";
    private static APIClient instance;
    private CookieStore cookieStore;
    private AsyncHttpClient client;
    private Gson gson;

    private APIClient(Context context) {
        this.client = new AsyncHttpClient();

        cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
        client.setUserAgent("API/StudNET/AndroidApp/" + BuildConfig.VERSION_NAME);
        client.setMaxRetriesAndTimeout(10, 500);
        gson = new GsonBuilder().serializeNulls().create();
    }

    public static APIClient init(Context context) {
        return instance = new APIClient(context);
    }

    public static APIClient getInstance() {
        return instance;
    }

    public Gson getGson() {
        return gson;
    }

    public boolean hasAccessToken() {
        for (Cookie current : cookieStore.getCookies())
            if (current.getName().equals("token"))
                return true;
        return false;
    }

    public void get(int requestId, String method, RequestParams params, APIResponseHandler responseHandler) {
        client.get(HOST_URL + method, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int status, Header[] headers, String response, Throwable error) {
                responseHandler.onError(requestId, status, response, error);
            }

            @Override
            public void onSuccess(int status, Header[] headers, String response) {
                responseHandler.onSuccess(requestId, status, response);
            }
        });
    }

    public void post(Context context, int requestId, String method, HttpEntity body, APIResponseHandler responseHandler) {
        client.post(context, HOST_URL + method, body, body.getContentType().getValue(),
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int status, Header[] headers, String response, Throwable error) {
                        responseHandler.onError(requestId, status, response, error);
                    }

                    @Override
                    public void onSuccess(int status, Header[] headers, String response) {
                        responseHandler.onSuccess(requestId, status, response);
                    }
                });
    }

    public void getSchedule(int requestId, APIResponseHandler callback) {
        get(requestId, GET_SCHEDULE, new RequestParams("scheduleId", 1), callback);
    }

}
