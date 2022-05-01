package com.example.we_care;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;

public class CameraActivity2 extends Activity implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final  String TAG="MainActivity";
    private Mat mRgba;
    private  Mat mGrey;
    private CameraBridgeViewBase mOpenCvCameraView;
    private Face_recognition face_recognition;
    private BaseLoaderCallback mLoaderCallback=new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
           switch (status){
               case LoaderCallbackInterface
                       .SUCCESS:{
                   Log.i(TAG, "OpenCv is loaded ");
                   mOpenCvCameraView.enableView();
               }
               default:  {
                   super.onManagerConnected(status);
               }
               break;
           }
        }
    };

    public CameraActivity2(){
        Log.i(TAG, "instantiated new"+this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int MY_PERMISSIONS_REQUEST_CAMERA=0;
      //if camera permission not given ,,, will ask for it on device
        if (ContextCompat.checkSelfPermission(CameraActivity2.this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions( CameraActivity2.this, new String[] {Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);
        }

        setContentView(R.layout.activity_camera2);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.frame_surface);
        //mOpenCvCameraView =(CameraBridgeViewBase)findViewById(R.id.frame_surface);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

//        int INPUT_SIZE = 0;
//        AssetManager assetManager = null;
//        Context context=null ;
//        String modelpath=null;
//        int input_size=INPUT_SIZE;
//
//        try {
//          Face_recognition camera = new Face_recognition(assetManager,context ,modelpath,input_size);
//            if ( camera.Val!=null){
//                Intent goToIdentify = new Intent(CameraActivity2.this, IdentifyGetData.class);
//                startActivity(goToIdentify); }
//            else if (camera.Val.equals("random_person"))
//        {
//                Intent goToHomles = new Intent(CameraActivity2.this, GoToHomeless.class);
//                startActivity(goToHomles);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
       // if (Face_recognition.Val!=null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(8000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!Face_recognition.Val.equals("")) {

                        if (Face_recognition.Val.equals("random_person")) {
                            Intent goToIdentify = new Intent(CameraActivity2.this, GoToHomeless.class);
                            startActivity(goToIdentify);
                            //  Toast.makeText(CameraActivity2.this, "Please Enter Homeless Data", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Intent goToIdentify = new Intent(CameraActivity2.this, IdentifyGetData.class);
                            startActivity(goToIdentify);
                            //     Toast.makeText(CameraActivity2.this, "Successful Recognition", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }).start();
     //   }





        try{

            int inputSize=96;

            OpenCVLoader.initDebug();
            face_recognition =new Face_recognition (getAssets(),
                    CameraActivity2.this ,
                    "model (1).tflite" ,
                  inputSize  );
        }
        catch (IOException e){
            e.printStackTrace();

            Log.d("CameraActivity2","Model is not loaded");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.d(TAG, "Opencv initialization is done");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else {
            Log.d(TAG, "Opencv is not loaded , try again");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,this,mLoaderCallback);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mOpenCvCameraView!=null){
            mOpenCvCameraView.disableView();
        }
    }
    public  void onDestroy(){
        super.onDestroy();
        if(mOpenCvCameraView!=null){
            mOpenCvCameraView.disableView();
        }
    }

    public void onCameraViewStarted(int width , int height){
        mRgba=new Mat(height,width, CvType.CV_8UC4);
        mGrey=new Mat(height,width, CvType.CV_8UC1);
    }
    public void onCameraViewStopped(){
        mRgba.release();
    }
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mRgba=inputFrame.rgba();
        mGrey=inputFrame.gray();

        //pass input of recognizeimage function as mRgba
        mRgba=face_recognition.recognizeImage(mRgba);


        return mRgba;
    }



}