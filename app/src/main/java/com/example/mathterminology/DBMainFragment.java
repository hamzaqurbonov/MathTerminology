package com.example.mathterminology;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBMainFragment extends SQLiteOpenHelper {

    private static final String DB_NAME = "DBMain";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mycourses";
    private static final String ID_COL = "id";
    private static final String translate = "tracks";
    private static final String word = "Test";

    // creating a constructor for our database handler.
    public DBMainFragment(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + translate + " TEXT,"
                + word + " TEXT)";
        db.execSQL(query);
    }
    public void addNewCourse(String courseTest, String courseTracks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(ID_COL, id);
        values.put(translate, courseTracks);
        values.put(word, courseTest);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<MainFragmentModel> readCourses() {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<MainFragmentModel> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                int Id = Integer.parseInt(cursorCourses.getString(0));
                String courseTracks = cursorCourses.getString(1);
                String courseTest = cursorCourses.getString(2);
//                courseModalArrayList.add(new HistoryModel(
//                        cursorCourses.getString(1),
//                        cursorCourses.getString(2)
//                ));

                courseModalArrayList.add(new MainFragmentModel(Id, courseTracks, courseTest));
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


    public ArrayList<MainFragmentModel> searchCourses(String query) {
        ArrayList<MainFragmentModel> searchList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL сўрови, жадвал номи "mycourses" деб тўғрилаш
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE LOWER(" + word + ") LIKE ? OR LOWER(" + translate + ") LIKE ?", new String[]{"%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%"});
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + TEST_COL1 + " = ? OR " + TRACKS_COL + " = ?", new String[]{query, query});
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + word + " LIKE ?", new String[]{"%" + query + "%"});
        if (cursor.moveToFirst()) {
            do {
//                Log.d("demo43", "Test: " + cursor.getString(cursor.getColumnIndex(TEST_COL1)) + ", Tracks: " + cursor.getString(cursor.getColumnIndex(TRACKS_COL)));
                int Id = cursor.getInt(cursor.getColumnIndex(ID_COL)); // ID олиш
                String courseTest = cursor.getString(cursor.getColumnIndex(word));
                String courseTracks = cursor.getString(cursor.getColumnIndex(translate));

                // Натижаларни рўйхатга қўшиш
                searchList.add(new MainFragmentModel(Id, courseTest, courseTracks));
            } while (cursor.moveToNext());
        }
        Log.d("demo43", "onDataChange3 " + query + " " + searchList );
        cursor.close();
        return searchList; // Қидирув натижаларини қайтариш
    }
}