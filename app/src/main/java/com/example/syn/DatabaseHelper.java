package com.example.syn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String VOCABULARY_TABLE = "VOCABULARY_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_WORDS = "WORDS";
    public static final String COLUMN_MEANS = "MEANS";
    public static final String COLUMN_LENGTH = "LENGTH";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "vocabulary.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + VOCABULARY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_WORDS + " TEXT, " + COLUMN_MEANS + " TEXT," + COLUMN_LENGTH + " INTEGER)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlQuery = ("DROP TABLE IF EXISTS " + VOCABULARY_TABLE)  ;
        db.execSQL(sqlQuery);
        onCreate(db);
    }

    public boolean addOne(Vocabulary voca) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, voca.name);
        cv.put(COLUMN_WORDS, voca.words);
        cv.put(COLUMN_MEANS, voca.means);
        cv.put(COLUMN_LENGTH, voca.length);
        long insert = db.insert(VOCABULARY_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        return true;
    }

    public ArrayList<Vocabulary> getAllVocabulary() {
        String sqlQuery = "SELECT * FROM " + VOCABULARY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(sqlQuery, null);
        ArrayList<Vocabulary> listVoca = new ArrayList<>();
        if (cur != null) {
            if (cur.moveToFirst()){
                do {
                    // Passing values
                    int id = cur.getInt(0);
                    String name = cur.getString(1);
                    String words = cur.getString(2);
                    String means = cur.getString(3);
                    int length = cur.getInt(4);
                    Vocabulary vocabulary = new Vocabulary(id, name, words, means, length);
                    listVoca.add(vocabulary);
                    // Do something Here with values
                } while(cur.moveToNext());
            }
        }
        cur.close();
        db.close();
        return listVoca;
    }

    public Vocabulary getVocabulary(int id) {
        String sqlQuery = "SELECT * FROM " + VOCABULARY_TABLE + " WHERE ID = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(sqlQuery, null);
        Vocabulary vocabulary = null;
        if (cur != null) {
            if (cur.moveToFirst()){
                do {
                    // Passing values
                    String name = cur.getString(1);
                    String words = cur.getString(2);
                    String means = cur.getString(3);
                    int length = cur.getInt(4);
                    vocabulary = new Vocabulary(id, name, words, means, length);
                    // Do something Here with values
                } while(cur.moveToNext());
            }
        }
        cur.close();
        db.close();
        return vocabulary;
    }

    public boolean updateVocabulary(int id, Vocabulary voca) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, voca.name);
        cv.put(COLUMN_WORDS, voca.words);
        cv.put(COLUMN_MEANS, voca.means);
        cv.put(COLUMN_LENGTH, voca.length);
        long insert = db.update(VOCABULARY_TABLE, cv, "ID = " + id, null);
        if (insert == -1) {
            return false;
        }
        return true;
    }

    public boolean deleteVocabulary(Vocabulary voca) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "DELETE FROM " + VOCABULARY_TABLE + " WHERE ID = " +voca.id;

        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }
}
