package com.GestureRecognition.UI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.GestureRecognition.Globals;
import com.GestureRecognition.R;
import com.GestureRecognition.VideoInfo;
import com.GestureRecognition.DB.GestureRecognitionDBHandler;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private  GestureRecognitionDBHandler db;
	private List<VideoInfo> vi ;
	@Override
	protected void onResume(){
		super.onResume();
		vi = db.getAllContacts();
		
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new GestureRecognitionDBHandler(this);
        vi = db.getAllContacts();
        ListView listView = (ListView) findViewById(R.id.StoredVideoLV);
        ArrayAdapter<VideoInfo> adapter = new ArrayAdapter<VideoInfo>(this,
        		  android.R.layout.simple_list_item_1, android.R.id.text1, vi);
        listView.setAdapter(adapter); 
        listView.setOnItemClickListener(new OnItemClickListener() {
        	  
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
					displayDBEntry(position);
				// TODO Auto-generated method stub
				
			}
        	}); 
    }
    public void displayDBEntry(int which){
    	Intent intent = new Intent(this, DisplayVideo.class);
		 ((Globals)getApplication()).vid = ((ArrayList<VideoInfo>)vi).get(which);
		 startActivityForResult(intent,0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void onClick(View v){
    	  	Intent intent = new Intent(this, AddNewGestureActivity.class);
    	    startActivity(intent);
    }
    public void demo(View v){
    	Intent intent = new Intent(this, demo.class);
	    startActivity(intent);
    }
}
