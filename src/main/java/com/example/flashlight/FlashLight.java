package com.example.flashlight;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class FlashLight {

    private Context context;
    private Activity activity;
    private Camera camera;
    private Camera.Parameters params;


    public FlashLight(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void init()throws Exception{
        if(!isPermissionsGranted()){
            askPermissions();
        }
        try {
            initCamera();
            if(camera == null || params == null){
                throw new Exception();
            }
        }
        catch (Exception e){
            throw new Exception();
        }
    }

    public void destroy(){
        if(camera != null){
            camera.release();
            camera = null;
        }
    }

    public void turnOn(){
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }

    public void turnOff(){
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
    }

    private void initCamera(){
        camera = Camera.open();
        params = camera.getParameters();
    }

    private boolean isPermissionsGranted(){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void askPermissions(){
        ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, 60);
    }
}
