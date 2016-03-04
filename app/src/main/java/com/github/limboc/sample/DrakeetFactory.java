package com.github.limboc.sample;


public class DrakeetFactory {

    protected static final Object monitor = new Object();
    static GankApi sGankIOSingleton = null;
    public static final int meizhiSize = 10;


    public static GankApi getGankIOSingleton() {
        synchronized (monitor) {
            if (sGankIOSingleton == null) {
                sGankIOSingleton = new DrakeetRetrofit().getGankService();
            }
            return sGankIOSingleton;
        }
    }


}
