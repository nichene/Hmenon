package com.ufrpe.hmenon.infrastructure.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper{
    private static final String NAMEDB = "hmenon";
    private static final int VERSIONDB = 9;
    public static final String TABLE_USER = "user";
    public static final String USER_ID = "_user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String TABLE_TURISTICPOINT = "turistic_point";
    public static final String TURISTICPOINT_ID = "_turistic_point_id";
    public static final String TURISTICPOINT_NAME = "turistic_point_name";
    public static final String TURISTICPOINT_RESUME = "turistic_point_resume";
    public static final String TURISTICPOINT_HISTORY = "turistic_point_history";


    public Helper(Context context){
        super(context, NAMEDB, null, VERSIONDB);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_USER +"(" +
                USER_ID +" integer primary key autoincrement," +
                USER_NAME +" text not null," +
                USER_PASSWORD +" text not null);");
        db.execSQL("create table "+ TABLE_TURISTICPOINT +"(" +
                TURISTICPOINT_ID +" integer primary key autoincrement," +
                TURISTICPOINT_NAME+ " text not null,"+
                TURISTICPOINT_RESUME +" text not null," +
                TURISTICPOINT_HISTORY +" text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ TABLE_USER);
        db.execSQL("drop table if exists "+ TABLE_TURISTICPOINT);
        onCreate(db);
    }
}
