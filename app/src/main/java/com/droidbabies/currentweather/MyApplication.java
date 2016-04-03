package com.droidbabies.currentweather;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by sajidMacPro on 4/3/16.
 */
public class MyApplication extends Application {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getReqQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public void addToReqQueue(Request req, String tag) {

        getReqQueue().add(req);
    }

    public  void addToReqQueue(Request req) {

        getReqQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        getReqQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new BitmapLruCache());
        }
        return this.mImageLoader;
    }

    public void cancelPendingReq(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
