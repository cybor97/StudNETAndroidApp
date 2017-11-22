package com.cybor.studnet.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.cybor.studnet.R;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = getLayoutInflater()
                .inflate(R.layout.action_bar_layout, (ViewGroup) getWindow().getDecorView(), true)
                .findViewById(R.id.toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(toolbar);
    }

    public int getStatusBarHeight() {
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? getResources().getDimensionPixelSize(resourceId) : 0;
    }
}
