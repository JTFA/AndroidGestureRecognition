package com.GestureRecognition.UI;

import com.GestureRecognition.Globals;
import com.GestureRecognition.R;
import com.GestureRecognition.VideoInfo;
import com.GestureRecognition.DB.GestureRecognitionDBHandler;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;

public class DisplayVideo extends Activity{
	private GestureRecognitionDBHandler db;
	private VideoInfo vi;
	@Override
	public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.videoinfo);
	     db = new GestureRecognitionDBHandler(this);
	     vi = ((Globals)getApplication()).vid;
	     showVI();
	}
	public void delete(View V){
		db.deleteVideoInfo(vi);
		finish();
	}
	
	public void viewMHI(View v){
		((Globals)getApplication()).bm = vi.getMHI();
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		((Globals)getApplication()).bm  = Bitmap.createBitmap(((Globals)getApplication()).bm , 0, 0, 
				((Globals)getApplication()).bm .getWidth(), ((Globals)getApplication()).bm .getHeight(), 
		                              matrix, true);
		Intent i = new Intent(this, SingleImageViewer.class);
		startActivityForResult(i, 0);
	}
	public void viewMEI(View v){
		((Globals)getApplication()).bm = vi.getMEI();
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		((Globals)getApplication()).bm  = Bitmap.createBitmap(((Globals)getApplication()).bm , 0, 0, 
				((Globals)getApplication()).bm .getWidth(), ((Globals)getApplication()).bm .getHeight(), 
		                              matrix, true);
		Intent i = new Intent(this, SingleImageViewer.class);
		startActivityForResult(i, 0);
	}
	public void showVI(){
		showVI(vi);
	}
	public void showVI(VideoInfo vi){
		
		((TextView)this.findViewById(R.id.vidInfoTitleTV)).setText(vi.getTitle());
		((TextView)this.findViewById(R.id.vidInfoLastModifiedTV)).setText(vi.getLastModifiedDate());
		((ImageButton)this.findViewById(R.id.imageButton2)).setImageBitmap(vi.getMHI());
		((ImageButton)this.findViewById(R.id.imageButton1)).setImageBitmap(vi.getMEI());
	}
	
}
