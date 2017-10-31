package com.example.android.contact_share;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ROHAN on 17-10-2017.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME="contact.db";
    public static final int DATABASE_VERSION=2;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String Favourite_TABLE= "CREATE TABLE " +
                Contract.entry.TABLE_NAME+ " (" +
                Contract.entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Contract.entry.COLUMN_NAME + " TEXT NOT NULL UNIQUE,"+
                Contract.entry.COLUMN_NUMBER + " UNIQUE  "+
                ");";
        Log.i("SQl ",Favourite_TABLE);
        sqLiteDatabase.execSQL(Favourite_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP THE TABLE IF EXIST"+Contract.entry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    SQLiteDatabase db=this.getWritableDatabase();

}
