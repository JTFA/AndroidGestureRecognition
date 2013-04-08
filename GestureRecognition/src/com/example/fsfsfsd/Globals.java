package com.example.fsfsfsd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.opencv.core.Mat;

import android.app.Application;
import android.graphics.Bitmap;

public class Globals extends Application {
	public ArrayList<Mat> gFrames;
	public VideoInfo vid;
	public Bitmap bm;
    private static ObjectOutputStream outStream = null;
    private static ObjectInputStream inStream = null;

    public static void setOutStream(ObjectOutputStream outStream) {
    	Globals.outStream = outStream;
    }

    public static ObjectOutputStream getOutStream() {
        return Globals.outStream;
    }
}