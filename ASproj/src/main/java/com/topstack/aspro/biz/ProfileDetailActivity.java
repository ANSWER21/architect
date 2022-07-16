package com.topstack.aspro.biz;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.topstack.aspro.route.RouteFlag;

/**
 * @author Administrator
 */
@Route(path = "/profile/detail", extras = RouteFlag.FLAG_LOGIN)
public class ProfileDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
