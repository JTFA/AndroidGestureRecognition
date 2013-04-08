package com.GestureRecognition.UI;

import java.io.File;
import java.net.URI;

import com.GestureRecognition.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AddNewGestureActivity extends Activity{
	private String[] mFileList;
	private File mPath = new File(Environment.getExternalStorageDirectory() + "//yourdir//");
	private String mChosenFile;
	private static final String FTYPE = ".txt";    
	private static final int DIALOG_LOAD_FILE = 1000;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_new_gesture);
	    }	 
	 protected void onActivityResult (int requestCode, int resultCode, Intent data){
		 ((TextView)this.findViewById(R.id.vidInfoTitleTV)).setText(data.toString());
		 Intent actIntent = new Intent(this, ChooseProblemSetsActivity.class);
	 	 actIntent.setData(data.getData());
	 	 startActivity(actIntent);
	 }
	  public void pickAviClick(View v){
		  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
          intent.setType("video/*");
          startActivityForResult(Intent.createChooser(intent, "Select AVI"),99);
          
         
	    }
	 

	 
	 	public void onClick(View v){
	 		switch(v.getId()){
	 		case R.id.button2:
	 			dispatchTakeVideoIntent();
	 			
	 		break;
	 			
	 			
	 		}
	 	}
	 	private void handleCameraVideo(Intent intent) {
	 	    Uri mVideoUri = intent.getData();
	 	    //Log.d("vid",mVideoUri.toString());
	 	   Intent actIntent = new Intent(this, ChooseProblemSetsActivity.class);
	 	   actIntent.setData(intent.getData());
	 	   startActivity(actIntent);
	 	}
	 	private void dispatchTakeVideoIntent() {
	 	    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	 	    startActivityForResult(takeVideoIntent,0);
	 	    handleCameraVideo(takeVideoIntent);
	 	    
	 	}
}
