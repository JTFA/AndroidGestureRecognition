package com.GestureRecognition;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class VideoInfo {
	private String title;
	private int ID;
	public int getID(){
		return ID;
	}
	public void setID(int id){
		ID=id;
	}
	private String lastModifiedDate;
	private Bitmap MHI;
	private Bitmap MEI;
	private ArrayList<Bitmap> frames;
	@Override
	public String toString(){
		return title;
	}
	public VideoInfo(){
		setFrames(new ArrayList<Bitmap>());
	}
	public VideoInfo(String t, String l){
		title = t;
		lastModifiedDate = l;
	}
	public VideoInfo(String t, String l, Bitmap MH, Bitmap ME){
		setFrames(new ArrayList<Bitmap>());
		setTitle(t);
		setLastModifiedDate(l);
		setMHI(MH);
		setMEI(ME);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Bitmap getMHI() {
		return MHI;
	}
	public void setMHI(Bitmap mHI) {
		MHI = mHI;
	}
	public Bitmap getMEI() {
		return MEI;
	}
	public void setMEI(Bitmap mEI) {
		MEI = mEI;
	}
	public ArrayList<Bitmap> getFrames() {
		return frames;
	}
	public void setFrames(ArrayList<Bitmap> frames) {
		this.frames = frames;
	} 
}
