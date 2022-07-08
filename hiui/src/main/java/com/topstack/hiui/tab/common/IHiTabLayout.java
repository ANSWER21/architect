package com.topstack.hiui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * @author Administrator
 * 泛型Tab是一个ViewGroup
 * D是对应数据
 */
public interface IHiTabLayout<Tab extends ViewGroup, D> {
    /**
     * Tab实体
     *
     * @param data
     * @return
     */
    Tab findTab(@NonNull D data);

    /**
     * 添加一个Tab选项切换到监听器
     *
     * @param listener
     */
    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    /**
     * 默认被选择的Tab选项
     *
     * @param defaultInfo
     */
    void defaultSelected(@NonNull D defaultInfo);

    /**
     * 注入信息
     *
     * @param infoList
     */
    void inflateInfo(@NonNull List<D> infoList);

    interface OnTabSelectedListener<D> {
        /**
         * 选中监听
         *
         * @param index    选中下标
         * @param prevInfo 上一个选中信息
         * @param nextInfo 下一个选中信息
         */
        void onTabSelectedChange(int index, @Nullable D prevInfo, @NonNull D nextInfo);
    }
}
