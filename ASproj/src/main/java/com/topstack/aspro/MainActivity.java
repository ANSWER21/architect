package com.topstack.aspro;

import android.os.Bundle;

import com.topstack.aspro.logic.MainActivityLogic;
import com.topstack.common.ui.component.HiBaseActivity;

/**
 * @author Administrator
 */
public class MainActivity extends HiBaseActivity implements MainActivityLogic.ActivityProvider {
    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityLogic = new MainActivityLogic(this);
    }
}
