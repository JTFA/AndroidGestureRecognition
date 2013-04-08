package com.example.fsfsfsd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class demo2 extends Activity {
	private ArrayList<Mat> frames;
	private ArrayList<Mat> binaryFrames;
	private  Bitmap bmp;
	private Mat[] mats;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.demo2);
	        frames = ((Globals)getApplication()).gFrames;
	        //Log.d("fraemcount", frames.size()+"");
	        //Log.d("lastc", frames.get(frames.size()-1).cols()+"");
	        //Log.d("lastr", frames.get(frames.size()-1).rows()+"");
	       // frames = (ArrayList<Mat>) getIntent().getSerializableExtra("frames");
	        binaries();
	        mats = mhi((char) 50);
	        
	        Mat themat = mats[0];
	        Mat mei = mats[1];
	        ImageView iv = (ImageView)this.findViewById(R.id.imageView1);
	        bmp = Bitmap.createBitmap(themat.cols(), themat.rows(),Bitmap.Config.ARGB_8888 );
	        Utils.matToBitmap(mei,bmp);
	        //Utils.matToBitmap(frames.get(15),bmp);
	        
	        iv.setImageBitmap(bmp);
	        
	    }

	
	 public void save(String s ){
		 GestureRecognitionDBHandler db = new GestureRecognitionDBHandler(this);
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   //get current date time with Date()
		 VideoInfo vi = new VideoInfo(s,dateFormat.format(new Date()));
		 Bitmap b = Bitmap.createBitmap(mats[0].cols(), mats[0].rows(),Bitmap.Config.ARGB_8888 );
		 vi.setMHI(b);
		 Bitmap b2 = Bitmap.createBitmap(mats[1].cols(), mats[1].rows(),Bitmap.Config.ARGB_8888 );
		 Utils.matToBitmap(mats[0],b);
		 Utils.matToBitmap(mats[1],b2);
		 vi.setMEI(b);
		 vi.setMEI(b2);
		 db.addVideoInfo(vi);
		 Intent intent = new Intent(this, DisplayVideo.class);
		 ((Globals)getApplication()).vid = vi;
		 startActivityForResult(intent,0);
	 }
	 
	 public void promptsave(View v){
		 final EditText input = new EditText(this); // This could also come from an xml resource, in which case you would use findViewById() to access the input
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setView(input);
		 builder.setMessage("Please name your recording");
		 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		     public void onClick(DialogInterface dialog, int whichButton) {
		         String value = input.getText().toString();
		         save(value); // mItem is a member variable in your Activity
		         dialog.dismiss();
		     }
		 });
		builder.show();
		 
		 
	 }
	 public void binaries(){
		 binaryFrames = new ArrayList<Mat>();
		 for(Mat m : frames)
			 binaryFrames.add(m.clone());
		 binaryFrames.get(0).setTo(new Scalar(0));
		 for(int m = 1;m<frames.size()-1;m++){
			 byte buff[] = new byte[(int) (frames.get(m).total() * frames.get(m).channels())];
			 byte buffOld[] = new byte[(int) (frames.get(m-1).total() * frames.get(m-1).channels())];
			 Mat retmat = new Mat(frames.get(m).size(), frames.get(m+1).type());
			 
			 Core.subtract( frames.get(m), frames.get(m+1),retmat);
			 frames.get(m).get(0, 0, buff);
			 frames.get(m-1).get(0, 0, buffOld);
			 for (int i = 0; i <  retmat.rows(); i++)
			    {
			        for(int j = 0; j < retmat.cols(); j++)
			        {
			            //cout << "current value: " << ret.at<char>(i,j) << endl;
	
			            if(Math.abs(buff[i*retmat.cols()+j] -buffOld[i*retmat.cols()+j])>40) 
			            	buff[i*retmat.cols()+j] = (byte) 255;
			            else 
			            	buff[i*retmat.cols()+j] = (byte) 0;
			        }
			    }
			 binaryFrames.get(m).put(0, 0, buff);
		 }
	 }
	 public Mat mei(){
		 return null;
	 }
	 	public Mat[] mhi(char tau){
	 		
	 		assert(binaryFrames.get(0).type() == CvType.CV_8U);

	 	    Mat mhi = new Mat(binaryFrames.get(0).rows(),binaryFrames.get(0).cols(),CvType.CV_8U);
	 	    mhi.setTo(new Scalar(0));
	 	    //mhi.setTo(0);
	 	     Mat mei = new Mat(binaryFrames.get(0).rows(),binaryFrames.get(0).cols(),CvType.CV_8U);
	 	    mei.setTo(new Scalar(0));

	 	    ArrayList<Mat> mhis = new ArrayList<Mat>();
	 	    //Mat mhi = Bs[0].clone();
	 	    //mhis.push_back(mhi.clone());


	 	    //imshow("inner mhi", mhi);
	 	  
	 	   
	 	   byte mbuff[] = new byte[(int) (mhi.total() * mhi.channels())];
	 	   mhi.get(0, 0,mbuff);
	 	  byte meibuff[] = new byte[(int) (mei.total() * mei.channels())];
       	  mei.get(0, 0,meibuff);
	 	    for(int m = 0; m < binaryFrames.size()/2 ;m++)
	 	    {
	 	        //imshow("inner mhi", mhi);
	 	        //!cout << "Mat results "<< Bs[m] << endl;
	 	    	byte buff[] = new byte[(int) (binaryFrames.get(m).total() * binaryFrames.get(m).channels())];
	 	    	binaryFrames.get(m).get(0, 0, buff);
	 	    	
	 	    	
	 	    	
	 	    	for (int i = 0; i <  binaryFrames.get(m).rows(); i++)
	 	        {
	 	            for(int j = 0; j < binaryFrames.get(m).cols(); j++)
	 	            {
	 	            	
	 	                if((buff[(i*binaryFrames.get(m).cols()+j)* binaryFrames.get(m).channels()])== (byte)255)
	 	                {
	 	                	
	 	                    //cout << " " << (int) Bs[m].at<unsigned char>(i,j) << " ";
	 	                	//byte[] mhiBuff = new byte[(int) mhi.cols() * mhi.channels()];
	 	                	//mhi.get(i, 0,mhiBuff);
	 	                	//mhiBuff[j*mhi.channels()] = (byte)tau;
	 	                	
	 	                	mbuff[i*mhi.cols() + j] = (byte)tau;
	 	                	meibuff[i*mei.cols() + j] = (byte)255;
	 	                	//mhi.put(i, 0,mhiBuff);
	 	                	
	 	                	
	 	                }
	 	                else
	 	                {
	 	                    if(m-1 >=0 && m-1 < binaryFrames.size())
	 	                    {
	 	                    	mbuff[i*mhi.cols()+ j]= (byte) Math.max(mbuff[i*mhi.cols()+j]-1,0);
	 	                    	//byte[] mhiBuff = new byte[(int) mhi.cols() * mhi.channels()];
	 	                    	//mhis.get(m-1).get(i, 0,mhiBuff);
	 	                    	//mhiBuff[j*mhi.channels()]= (byte) Math.min(mhiBuff[j]-1,0);
	 	                    	//mhi.put(i, 0,mhiBuff);
	 	                    }
	 	                }
	 	            }
	 	        }
	 	    	
	 	        //mhis.add(mhi.clone());
	 	        //imshow("MHI",mhi);
	 	        //imshow("MEI",mei);
	 	        //cvWaitKey(0);
	 	    }
	 	    mhi.put(0,0,mbuff);
	 	    mei.put(0,0,meibuff);
	 	    // #if WRITE
	 	    //saveFiles(mhis,2,call);
	 	    //#endif
	 	    Mat[] retarr = new Mat[2];
	 	    retarr[0] = mhi;
	 	    retarr[1] = mei;
	 	    return retarr;
	 	}
		
}
