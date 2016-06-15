package com.github.limboc.sample;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;


public class App extends Application {

    private static App app;
    public static Context context;
    public HashMap<String, WeakReference<Activity>> activityList = new HashMap<>();

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

    public void addActivity(Activity activity) {
        if (null != activity) {
            activityList.put(activity.getClass().getName(), new WeakReference<>(activity));
        }
    }

    public void removeActivity(Activity activity){
        if (null != activity) {
            activityList.remove(activity.getClass().getName());
        }
    }

    public void finishSingleActivityByClass(Class cls) {
        Iterator<String> iterator = activityList.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            WeakReference<Activity> activity = activityList.get(key);
            if(key.equals(cls.getName()) && activity != null && activity.get() != null){
                activity.get().finish();
                iterator.remove();
                return;
            }
        }
    }

    public void finishAllActivity(){
        Iterator<String> iterator = activityList.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            WeakReference<Activity> activity = activityList.get(key);
            if (activity != null && activity.get() != null) {
                activity.get().finish();
                iterator.remove();
            }
        }
    }
}
