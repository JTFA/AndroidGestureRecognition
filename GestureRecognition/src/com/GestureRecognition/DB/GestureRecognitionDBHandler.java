package com.GestureRecognition.DB;

import java.util.ArrayList;
import java.util.List;

import com.GestureRecognition.VideoInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GestureRecognitionDBHandler extends SQLiteOpenHelper {

	private static final String VIDEO_INFO_TABLE = "Video_Info";
	private static final String KEY = "id";
	private static final String TITLE = "title";
	private static final String MHI = "MHI";
	private static final String LASTMOD = "date_last_modified";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "GRDB";
	public GestureRecognitionDBHandler(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VIDEO_INFO_TABLE = "CREATE TABLE " + VIDEO_INFO_TABLE + "("
                + KEY + " INTEGER PRIMARY KEY," + TITLE + " TEXT,"
                + LASTMOD + " TEXT," +MHI + " BLOB" + ")";
        //Log.d("drop","drop");
        
        db.execSQL(CREATE_VIDEO_INFO_TABLE);
        
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        
    	db.execSQL("DROP TABLE IF EXISTS " +VIDEO_INFO_TABLE);
 
        // Create tables again
        onCreate(db);
    }
    public void deleteVideoInfo(VideoInfo vi) {
    	
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VIDEO_INFO_TABLE, KEY + " = ?",
                new String[] { String.valueOf(vi.getID()) });
        db.close();
    }
    public void addVideoInfo(VideoInfo vi) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(TITLE, vi.getTitle()); // Contact Name
        values.put(LASTMOD, vi.getLastModifiedDate()); // Contact Phone Number
        values.put(KEY,vi.getID());
       
        // Inserting Row
        db.insert(VIDEO_INFO_TABLE, null, values);
        db.close(); // Closing database connection
    }
   
    // Getting All Contacts
 public List<VideoInfo> getAllContacts() {
    List<VideoInfo> contactList = new ArrayList<VideoInfo>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + VIDEO_INFO_TABLE;
 
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
 
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
        	VideoInfo vi = new VideoInfo();
        	vi.setTitle(cursor.getString(1));
        	vi.setLastModifiedDate(cursor.getString(2));
            // Adding contact to list
            contactList.add(vi);
        } while (cursor.moveToNext());
    }
 
    // return contact list
    return contactList;
}
    public VideoInfo getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        Cursor cursor = db.query(VIDEO_INFO_TABLE, new String[] { KEY,
                TITLE, LASTMOD }, KEY + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        VideoInfo vid = new VideoInfo(cursor.getString(1), cursor.getString(2));
        vid.setID(Integer.parseInt(cursor.getString(0)));
        // return contact
        return vid;
    }

}
