package com.ufrpe.hmenon.user.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.ufrpe.hmenon.infrastructure.dao.DAO;
import com.ufrpe.hmenon.infrastructure.dao.Helper;
import com.ufrpe.hmenon.user.domain.User;


public class UserDAO extends DAO{
    private UserDAO(){
    }

    private static final UserDAO instance = new UserDAO();

    public static UserDAO getInstance() {
        return instance;
    }

    public void insert(User user){
        open();
        ContentValues values = new ContentValues();
        values.put(Helper.COLUMN_NAME, user.getNome());
        values.put(Helper.COLUMN_PASSWORD, user.getSenha());
        getDb().insert(Helper.TABLE_USER, null, values);
        close();
    }

    public void update(User user){
        open();
        ContentValues values = new ContentValues();
        values.put(Helper.COLUMN_NAME, user.getNome());
        values.put(Helper.COLUMN_PASSWORD, user.getSenha());

        getDb().update(Helper.TABLE_USER,values,Helper.COLUMN_ID + " = ?", new String[]{
                String.valueOf(user.getId())});

        close();
    }


    public void delete(User user){
        open();
        getDb().delete(Helper.TABLE_USER, Helper.COLUMN_NAME + " = ?", new String[]{user.getNome()});
        close();

    }

    public User search(String nome){
        open();
        Cursor cursor = getDb().rawQuery("select * from " + Helper.TABLE_USER
                + " where " + Helper.COLUMN_NAME
                + " = ?", new String[]{nome});
        User user = null;
        if (cursor.moveToFirst()){
            user = new User();
            user.setId(cursor.getLong(0));
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
        }
        close();
        return user;
    }

    public User search(String name, String password){
        open();
        Cursor cursor = getDb().rawQuery("select * from " + Helper.TABLE_USER
                + " where " + Helper.COLUMN_NAME
                + " = ? and " + Helper.COLUMN_PASSWORD + " = ?", new String[]{name, password});
        User user = null;
        if (cursor.moveToFirst()){
            user = new User();
            user.setId(cursor.getLong(0));
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
        }
        close();
        return user;
    }
}
