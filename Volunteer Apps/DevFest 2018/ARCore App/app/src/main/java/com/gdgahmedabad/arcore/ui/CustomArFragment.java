package com.gdgahmedabad.arcore.ui;

import android.Manifest;

import com.google.ar.sceneform.ux.ArFragment;


/**
 * Project : GDGDevFestAr
 * Created by: Harsh Dalwadi - Senior Software Engineer
 * Created Date: 25-10-2018
 */
public class CustomArFragment extends ArFragment {
    @Override
    public String[] getAdditionalPermissions() {
        String[] additionalPermissions = super.getAdditionalPermissions();
        int permissionLength = additionalPermissions != null ? additionalPermissions.length : 0;
        String[] permissions = new String[permissionLength + 2];
        permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        permissions[1] = Manifest.permission.CAMERA;
        if (permissionLength > 0) {
            System.arraycopy(additionalPermissions, 0, permissions, 1, additionalPermissions.length);
        }
        return permissions;
    }
}
