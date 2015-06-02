package com.ufrpe.hmenon.infrastructure.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class Helper extends SQLiteOpenHelper{
    private static final String NAMEDB = "hmenon";
    private static final int VERSIONDB = 6;
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";


    public Helper(Context context){
        super(context, NAMEDB, null, VERSIONDB);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_USER +"(" +
                COLUMN_ID+" integer primary key autoincrement," +
                COLUMN_NAME +" text not null," +
                COLUMN_PASSWORD +" text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ TABLE_USER);
        onCreate(db);
    }
}
