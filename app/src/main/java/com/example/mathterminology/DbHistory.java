package com.example.mathterminology;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHistory extends SQLiteOpenHelper {

    private static final String DB_NAME = "coursedb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mycourses";
    private static final String ID_COL = "id";
    private static final String TRACKS_COL = "tracks";
    private static final String TEST_COL1 = "Test";

    // creating a constructor for our database handler.
    public DbHistory(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TRACKS_COL + " TEXT,"
                + TEST_COL1 + " TEXT)";
        db.execSQL(query);
    }
    public void addNewCourse(String courseTest, String courseTracks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(ID_COL, id);
        values.put(TRACKS_COL, courseTracks);
        values.put(TEST_COL1, courseTest);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<HistoryModel> readCourses() {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<HistoryModel> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                int Id = Integer.parseInt(cursorCourses.getString(0));
                String courseTracks = cursorCourses.getString(1);
                String courseTest = cursorCourses.getString(2);
//                courseModalArrayList.add(new HistoryModel(
//                        cursorCourses.getString(1),
//                        cursorCourses.getString(2)
//                ));

                courseModalArrayList.add(new HistoryModel(Id, courseTracks, courseTest));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return courseModalArrayList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public  void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }


    public void deleteSelect(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{id});
        db.close();
    }
}