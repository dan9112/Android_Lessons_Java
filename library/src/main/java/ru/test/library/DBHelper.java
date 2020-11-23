package ru.test.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "libraryDb";
    public static final String TABLE_CONTACTS = "bookshelf";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_SERIA = "seria";
    public static final String KEY_NUM = "num";
    public static final String KEY_READ = "read";

    final String LOG_TAG = "myLogs";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(LOG_TAG, "--- onCreate database ---");

        // создаем таблицу с полями
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_AUTHOR + " text," + KEY_SERIA + " text," + KEY_NUM + " integer," + KEY_READ + " numeric" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);

        onCreate(db);
    }
}
