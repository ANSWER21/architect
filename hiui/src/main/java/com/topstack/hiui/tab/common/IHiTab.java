package com.topstack.hiui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * @author Administrator
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {
    void setHiTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     *
     * @param height 高度
     */
    void resetHeight(@Px int height);
}
