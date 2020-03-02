package com.swetajain.openvision;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity implements
        CameraBridgeViewBase.CvCameraViewListener2 {

    CameraBridgeViewBase cameraBridgeViewBase;
    Mat mat1, mat2, mat3;
    BaseLoaderCallback baseLoaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraBridgeViewBase = findViewById(R.id.camera_view);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        baseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                super.onManagerConnected(status);
                switch (status){
                    case BaseLoaderCallback.SUCCESS:
                        cameraBridgeViewBase.enableView();
                        break;
                    default: super.onManagerConnected(status);
                        break;
                }
            }
        };

        if (OpenCVLoader.initDebug()) {
            Toast.makeText(getApplicationContext(),
                    "OpenCV loaded successfully!",
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "OpenCV not loaded !",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mat1 = new Mat(width, height, CvType.CV_8UC(4));
        mat2 = new Mat(width, height, CvType.CV_8UC(4));
        mat3 = new Mat(width, height, CvType.CV_8UC(4));
    }

    @Override
    public void onCameraViewStopped() {

        mat1.release();
//        mat2.release();
//        mat3.release();

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mat1 = inputFrame.rgba();
       // Core.transpose(mat1,mat2);
      //  Imgproc.resize(mat2,mat3,mat3.size(),0,0,0);
       // Core.flip(mat3,mat1,1);


        return mat1;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(getApplicationContext(),
                    "Problem in loading !",
                    Toast.LENGTH_SHORT)
                    .show();
        }else{
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
        }
    }
}
