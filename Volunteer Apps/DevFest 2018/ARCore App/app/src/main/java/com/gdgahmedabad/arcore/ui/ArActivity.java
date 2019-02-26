package com.gdgahmedabad.arcore.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import com.gdgahmedabad.arcore.MyApp;
import io.github.dalwadi2.gdgdevfestar.R;

/**
 * Project : GDGDevFestAr
 * Created by: Harsh Dalwadi - Senior Software Engineer
 * Created Date: 25-10-2018
 */
public class ArActivity extends AppCompatActivity {
    private static final String TAG = ArActivity.class.getSimpleName();

    private static final double MIN_OPENGL_VERSION = 3.0;
    final CharSequence[] pickObject = {"GDG Logo", "DevFest", "GDGAhmedabad", "Firebase", "Flutter", "Google Cloud", "Angular", "Node JS", "Android"};
    private int selectedObject = 0;
    private AlertDialog alertdialog1;
    private ModelRenderable gdgLogoRenderable;
    private ModelRenderable devFestRenderable;
    private ModelRenderable gdgAhmRenderable;
    private ModelRenderable firebaseRenderable;
    private ModelRenderable flutterRenderable;
    private ModelRenderable googleCloudRenderable;
    private ModelRenderable angularRenderable;
    private ModelRenderable nodejsRenderable;
    private ModelRenderable androidRenderable;
    private ModelRenderable mainObject;
    private CustomArFragment arFragment;
    private ArActivity mActivity;
    private FrameLayout flash;

    @SuppressLint("ObsoleteSdkInt")
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) Objects.requireNonNull(activity.getSystemService(Context.ACTIVITY_SERVICE)))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setContentView(R.layout.activity_ar);

        mActivity = this;

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);
        flash = findViewById(R.id.pic_flash);

        findViewById(R.id.fab_pick).setOnClickListener(view -> {
            alertdialog1.show();
        });
        findViewById(R.id.fab_clear).setOnClickListener(view -> {
            clearAll();
        });

        findViewById(R.id.fab_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        createDialog();

        arFragment.getPlaneDiscoveryController().show();


        Session session = null;
        try {
            session = new Session(this);
            Config config = new Config(session);
            config.setFocusMode(Config.FocusMode.AUTO);
//            Log.w(TAG, "onCreate: " + config.getFocusMode().name());
            session.configure(config);
        } catch (UnavailableArcoreNotInstalledException e) {
            Log.w(TAG, "UnavailableArcoreNotInstalledException: ", e);
        } catch (UnavailableApkTooOldException e) {
            Log.w(TAG, "UnavailableApkTooOldException: ", e);
        } catch (UnavailableSdkTooOldException e) {
            Log.w(TAG, "UnavailableSdkTooOldException: ", e);
        }

        createModels();
        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (mainObject == null) {
                        return;
                    }

                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    // Create the transformable andy and add it to the anchor.
                    TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
                    andy.setParent(anchorNode);

                    andy.setRenderable(mainObject);
                    andy.select();

                    andy.setOnTapListener(new Node.OnTapListener() {
                        @Override
                        public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                            Log.w(TAG, "onTap: ");
                            andy.removeChild(andy.getParent());
                            anchor.detach();

                        }
                    });

                   /* anchorNode.setOnTapListener(new Node.OnTapListener() {
                        @Override
                        public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                            Log.w(TAG, "anchorNode onTap: " );
                        }
                    });*/
                });

    }

    private void clearAll() {
        Collection<Anchor> anchors = arFragment.getArSceneView().getSession().getAllAnchors();
        Log.w(TAG, "clearAll: " + anchors.size());
        if (anchors.size() > 0) {
            for (Anchor anchor : anchors) {
                anchor.detach();
            }
        }
    }

    private void createModels() {
        ModelRenderable.builder()
                .setSource(this, R.raw.gdg_final)
                .build()
                .thenAccept(renderable -> {
                    gdgLogoRenderable = renderable;
                    mainObject = gdgLogoRenderable;
                })
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.devfest_final)
                .build()
                .thenAccept(renderable -> devFestRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.gdg_full_final)
                .build()
                .thenAccept(renderable -> gdgAhmRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.firebase1)
                .build()
                .thenAccept(renderable -> firebaseRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.flutter_final)
                .build()
                .thenAccept(renderable -> flutterRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.google_cloud_final)
                .build()
                .thenAccept(renderable -> googleCloudRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.angular1)
                .build()
                .thenAccept(renderable -> angularRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.nodejs)
                .build()
                .thenAccept(renderable -> nodejsRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.andy)
                .build()
                .thenAccept(renderable -> androidRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (arFragment != null)
            arFragment.onStop();

    }

    private void takePhoto() {
        MyApp.logCaptureEvent();
        final String filename = generateFilename();
        ArSceneView view = arFragment.getArSceneView();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            saveBitmapToDisk(bitmap, filename);
                        } catch (IOException e) {
                            Toast toast = Toast.makeText(mActivity, e.toString(),
                                    Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                File photoFile = new File(filename);
                                Uri photoURI = FileProvider.getUriForFile(mActivity,
                                        mActivity.getPackageName() + ".pic.provider",
                                        photoFile);
                                startActivity(new Intent(mActivity, ShareActivity.class)
                                        .putExtra("pic", photoURI.toString())
                                );
                            }
                        });

                    }
                }).start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        flash.setVisibility(View.VISIBLE);

                        AlphaAnimation fade = new AlphaAnimation(1, 0);
                        fade.setDuration(100);
                        fade.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation anim) {
                                flash.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        flash.startAnimation(fade);
                    }
                });


            } else {
                Toast toast = Toast.makeText(mActivity,
                        "Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }

    private void saveBitmapToDisk(Bitmap bitmap, String filename) throws IOException {

        File out = new File(filename);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        try (FileOutputStream outputStream = new FileOutputStream(filename);
             ByteArrayOutputStream outputData = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData);
            outputData.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            throw new IOException("Failed to save bitmap to disk", ex);
        }
    }

    private String generateFilename() {
        String date =
                new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault()).format(new Date());
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + File.separator + "ArPhotos/" + date + "_devfest18.jpg";
    }

    public void createDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this)
                .setTitle("Choose an Object")
                .setSingleChoiceItems(pickObject, selectedObject, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedObject = which;
                        dialog.dismiss();
                        selectObject();
                    }
                });
        alertdialog1 = builder1.create();
    }

    private void selectObject() {
        switch (selectedObject) {
            case 0:
                mainObject = gdgLogoRenderable;
                break;
            case 1:
                mainObject = devFestRenderable;
                break;
            case 2:
                mainObject = gdgAhmRenderable;
                break;
            case 3:
                mainObject = firebaseRenderable;
                break;
            case 4:
                mainObject = flutterRenderable;
                break;
            case 5:
                mainObject = googleCloudRenderable;
                break;
            case 6:
                mainObject = angularRenderable;
                break;
            case 7:
                mainObject = nodejsRenderable;
                break;
            case 8:
                mainObject = androidRenderable;
                break;
            default:
                mainObject = gdgLogoRenderable;
        }
    }
}
