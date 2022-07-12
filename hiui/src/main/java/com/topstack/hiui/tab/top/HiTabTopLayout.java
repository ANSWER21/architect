package com.topstack.hiui.tab.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.topstack.hilibrary.util.HiDisplayUtil;
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
        autoScroll(nextInfo);
    }

    int tabWith;

    /**
     * 实现自动滚动
     *
     * @param nextInfo
     */
    private void autoScroll(HiTabTopInfo<?> nextInfo) {
        HiTabTop tabTop = findTab(nextInfo);
        if (tabTop == null) {
            return;
        }
        int index = infoList.indexOf(nextInfo);
        int[] loc = new int[2];
        //获取控件在屏幕上的位置(返回值装载进loc中)
        tabTop.getLocationInWindow(loc);
        int scrollWidth;
        if (tabWith == 0) {
            tabWith = tabTop.getWidth();
        }
        //判断点击了屏幕左侧还是右侧
        if ((loc[0] + tabWith / 2) > HiDisplayUtil.getDisplayWidthInPx(getContext()) / 2) {
            scrollWidth = rangeScrollWidth(index, 2);
        } else {
            scrollWidth = rangeScrollWidth(index, -2);
        }
        scrollTo(getScrollX() + scrollWidth, 0);
    }


    /**
     * 获取可滚动的范围
     *
     * @param index 从第几个开始
     * @param range 向前向后的范围
     * @return 可滚动的范围
     */
    private int rangeScrollWidth(int index, int range) {
        int scrollWidth = 0;
        for (int i = 0; i <= Math.abs(range); i++) {
            int next;
            if (range < 0) {
                next = range + i + index;
            } else {
                next = range - i + index;
            }
            if (next >= 0 && next < infoList.size()) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false);
                } else {
                    scrollWidth += scrollWidth(next, true);
                }
            }
        }
        return scrollWidth;

    }

    /**
     * 指定位置的控件可滚动的距离
     *
     * @param index   指定位置的控件
     * @param toRight 是否是点击了屏幕右侧
     * @return 可滚动的距离
     */
    private int scrollWidth(int index, boolean toRight) {
        HiTabTop target = findTab(infoList.get(index));
        if (target == null) {
            return 0;
        }
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        /**
         * 点击屏幕右侧
         * right坐标大于控件的宽度时，说明完全没有显示
         * 显示部分，减去已显示的宽度
         * left坐标小于等于-控件的宽度，说明完全没有显示
         * 显示部分
         */
        if (toRight) {
            if (rect.right > tabWith) {
                return tabWith;
            } else {
                return tabWith - rect.right;
            }
        } else {
            if (rect.left <= -tabWith) {
                return tabWith;
            } else if (rect.left > 0) {
                return rect.left;
            }
            return 0;
        }
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
