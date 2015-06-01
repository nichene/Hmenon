package com.ufrpe.hmenon.user.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.hmenon.infrastructure.dao.Helper;
import com.ufrpe.hmenon.user.domain.User;


public class UserDAO {
    private SQLiteDatabase db;
    private Helper helper;

    public UserDAO(Context context){
        helper = new Helper(context);
    }

    public void insert(User user){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Helper.COLUMN_NOME, user.getNome());
        values.put(Helper.COLUMN_SENHA, user.getSenha());
        db.insert(Helper.TABLE_USUARIO, null, values);
        db.close();
    }

    public void delete(User user){
        db = helper.getWritableDatabase();
        db.delete(Helper.TABLE_USUARIO, Helper.COLUMN_NOME + " = ?", new String[]{user.getNome()});
        db.close();

    }

    public User search(String nome){
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Helper.TABLE_USUARIO
                + " where "+ Helper.COLUMN_NOME
                + " = ?", new String[]{nome});
        User user = null;
        if (cursor.moveToFirst()){
            user = new User();
            user.setId(cursor.getLong(0));
            user.setNome(cursor.getString(1));
            user.setSenha(cursor.getString(2));
        }
        db.close();
        return user;
    }

    public User search(String nome, String senha){
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Helper.TABLE_USUARIO
        + " where "+ Helper.COLUMN_NOME
        + " = ? and " + Helper.COLUMN_SENHA + " = ?", new String[]{nome, senha});
        User user = null;
        if (cursor.moveToFirst()){
            user = new User();
            user.setId(cursor.getLong(0));
            user.setNome(cursor.getString(1));
            user.setSenha(cursor.getString(2));
        }
        db.close();
        return user;
    }
}
