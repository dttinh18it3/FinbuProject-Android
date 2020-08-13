package com.example.finbuproject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import static android.content.ContentValues.TAG;

public class Singleton {
    private static Context myContext;
    private static Singleton mInstance;
    private RequestQueue requestQueue;
    private Singleton(Context context) {
        myContext = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(myContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Singleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Singleton(context);
        }
        return mInstance;
    }

    public<T> void addToRequestQueue (Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

}
