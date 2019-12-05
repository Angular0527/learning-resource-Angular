package com.gdgahmedabad.arcore.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import com.gdgahmedabad.arcore.MyApp;

/**
 * Project : GDGDevFestAr
 * Created by: Harsh Dalwadi - Senior Software Engineer
 * Created Date: 25-10-2018
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApp.setUser();
        MyApp.logOpenEvent();
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(this::redirectScreen, 2000);
    }

    private void redirectScreen() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                startActivity(new Intent(SplashActivity.this, ArActivity.class));
                finish();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(SplashActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();

            }
        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButton(true)
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
}
