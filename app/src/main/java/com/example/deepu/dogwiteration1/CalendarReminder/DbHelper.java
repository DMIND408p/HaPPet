package com.example.deepu.dogwiteration1.CalendarReminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import compactcalendarview.domain.Event;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by qingyunhe on 17/09/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dailyRecord.db";
    private static final String KEY_NAME = "time_in_millis";
    private static final String COLOR = "color";
    private static final String WALK_DOG= "walk_dog";
    private static final String GROOMING= "grooming";
    private static final String DIARY_TEXT= "diary_text";
    private static final String DIARY_PIC_1= "diary_pic1";
    private static final String DIARY_PIC_2= "diary_pic2";
    private static final String DIARY_PIC_3= "diary_pic3";
    private static final String FEEDING_TEXT= "feeding_text";
    private static final String FEEDING_PIC_1= "feeding_pic1";
    private static final String FEEDING_PIC_2= "feeding_pic2";
    private static final String FEEDING_PIC_3= "feeding_pic3";
    private static final String TABLE_NAME = "daily_record";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    KEY_NAME + " INTEGER PRIMARY KEY," +
                    COLOR + " INTEGER," +
                    WALK_DOG + " INTEGER," +
                    GROOMING + " INTEGER," +
                    DIARY_TEXT + " TEXT," +
                    FEEDING_TEXT + " TEXT," +
                    DIARY_PIC_1 + " BLOB," +
                    DIARY_PIC_2 + " BLOB," +
                    DIARY_PIC_3 + " BLOB," +
                    FEEDING_PIC_1 + " BLOB," +
                    FEEDING_PIC_2 + " BLOB," +
                    FEEDING_PIC_3 + " BLOB" +
                    ");";
    private final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL(SQL_DELETE_ENTRIES);
        // create new table
        onCreate(db);
    }

    public void addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if (1==event.getWalk_dog()){
            values.put(WALK_DOG,event.getWalk_dog());
        }
        if (1==event.getGrooming()){
            values.put(GROOMING,event.getGrooming());
        }
        if (null!=event.getDiary_pic1()){
            byte[] bytes = DbBitmapUtility.getBytes(event.getDiary_pic1());
            values.put(DIARY_PIC_1,bytes);
        }
        if (null!=event.getDiary_pic2()){
            byte[] bytes = DbBitmapUtility.getBytes(event.getDiary_pic2());
            values.put(DIARY_PIC_2,bytes);
        }
        if (null!=event.getDiary_pic3()){
            byte[] bytes = DbBitmapUtility.getBytes(event.getDiary_pic3());
            values.put(DIARY_PIC_3,bytes);
        }

        if (null!=event.getFeeding_pic1()){
            byte[] bytes = DbBitmapUtility.getBytes(event.getFeeding_pic1());
            values.put(FEEDING_PIC_1,bytes);
        }
        if (null!=event.getFeeding_pic2()){
            byte[] bytes = DbBitmapUtility.getBytes(event.getFeeding_pic2());
            values.put(FEEDING_PIC_2,bytes);
        }
        if (null!=event.getFeeding_pic3()){
            byte[] bytes = DbBitmapUtility.getBytes(event.getFeeding_pic3());
            values.put(FEEDING_PIC_3,bytes);
        }
        if(null!=event.getDiary_text()){
            values.put(DIARY_TEXT,event.getDiary_text());
        }
        if(null!=event.getFeeding_text()){
            values.put(FEEDING_TEXT,event.getFeeding_text());
        }

        values.put(KEY_NAME, event.getTime_in_millis());
        values.put(COLOR, event.getColor());


        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    /**
     * get event by time_in_millis
     * @param time_in_millis
     * @return Event
     */
    public Event getEvent(Long time_in_millis){
        SQLiteDatabase db = this.getReadableDatabase();
       // String sql =   "SELECT * FROM "+TABLE_NAME+" WHERE "+;
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_NAME,
                        COLOR, WALK_DOG, GROOMING,DIARY_TEXT,FEEDING_TEXT,DIARY_PIC_1,DIARY_PIC_2,DIARY_PIC_3,
                        FEEDING_PIC_1,FEEDING_PIC_2,FEEDING_PIC_3}, KEY_NAME + "=?",
                new String[] { String.valueOf(time_in_millis) }, null, null, null, null);
        if (cursor.getCount()<=0){
            return null;
        }else {
            cursor.moveToFirst();
            Event event = new Event();
            event.setTime_in_millis(Long.parseLong(cursor.getString(0)));
            event.setColor(Integer.parseInt(cursor.getString(1)));
            if (null!=cursor.getString(2)){
                Integer walk_dog = Integer.parseInt(cursor.getString(2));//walk_dog
                event.setWalk_dog(walk_dog);
            }
            if (null!=cursor.getString(3)){
                Integer grooming = Integer.parseInt(cursor.getString(3));//grooming
                event.setGrooming(grooming);
            }
            if (null!=cursor.getString(4)){
                event.setDiary_text(cursor.getString(4));
            }
            if (null!=cursor.getString(5)) {
                event.setFeeding_text(cursor.getString(5));
            }
            if (null != cursor.getBlob(6)) {
                event.setDiary_pic1(DbBitmapUtility.getImage(cursor.getBlob(6)));
            }
            if (null != cursor.getBlob(7)) {
                event.setDiary_pic2(DbBitmapUtility.getImage(cursor.getBlob(7)));
            }
            if (null != cursor.getBlob(8)) {
                event.setDiary_pic3(DbBitmapUtility.getImage(cursor.getBlob(8)));
            }
            if (null != cursor.getBlob(9)) {
                event.setFeeding_pic1(DbBitmapUtility.getImage(cursor.getBlob(9)));
            }
            if (null != cursor.getBlob(10)) {
                event.setFeeding_pic2(DbBitmapUtility.getImage(cursor.getBlob(10)));
            }
            if (null != cursor.getBlob(11)) {
                event.setFeeding_pic3(DbBitmapUtility.getImage(cursor.getBlob(11)));
            }
            Log.d(TAG, "Get event by: "+time_in_millis+" GET EVENT: " + event);
            return event;
        }
    }

    public List<Event> getAllEvents(){
        List<Event> eventList= new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setTime_in_millis(Long.parseLong(cursor.getString(0)));
                event.setColor(Integer.parseInt(cursor.getString(1)));
                if (null!=cursor.getString(2)){
                    Integer walk_dog = Integer.parseInt(cursor.getString(2));//walk_dog
                    event.setWalk_dog(walk_dog);
                }
                if (null!=cursor.getString(3)){
                    Integer grooming = Integer.parseInt(cursor.getString(3));//grooming
                    event.setGrooming(grooming);
                }
                if (null!=cursor.getString(4)){
                    event.setDiary_text(cursor.getString(4));
                }
                if (null!=cursor.getString(5)) {
                    event.setFeeding_text(cursor.getString(5));
                }
                if (null != cursor.getBlob(6)) {
                    event.setDiary_pic1(DbBitmapUtility.getImage(cursor.getBlob(6)));
                }
                if (null != cursor.getBlob(7)) {
                    event.setDiary_pic2(DbBitmapUtility.getImage(cursor.getBlob(7)));
                }
                if (null != cursor.getBlob(8)) {
                    event.setDiary_pic3(DbBitmapUtility.getImage(cursor.getBlob(8)));
                }
                if (null != cursor.getBlob(9)) {
                    event.setFeeding_pic1(DbBitmapUtility.getImage(cursor.getBlob(9)));
                }
                if (null != cursor.getBlob(10)) {
                    event.setFeeding_pic2(DbBitmapUtility.getImage(cursor.getBlob(10)));
                }
                if (null != cursor.getBlob(11)) {
                    event.setFeeding_pic3(DbBitmapUtility.getImage(cursor.getBlob(11)));
                }

                // Adding contact to list
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        // return contact list
        return eventList;
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single event
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WALK_DOG,event.getWalk_dog());
        values.put(GROOMING,event.getGrooming());
        if(event.getDiary_pic1()!=null){
            values.put(DIARY_PIC_1,DbBitmapUtility.getBytes(event.getDiary_pic1()));
        }else{
            values.put(DIARY_PIC_1, (byte[]) null);
        }
        if(event.getDiary_pic2()!=null){
            values.put(DIARY_PIC_2,DbBitmapUtility.getBytes(event.getDiary_pic2()));
        }else{
            values.put(DIARY_PIC_2, (byte[]) null);
        }
        if(event.getDiary_pic3()!=null){
            values.put(DIARY_PIC_3,DbBitmapUtility.getBytes(event.getDiary_pic3()));
        }else{
            values.put(DIARY_PIC_3, (byte[]) null);
        }
        if(event.getFeeding_pic1()!=null){
            values.put(FEEDING_PIC_1,DbBitmapUtility.getBytes(event.getFeeding_pic1()));
        }else {
            values.put(FEEDING_PIC_1, (byte[]) null);
        }
        if (event.getFeeding_pic2()!=null) {
            values.put(FEEDING_PIC_2,DbBitmapUtility.getBytes(event.getFeeding_pic2()));
        }else {
            values.put(FEEDING_PIC_2,(byte[]) null);
        }
        if (event.getFeeding_pic3() != null) {
            values.put(FEEDING_PIC_3,DbBitmapUtility.getBytes(event.getFeeding_pic3()));
        }else {
            values.put(FEEDING_PIC_3,(byte[]) null);
        }
        values.put(DIARY_TEXT,event.getDiary_text());
        values.put(FEEDING_TEXT,event.getFeeding_text());

        // updating row
        return db.update(TABLE_NAME, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(event.getTime_in_millis()) });
    }

    // Deleting single event
    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + " = ?",
                new String[] { String.valueOf(event.getTime_in_millis()) });
        db.close();
    }
}
