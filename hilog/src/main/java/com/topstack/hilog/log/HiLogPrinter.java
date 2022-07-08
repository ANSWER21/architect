package com.topstack.hilog.log;

import androidx.annotation.NonNull;

/**
 * @author Administrator
 */
public interface HiLogPrinter {
    /**
     * Log打印方法
     *
     * @param config
     * @param level
     * @param tag
     * @param printString
     */
    void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString);
}
