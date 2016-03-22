package com.github.limboc.sample;

import android.app.Application;
import android.content.Context;


public class App extends Application {

    private static App app;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    public void exit(){
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
