package com.GestureRecognition.processing;

import java.util.ArrayList;
import java.util.Map;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
public class P7 {
	private Mat Hu_dis;
	Map<Mat,Mat> motionImages;
	private VideoCapture capture;
	private String file_dir;
	private Mat Gxy; //background mean 
	
	public Mat binary_image_gen(final Mat f1, final Mat f2, int thresh)
	{
		Mat t0 = f1.clone(), t1 = f2.clone();
		Imgproc.cvtColor(t0, t0,  Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(t1, t1,  Imgproc.COLOR_RGB2GRAY);
		t0.convertTo(t0,CvType.CV_16S);
		t1.convertTo(t1,CvType.CV_16S);
		Mat ret = t0.clone().setTo(new Scalar(0));
		Core.subtract(t1, t0, ret);
		//TODO add the for loop code for thresholding
		
		/*
		 *  
		 for (int i = 0; i <  ret.rows; i++)
    	{
        	for(int j = 0; j < ret.cols; j++)
        	{
            	//cout << "current value: " << ret.at<char>(i,j) << endl;

            	if(abs(ret.at<short>(i,j)) > thresh) ret.at<short>(i,j) = 255;
            	else ret.at<short>(i,j) = 0;
        	}
    	}
		 * 
		 */
		
		return ret;
	}

	Map<Mat,Mat> MHI(ArrayList<Mat> Bs, int tau)
	{
		assert(Bs.get(0).type() == CvType.CV_8U);
		
		Mat mhi = new Mat(Bs.get(0).size(),CvType.CV_8U);
		mhi.setTo(new Scalar(0));
		
		Mat mei = new Mat(Bs.get(0).size(),CvType.CV_8U);
		mei.setTo(new Scalar(0));
		//TODO add the for loop code
		
		
		return null;
	}

	
	
	
	public Mat generateHuDiscriptors(){
		return null;
	}
	public Mat generateHu_central_Discriptors(){
		 

		    return null;
	}
	public double eta_n_pq(int p, int q, final Mat I){
		double mu00 = mu_pq(0,0,I);
	    double mupq = mu_pq(p,q,I);
	    double npq  = mupq/Math.pow(mu00,1 +(p+q)/2); 
	    return npq;
	}
	public int init_frames_Gxy(Mat gxy)
	{
		return 0;
	}

	public int backgroundInit(){
		return 0;
	}
	public Mat getGxy() {return Gxy;};
	

	private int Mij(int i, int j, final Mat I){
		
		int mij = 0;
	    for(int x = 0; x < I.rows(); x++)
	    {
	        for(int y = 0; y < I.cols(); y++)
	        {
	        	byte[] pix = new byte[(int) (I.total() * I.channels())];
	            mij += Math.pow(x,i)*Math.pow(y,j)*I.get(x,y,pix);
	        }
	    }
	    return mij;
	}
	private double mu_pq(int p, int q, final Mat I ){
		int M10 = Mij(1,0,I);
	    int M00 = Mij(0,0,I);
	    int M01 = Mij(0,1,I);
	    double x_avg = M10/(double)M00;
	    double y_avg = M01/(double)M00;

	    double mu_sum = 0;
	    for(int x = 0; x < I.rows(); x++)
	    {
	        for(int y = 0; y < I.cols(); y++)
	        {
	           mu_sum += Math.pow((x-x_avg),p) *Math.pow(y-y_avg,q);
	        }
	    }
	    return mu_sum;
	}
	private void saveFiles(ArrayList<Mat> m, int c){
		//for the p =1 version
		int p = 1;
	}
	private void saveFiles(ArrayList<Mat> m, int c,int p){
		
	}
	private void saveFile(final Mat m, int c, int f,int p){
		//for the String s ="" version
		String s = "";
	}
	private void saveFile(final Mat m, int c, int f,int p,String s){
		
	}


	Mat erode_img(final Mat src,int er_elem,int er_size ){
		int erosion_type = 0;
	    Mat erosion_dst = new Mat();
	    switch(er_elem)
	    {
	        case 0:
	            erosion_type =  Imgproc.MORPH_RECT;
	            break;
	        case 1:
	            erosion_type = Imgproc.MORPH_CROSS;
	            break;
	        case 2:
	            erosion_type = Imgproc.MORPH_ELLIPSE;
	            break;
	    }

	  Mat element = Imgproc.getStructuringElement( erosion_type,
	                                       new Size( 2*er_size + 1, 2*er_size+1 ),
	                                       new Point( er_size, er_size ) );

	  /// Apply the erosion operation
	  Imgproc.erode( src, erosion_dst, element );
	  return erosion_dst;
	}
	Mat erode_img(final Mat src,int er_size){
		int er_elem=1;
		return null;
	}
	Mat erode_img(final Mat src){
		int er_elem=1;
		int er_size = 1;
		return null;
	}
	Mat dilate_img(final Mat src,int dl_elem,int dl_size){
		return null;
	}
	Mat dilate_img(final Mat src,int dl_elem){
		int dl_size=5;
		return null;
	}
	Mat dilate_img(final Mat src){
		int dl_elem=2;
		int dl_size=5;
		return null;
	}
	public P7(String[] args){
		

	}
	public P7(){
		
	}
	public int init_frames(){
		return 0;
	}
}


















