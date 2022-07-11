package com.topStack.architect;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.topstack.hiui.tab.common.IHiTabLayout;
import com.topstack.hiui.tab.top.HiTabTopInfo;
import com.topstack.hiui.tab.top.HiTabTopLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class HiTabTopDemoActivity extends AppCompatActivity {

    String[] tabsStr = new String[]{
            "热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_tab_top_demo);
        initTabTop();
    }

    private void initTabTop() {
        HiTabTopLayout hiTabTopLayout = findViewById(R.id.tab_top_layout);
        List<HiTabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabTopDefaultColor);
        int tintColor = getResources().getColor(R.color.tabTopTintColor);
        for (String s : tabsStr) {
            HiTabTopInfo<?> info = new HiTabTopInfo<>(s, defaultColor, tintColor);
            infoList.add(info);
        }
        hiTabTopLayout.inflateInfo(infoList);
        hiTabTopLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabTopInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable HiTabTopInfo<?> prevInfo, @NonNull HiTabTopInfo<?> nextInfo) {
                Toast.makeText(HiTabTopDemoActivity.this, nextInfo.name, Toast.LENGTH_LONG).show();
            }
        });
        hiTabTopLayout.defaultSelected(infoList.get(0));
    }

}