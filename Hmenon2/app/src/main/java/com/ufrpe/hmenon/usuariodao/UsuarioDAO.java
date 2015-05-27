package com.ufrpe.hmenon.usuariodao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.hmenon.dominio.Usuario;

public class UsuarioDAO {
    private SQLiteDatabase db;
    private Helper helper;

    public UsuarioDAO(Context context){
        helper = new Helper(context);
    }

    public void addAll(){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Helper.COLUMN_NOME, "Lucas");
        values.put(Helper.COLUMN_EMAIL, "lucas@email.com");
        values.put(Helper.COLUMN_SENHA, "000000");
        ContentValues values1 = new ContentValues();
        values1.put(Helper.COLUMN_NOME, "Nichene");
        values1.put(Helper.COLUMN_EMAIL, "nichene@email.com");
        values1.put(Helper.COLUMN_SENHA, "111111");
        ContentValues values2 = new ContentValues();
        values2.put(Helper.COLUMN_NOME, "Ricardo");
        values2.put(Helper.COLUMN_EMAIL, "ricardo@email.com");
        values2.put(Helper.COLUMN_SENHA, "222222");
        db.insert(Helper.TABLE_USUARIO, null, values);
        db.insert(Helper.TABLE_USUARIO, null, values1);
        db.insert(Helper.TABLE_USUARIO, null, values2);
        db.close();
    }

    public Usuario buscar(String nome){
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Helper.TABLE_USUARIO
        + " where "+ Helper.COLUMN_NOME
        + " like ?", new String[]{nome});
        Usuario usuario = null;
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            usuario = new Usuario();
            usuario.setId(cursor.getLong(0));
            usuario.setNome(cursor.getString(1));
            usuario.setEmail(cursor.getString(2));
            usuario.setSenha(cursor.getString(3));
        }
        db.close();
        return usuario;
    }
}
