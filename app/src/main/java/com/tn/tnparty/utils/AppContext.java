package com.tn.tnparty.utils;

import java.util.HashMap;

/**
 * Created by raghav on 1/14/2018.
 */

public class AppContext {


    private static final AppContext ourInstance = new AppContext();

    private static volatile HashMap<String, Object> mContext;// = new HashMap<>();

    public static AppContext getInstance() {

        if (mContext == null)
            mContext = new HashMap<>();

        return ourInstance;
    }

    private AppContext() {
    }

    public void add(String key, Object val) {

        if (mContext != null) {
            mContext.put(key, val);
        }
    }

    public Object get(String key) {

        if (mContext != null) {
            return mContext.get(key);
        }

        return null;
    }

    public void remove(String key) {

        if (mContext != null) {
            mContext.remove(key);
        }
    }

    public void clearContext() {

        mContext = null;
    }


}
