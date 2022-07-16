package com.topstack.aspro.biz;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.topstack.aspro.route.RouteFlag;

/**
 * @author Administrator
 */
@Route(path = "/profile/vip", extras = RouteFlag.FLAG_VIP)
public class VipActivity extends AppCompatActivity {
}
