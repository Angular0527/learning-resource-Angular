package com.gdgahmedabad.arcore.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;

import com.gdgahmedabad.arcore.MyApp;
import io.github.dalwadi2.gdgdevfestar.R;

import com.gdgahmedabad.arcore.utils.IntentShareHelper;
import com.gdgahmedabad.arcore.utils.SharePlatform;

/**
 * Project : GDGDevFestAr
 * Created by: Harsh Dalwadi - Senior Software Engineer
 * Created Date: 25-10-2018
 */
public class ShareActivity extends AppCompatActivity implements View.OnClickListener {

    private PhotoView imgPic;
    private String URI;

    private String shareMsg = "#GDGAhm #DevFestAhm #DevFest18 #DevFestIndia #ArFun #ArCore #ArAndroid @GDGAhmedabad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        imgPic = findViewById(R.id.img_pic);
        findViewById(R.id.btn_fb).setOnClickListener(this);
        findViewById(R.id.btn_twitter).setOnClickListener(this);
        findViewById(R.id.btn_whatsapp).setOnClickListener(this);
        findViewById(R.id.btn_other).setOnClickListener(this);
        findViewById(R.id.btn_open).setOnClickListener(this);


        URI = getIntent().getExtras().getString("pic", "");

        imgPic.setImageURI(Uri.parse(URI));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fb:
                IntentShareHelper.shareOnFacebook(this, shareMsg, Uri.parse(URI));
                MyApp.logShareEvent(SharePlatform.FB);
                break;
            case R.id.btn_twitter:
                IntentShareHelper.shareOnTwitter(this, shareMsg, Uri.parse(URI));
                MyApp.logShareEvent(SharePlatform.TWITTER);
                break;
            case R.id.btn_whatsapp:
                IntentShareHelper.shareOnWhatsapp(this, shareMsg, Uri.parse(URI));
                MyApp.logShareEvent(SharePlatform.WHATSAPP);
                break;
            case R.id.btn_other:
                IntentShareHelper.shareOnOther(this, shareMsg, Uri.parse(URI));
                MyApp.logShareEvent(SharePlatform.OTHER);
                break;
            case R.id.btn_open:
                IntentShareHelper.openImage(this, Uri.parse(URI));
                break;
        }
    }
}
