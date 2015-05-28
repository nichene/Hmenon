package com.ufrpe.hmenon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class UsuarioDAO {
    private SQLiteDatabase db;
    private Helper helper;

    public UsuarioDAO(Context context){
        helper = new Helper(context);
    }

    public void inserir(Usuario usuario){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Helper.COLUMN_NOME, usuario.getNome());
        values.put(Helper.COLUMN_SENHA, usuario.getSenha());
        db.insert(Helper.TABLE_USUARIO, null, values);
        db.close();
    }


    public Usuario buscar(String nome){
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Helper.TABLE_USUARIO
        + " where "+ Helper.COLUMN_NOME
        + " like ?", new String[]{nome});
        Usuario usuario = null;
        if (cursor.moveToFirst()){
            usuario = new Usuario();
            usuario.setId(cursor.getLong(0));
            usuario.setNome(cursor.getString(1));
            usuario.setSenha(cursor.getString(2));
        }
        db.close();
        return usuario;
    }
}
