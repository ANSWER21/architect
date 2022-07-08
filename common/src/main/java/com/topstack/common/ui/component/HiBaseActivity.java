package com.topstack.common.ui.component;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.topstack.common.R;

/**
 * @author Administrator
 */
public class HiBaseActivity extends AppCompatActivity implements HiBaseActionInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_base);
    }
}