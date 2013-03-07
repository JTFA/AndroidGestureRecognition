package com.example.fsfsfsd;

import java.net.URI;
import java.util.ArrayList;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ChooseProblemSetsActivity extends Activity{
	private Uri data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		data = getIntent().getData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_problem_sets);
        
    }
	private ArrayList<Float> labels;
	public void probSet3(View v){
		if (data.toString().contains("A1")){
			labels.add(0.0f);
        }else if (data.toString().contains("A2")){
			labels.add(1.0f);
        }else if (data.toString().contains("A3")){
			labels.add(2.0f);
        }
	}
}
