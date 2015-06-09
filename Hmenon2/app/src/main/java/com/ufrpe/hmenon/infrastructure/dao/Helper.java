package com.ufrpe.hmenon.infrastructure.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper{
    private static final String NAMEDB = "hmenon";
    private static final int VERSIONDB = 11;
    public static final String TABLE_USER = "user";
    public static final String USER_ID = "_user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String TABLE_TOURISTICPOINT = "touristic_point";
    public static final String TOURISTICPOINT_ID = "_touristic_point_id";
    public static final String TOURISTICPOINT_NAME = "touristic_point_name";
    public static final String TOURISTICPOINT_RESUME = "touristic_point_resume";
    public static final String TOURISTICPOINT_HISTORY = "touristic_point_history";
    public static final String TOURISTICPOINT_IMAGE = "touristic_point_image";
    public static final String TOURISTICPOINT_ACTIVITYTEXT = "touristic_point_activity_text";


    public Helper(Context context){
        super(context, NAMEDB, null, VERSIONDB);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_USER +"(" +
                USER_ID +" integer primary key autoincrement," +
                USER_NAME +" text not null," +
                USER_PASSWORD +" text not null);");
        db.execSQL("create table "+ TABLE_TOURISTICPOINT +"(" +
                TOURISTICPOINT_ID +" integer primary key autoincrement," +
                TOURISTICPOINT_NAME + " text not null,"+
                TOURISTICPOINT_RESUME +" text not null," +
                TOURISTICPOINT_HISTORY +" text not null," +
                TOURISTICPOINT_IMAGE + " text not null," +
                TOURISTICPOINT_ACTIVITYTEXT + " text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ TABLE_USER);
        db.execSQL("drop table if exists "+ TABLE_TOURISTICPOINT);
        onCreate(db);
    }
}
