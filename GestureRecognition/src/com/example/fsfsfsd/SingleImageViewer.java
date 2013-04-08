package com.example.fsfsfsd;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class SingleImageViewer extends Activity{
	
	@Override
	public void onCreate(Bundle SavedInstanceState){
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.singleimage);
		((ImageView)this.findViewById(R.id.imageView1)).setImageBitmap(((Globals)getApplication()).bm);
	}

}
