package com.topstack.hiui.tab.top;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.topstack.hiui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 */
public class HiTabTopLayout extends HorizontalScrollView implements IHiTabLayout<HiTabTop, HiTabTopInfo<?>> {

    private List<OnTabSelectedListener<HiTabTopInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private HiTabTopInfo<?> selectedInfo;
    private List<HiTabTopInfo<?>> infoList;

    public HiTabTopLayout(Context context) {
        super(context);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public HiTabTop findTab(@NonNull HiTabTopInfo<?> info) {
        ViewGroup ll = getRootLayout(false);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof HiTabTop) {
                HiTabTop tab = (HiTabTop) child;
                if (tab.getHiTabInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabTopInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull HiTabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<HiTabTopInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        LinearLayout linearLayout = getRootLayout(true);
        selectedInfo = null;
        //清除之前添加的HiTabBottom listener,Tips：Java foreach remove问题
        Iterator<OnTabSelectedListener<HiTabTopInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabTop) {
                iterator.remove();
            }
        }
        for (int i = 0; i < infoList.size(); i++) {
            HiTabTopInfo<?> info = infoList.get(i);
            HiTabTop tab = new HiTabTop(getContext());
            tabSelectedChangeListeners.add(tab);
            tab.setHiTabInfo(info);
            linearLayout.addView(tab);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSelected(info);
                }
            });
        }
    }

    private void onSelected(@NonNull HiTabTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabTopInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private LinearLayout getRootLayout(boolean clear) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            addView(rootView, params);
        } else if (clear) {
            rootView.removeAllViews();
        }
        return rootView;
    }
}
