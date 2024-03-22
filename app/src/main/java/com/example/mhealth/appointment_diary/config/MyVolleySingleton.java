package com.example.mhealth.appointment_diary.config;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyVolleySingleton {
    private static MyVolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private MyVolleySingleton(Context ctx) {
        context = ctx;
        requestQueue = getRequestQueue();
    }

    public static synchronized MyVolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MyVolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext(), new TLSHurlStack());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

