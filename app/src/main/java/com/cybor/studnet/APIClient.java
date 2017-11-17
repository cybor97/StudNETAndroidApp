package com.cybor.studnet;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

public class APIClient {

    private static APIClient instance;
    private AsyncHttpClient client;
    private Context context;

    private APIClient(Context context) {
        this.client = new AsyncHttpClient();
        this.context = context;

        client.setCookieStore(new PersistentCookieStore(context));
        client.setUserAgent("API/StudNET " + BuildConfig.VERSION_NAME);
    }

    public static void init(Context context) {
        instance = new APIClient(context);
    }

    public static APIClient getInstance() {
        return instance;
    }

    public void get(String url, RequestParams params, APIResponseHandler responseHandler) {
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int status, Header[] headers, String response, Throwable error) {
                responseHandler.onError(status, headers, response, error);
            }

            @Override
            public void onSuccess(int status, Header[] headers, String response) {
                responseHandler.onSuccess(status, headers, response);
            }
        });
    }

    public void post(String url, HttpEntity body, String contentType, APIResponseHandler responseHandler) {
        client.post(context, url, body, contentType, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int status, Header[] headers, String response, Throwable error) {
                responseHandler.onError(status, headers, response, error);
            }

            @Override
            public void onSuccess(int status, Header[] headers, String response) {
                responseHandler.onSuccess(status, headers, response);
            }
        });
    }

}
