package com.GestureRecognition.UI;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint.Cap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener;

import com.GestureRecognition.Globals;
import com.GestureRecognition.R;
import com.GestureRecognition.processing.P7;


public class demo extends Activity implements CvCameraViewListener{
	private CameraBridgeViewBase mOpenCvCameraView;
	private ArrayList<Mat> frames;
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
	    @Override
	    public void onManagerConnected(int status) {
	        switch (status) {
	            case LoaderCallbackInterface.SUCCESS:
	            {
	            	//System.loadLibrary("opencv_java");
	                Log.i("tag", "OpenCV loaded successfully");
	               
	            } break;
	            default:
	            {
	            	Log.i("tag", "OpenCV not loaded successfully");
	                super.onManagerConnected(status);
	            } break;
	        }
	    }
	};

	@Override
	public void onResume()
	{
	    super.onResume();
	    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
	}
	public void runCam(){

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        
        
    }
	protected void onActivityResult (int requestCode, int resultCode, Intent data){
		 //VideoView vid = ((VideoView)this.findViewById(R.id.videoView1));
		 //vid.setVideoPath(data.toString());
		 //vid.start();
		 //Intent actIntent = new Intent(this, ChooseProblemSetsActivity.class);
	 	 //actIntent.setData(data.getData());
	 	 //startActivity(actIntent);
	 }
	public void stop(View v){
		mOpenCvCameraView.disableView();
	}
	public void process(View v){
		Intent actIntent = new Intent(this, demo2.class);
		actIntent.setFlags(actIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
		((Globals)getApplication()).gFrames= frames;
	 	startActivityForResult(actIntent,0);
		
	}
	public void cap(View v){
		//Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("video/*");
        //startActivityForResult(Intent.createChooser(intent, "Select AVI"),99);
		mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.puzzle_activity_surface_view);
        mOpenCvCameraView.setCvCameraViewListener(this);
        frames = new ArrayList<Mat>();
        mOpenCvCameraView.enableView();
	}
	public void erode(View v){
		P7 err = new P7();
	}
	@Override
	public void onCameraViewStarted(int width, int height) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stubzz
		
	}
	@Override
	public Mat onCameraFrame(Mat inputFrame) {
		Imgproc.cvtColor(inputFrame, inputFrame, Imgproc.COLOR_BGR2GRAY);
		frames.add(inputFrame.clone());
		Log.d("the size", "" + inputFrame.total());
		return inputFrame;
	}
}
