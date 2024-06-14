package com.example.mathterminology;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbLike extends SQLiteOpenHelper {
    private static final String DB_NAME = "likedb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mycourses";
    private static final String ID_COL = "id";
    private static final String TRACKS_COL = "tracks";
    private static final String TEST_COL1 = "Test";

    // creating a constructor for our database handler.
    public DbLike(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TRACKS_COL + " TEXT,"
                + TEST_COL1 + " TEXT)";
        db.execSQL(query);
    }
    public void addNewCourse(String courseTest, String courseTracks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TRACKS_COL, courseTracks);
        values.put(TEST_COL1, courseTest);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<LakeModel> readCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<LakeModel> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(new LakeModel(
                        cursorCourses.getString(1),
                        cursorCourses.getString(2)
                ));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return courseModalArrayList;
    }

    public void deleteCourse(String courseName) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "name=?", new String[]{courseName});
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

