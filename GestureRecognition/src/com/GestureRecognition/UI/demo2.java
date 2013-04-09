package com.GestureRecognition.UI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.GestureRecognition.Globals;
import com.GestureRecognition.R;
import com.GestureRecognition.VideoInfo;
import com.GestureRecognition.DB.GestureRecognitionDBHandler;


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
	        
	        mats = MHI_MEI(gen_frames(60),90);
	        
	        Mat mhi = mats[0];
	        Mat mei = mats[1];
	        ImageView iv = (ImageView)this.findViewById(R.id.imageView1);
	        bmp = Bitmap.createBitmap(mhi.cols(), mhi.rows(),Bitmap.Config.ARGB_8888 );
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
	

	 	public ArrayList<Mat> gen_frames(int thresh)
	 	{
	 		ArrayList<Mat> frame_diff   = new ArrayList<Mat>();

	 		for(int i = 0; i < frames.size()-1; i++ )
	 		{
	 			frame_diff.add(FrameDiff(frames.get(i),frames.get(i+1),thresh));
	 		}
	 		
	 		return frame_diff;
	 	}
	 
	 	public Mat FrameDiff(final Mat f1, final Mat f2, int thresh)
	 	{
	 		Mat t0 = f1.clone(), t1 = f2.clone();
	 		//Imgproc.cvtColor(t0, t0,  Imgproc.COLOR_RGB2GRAY);
	 		//Imgproc.cvtColor(t1, t1,  Imgproc.COLOR_RGB2GRAY);
	 		//t0.convertTo(t0,CvType.CV_16S);
	 		//t1.convertTo(t1,CvType.CV_16S);
	 		
	 		Mat ret = t0.clone().setTo(new Scalar(0));
	 		Core.subtract(t1, t0, ret);
	 		
	 		//short buff[] = new short[(int) (ret.total() * ret.channels())];
	 		byte buff[] = new byte[(int) (ret.total() * ret.channels())];
	 		ret.get(0, 0, buff);
	 		
	 		for(int row = 1; row < ret.rows(); row++) {
				for(int col = 0; col < ret.cols(); col++) {
					
					//short a = (short) Math.abs(buff[col + row*ret.cols()]);
					byte a = buff[col + row*ret.cols()];
					if(a > thresh) buff[col + row*ret.cols()] = (byte) 254;
					else buff[col + row*ret.cols()] = 0;
					
				}
	 		}
	 		ret.put(0,0,buff);
	 		//ret.convertTo(ret, CvType.CV_8U); //TODO might need to convert
	 		return ret;
	 	}
	 	
	 	public Mat[] MHI_MEI(ArrayList<Mat> Bs, int tau)
	 	{
	 		assert(Bs.get(0).type() == CvType.CV_8U);
			
	 		ArrayList<byte[]> Mhis = new ArrayList< byte[]>();
	 		
			Mat mhi = new Mat(Bs.get(0).size(),CvType.CV_8U);
			mhi.setTo(new Scalar(0));
			
			Mat mei = new Mat(Bs.get(0).size(),CvType.CV_8U);
			mei.setTo(new Scalar(0));
			
			byte bs[] = new byte[(int) (Bs.get(0).total() * Bs.get(0).channels())];
			
			byte mhi_arr[] = new byte[(int) (mhi.total() * mhi.channels())];
			byte mei_arr[] = new byte[(int) (mei.total() *mei.channels())];
			
			for(int m = 0; m < Bs.size();m++)
			{
				Bs.get(0).get(0, 0,bs);
				for (int i = 0; i <  Bs.get(m).rows(); i++)
	 	        {
	 	            for(int j = 0; j < Bs.get(m).cols(); j++)
	 	            {
	 	            	if((bs[(i*Bs.get(m).cols()+j)* Bs.get(m).channels()])== (byte)255)
	 	            	{
	 	            		mhi_arr[i*mhi.cols() + j] = (byte)tau;
	 	            		mei_arr[i*mei.cols() + j] = (byte)tau;
	 	            	}
	 	            	else
	 	            	{
	 	            		if(m > 0 && m-1 < Bs.size())
	 	                    {
	 	            			mhi_arr[i*mhi.cols() + j] = (byte) Math.max(Mhis.get(m-1)[i*mhi.cols() + j]-1,0);
	 	                    }
	 	            	}
	 	            }
	 	           Mhis.add(mhi_arr);
	 	        }
			}
			mhi.put(0,0,mhi_arr);
	 	    mei.put(0,0,mei_arr);
			return new Mat[] {mhi,mei};
			
	 	}
	 	
	 	

		
}
