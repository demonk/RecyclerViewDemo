package cn.demonk.roboding.recyclerviewtest;

import android.util.Log;

/**
 * Created by ligs on 10/8/16.
 */
public class DLog {

    private static final String TAG = "demonk";

    public static final void d(String msg) {
        Log.d(TAG, msg);
    }

    public static final void e(String msg) {
        Log.e(TAG, msg);
    }
}
