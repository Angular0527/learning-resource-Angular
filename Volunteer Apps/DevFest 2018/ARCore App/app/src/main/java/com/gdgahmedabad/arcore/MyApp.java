package com.gdgahmedabad.arcore;

import android.app.Application;
import android.os.Bundle;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.gdgahmedabad.arcore.utils.DeviceInfo;
import com.gdgahmedabad.arcore.utils.SharePlatform;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;

//import com.google.firebase.analytics.FirebaseAnalytics;


/**
 * Created by: Harsh Dalwadi - Software Engineer
 * Created Date: 23-11-2018
 */
public class MyApp extends Application {
    private static MyApp mInstance;
    private static FirebaseAnalytics mFirebaseAnalytics;

    public static synchronized MyApp getInstance() {
        return mInstance;
    }

    public static void setUser() {
        mFirebaseAnalytics.setUserProperty("Device_name", DeviceInfo.getDeviceName());
    }

    public static void logCaptureEvent() {
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Click Picture");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent("Click_Picture", bundle);

        Answers.getInstance().logCustom(new CustomEvent("Click_Picture")
                .putCustomAttribute("CONTENT_TYPE", "image")
        );
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Click_Picture")
                .putContentType("image")
                .putContentId("1"));
    }

    public static void logOpenEvent() {
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 2);
        bundle.putString(FirebaseAnalytics.Param.DESTINATION, DeviceInfo.getDeviceName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
        Answers.getInstance().logCustom(new CustomEvent("APP_OPEN")
                .putCustomAttribute("DESTINATION", DeviceInfo.getDeviceName())
        );
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("APP_OPEN")
                .putContentType(DeviceInfo.getDeviceName())
                .putContentId("2"));
    }

    public static void logShareEvent(SharePlatform platform) {
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 3);
        bundle.putString(FirebaseAnalytics.Param.DESTINATION, platform.getPlatform());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
        Answers.getInstance().logCustom(new CustomEvent("SHARE")
                .putCustomAttribute("DESTINATION", platform.getPlatform())
        );
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("SHARE")
                .putContentType(platform.getPlatform())
                .putContentId("3"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Fabric.with(this, new Answers());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

}
