package com.gdgahmedabad.arcore.utils;


/**
 * Created by: Harsh Dalwadi - Software Engineer
 * Created Date: 23-11-2018
 */
public enum SharePlatform {
    FB("Facebook"), TWITTER("Twitter"), WHATSAPP("WhatsApp"), OTHER("Other");

    private String platform;

    SharePlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }
}
