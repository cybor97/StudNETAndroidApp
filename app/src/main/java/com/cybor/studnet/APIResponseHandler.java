package com.cybor.studnet;

import cz.msebera.android.httpclient.Header;

public interface APIResponseHandler {
    void onSuccess(int statusCode, Header[] headers, String response);

    void onError(int statusCode, Header[] headers, String response, Throwable error);
}
