package com.ufrpe.hmenon.user.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.ufrpe.hmenon.infrastructure.dao.DAO;
import com.ufrpe.hmenon.infrastructure.dao.Helper;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
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
        values.put(Helper.USER_NAME, user.getName());
        values.put(Helper.USER_PASSWORD, user.getPassword());
        getDb().insert(Helper.TABLE_USER, null, values);
        close();
    }

    public void delete(User user){
        open();
        getDb().delete(Helper.TABLE_USER, Helper.USER_NAME + " = ?", new String[]{user.getName()});
        close();

    }

    public User search(String name){
        open();
        Cursor cursor = getDb().rawQuery("select * from " + Helper.TABLE_USER
                + " where " + Helper.USER_NAME
                + " = ?", new String[]{name});
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

    public void updateName(String name){
        open();
        User user = StaticUser.getUser();
        ContentValues values = new ContentValues();
        values.put(Helper.USER_NAME, name);
        getDb().update(Helper.TABLE_USER, values, Helper.USER_NAME + " = ?", new String[]{user.getName()});
        close();
    }

    public void updatePassword(String password){
        open();
        User user = StaticUser.getUser();
        ContentValues values = new ContentValues();
        values.put(Helper.USER_PASSWORD, password);
        getDb().update(Helper.TABLE_USER, values, Helper.USER_NAME + " = ?", new String[]{user.getName()});
    }

    public User search(String name, String password){
        open();
        Cursor cursor = getDb().rawQuery("select * from " + Helper.TABLE_USER
                + " where " + Helper.USER_NAME
                + " = ? and " + Helper.USER_PASSWORD + " = ?", new String[]{name, password});
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
