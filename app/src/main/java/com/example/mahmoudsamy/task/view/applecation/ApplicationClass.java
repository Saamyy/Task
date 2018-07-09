package com.example.mahmoudsamy.task.view.applecation;

import android.app.Application;
import android.content.Context;

public class ApplicationClass extends Application {
    private static Context context;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
}
