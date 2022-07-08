package com.topstack.hilog.log;

/**
 * @author Administrator
 */
public interface HiLogFormatter<T> {
    String format(T data);
}
