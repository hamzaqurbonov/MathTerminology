package com.example.mathterminology;

import android.annotation.SuppressLint;
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
    private static final String WORD = "word";
    private static final String TRANSLATE = "translate";

    // creating a constructor for our database handler.
    public DBMainFragment(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORD + " TEXT,"
                + TRANSLATE + " TEXT)";
        db.execSQL(query);
    }
    public void addNewCourse(String word, String translate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(ID_COL, id);
        values.put(WORD, word);
        values.put(TRANSLATE, translate);
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
                String word = cursorCourses.getString(1);
                String translate = cursorCourses.getString(2);
//                courseModalArrayList.add(new HistoryModel(
//                        cursorCourses.getString(1),
//                        cursorCourses.getString(2)
//                ));

                courseModalArrayList.add(new MainFragmentModel(Id, word, translate));
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

        // Мос келадиган қидириш учун аниқ мослик
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + WORD + " LIKE ?", new String[]{ query.trim() + "%"});   // query.trim() пробелни йўқ қилади. "%" ўхшашларини топади
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + WORD + " LIKE ?", new String[]{query.trim()});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int Id = cursor.getInt(cursor.getColumnIndex(ID_COL));
                @SuppressLint("Range") String word = cursor.getString(cursor.getColumnIndex(WORD));
                @SuppressLint("Range") String translate = cursor.getString(cursor.getColumnIndex(TRANSLATE));

                searchList.add(new MainFragmentModel(Id, word, translate));
            } while (cursor.moveToNext());
        }
        Log.d("demo45", "Қидирув сўрови: " + query);
        Log.d("demo45", "Натижалар сони: " + searchList.size());
        cursor.close();
        return searchList;
    }
}
