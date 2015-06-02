package com.ufrpe.hmenon.infrastructure.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAO {
    private static Helper helper;
    private static SQLiteDatabase db;
    private Context context;

    public Helper getHelper() {
        return helper;
    }
    private void setHelper(Helper helper) {
        this.helper = helper;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
    private void setDb(SQLiteDatabase db) {
        this.db = db;
    }
    public Context getContext() {
        return context;
    }
    private void setContext(Context context) {
        this.context = context;
    }

    public void setUpAttributes(Context context){
        helper = new Helper(context);
        this.context = context;
    }

    public void open(){
        setDb(getHelper().getWritableDatabase());
    }
    public void close(){
        getDb().close();
    }
}
