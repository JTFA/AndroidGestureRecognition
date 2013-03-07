package com.example.fsfsfsd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

public class demo extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        
    }
	protected void onActivityResult (int requestCode, int resultCode, Intent data){
		 VideoView vid = ((VideoView)this.findViewById(R.id.videoView1));
		 vid.setVideoPath(data.toString());
		 vid.start();
		 //Intent actIntent = new Intent(this, ChooseProblemSetsActivity.class);
	 	 //actIntent.setData(data.getData());
	 	 //startActivity(actIntent);
	 }
	public void cap(View v){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent, "Select AVI"),99);
	}
	public void erode(View v){
		P7 err = new P7();
	}
}
