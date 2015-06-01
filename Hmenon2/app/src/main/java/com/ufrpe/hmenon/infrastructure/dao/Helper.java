package com.ufrpe.hmenon.infrastructure.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class Helper extends SQLiteOpenHelper{
    private static final String NOMEDB = "hmenon";
    private static final int VERSAODB = 4;
    public static final String TABLE_USUARIO = "usuario";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_SENHA = "senha";


    public Helper(Context context){
        super(context, NOMEDB, null, VERSAODB);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_USUARIO+"(" +
                COLUMN_ID+" integer primary key autoincrement," +
                COLUMN_NOME+" text not null," +
                COLUMN_SENHA+" text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+TABLE_USUARIO);
        onCreate(db);
    }
}
