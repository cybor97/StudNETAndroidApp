package com.cybor.studnet;

public interface APIResponseHandler {
    void onSuccess(int requestId, int statusCode, String response);

    void onError(int requestId, int statusCode, String response, Throwable error);
}
