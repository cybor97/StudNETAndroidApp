package com.cybor.studnet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cybor.studnet.APIClient;
import com.cybor.studnet.APIResponseHandler;
import com.cybor.studnet.R;

import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_LONG;
import static com.cybor.studnet.APIClient.SIGN_IN;

public class AuthActivity extends BaseActivity implements View.OnClickListener,
        APIResponseHandler {
    private final int SIGN_UP_REQUEST = 0, SIGN_IN_REQUEST = 1;
    boolean signInMode;
    private TextView userNameET, passwordET, authButton, switchModeButton;
    private ViewGroup inputsContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_layout);

        userNameET = findViewById(R.id.user_name_et);
        passwordET = findViewById(R.id.password_et);
        inputsContainer = findViewById(R.id.inputs_container);
        authButton = findViewById(R.id.auth_button);

        findViewById(R.id.switch_mode_button).setOnClickListener(this);
        findViewById(R.id.auth_button).setOnClickListener(this);

        switchMode(true);
    }

    private void switchMode(boolean signInMode) {
        this.signInMode = signInMode;
        authButton.setText(signInMode ? R.string.sign_in : R.string.sign_up);
        int count = inputsContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View current = inputsContainer.getChildAt(i);
            if (current.getTag() == null || !current.getTag().equals("forSignIn"))
                current.setVisibility(signInMode ? GONE : VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_name_et:
                break;
            case R.id.password_et:
                break;
            case R.id.switch_mode_button:
                switchMode(!signInMode);
                break;
            case R.id.auth_button:
                if (signInMode)
                    APIClient.getInstance().post(this, SIGN_IN_REQUEST, SIGN_IN,
                            new StringEntity(String.format("{\"userName\":\"%s\", \"password\":\"%s\"}",
                                    userNameET.getText(), passwordET.getText()), ContentType.APPLICATION_JSON), this);
//                else
//                    APIClient.getInstance().post(this, SIGN_IN_REQUEST, SIGN_IN,
//                            new StringEntity(String.format("{\"userName\":\"%s\", \"password\":\"%s\"}",
//                                    userNameET.getText(), passwordET.getText()), ContentType.APPLICATION_JSON), this);
//
                break;
        }
    }

    @Override
    public void onSuccess(int requestId, int statusCode, String response) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onError(int requestId, int statusCode, String response, Throwable error) {
        Toast.makeText(this, String.format("ERROR:%s", statusCode), LENGTH_LONG).show();
    }
}
