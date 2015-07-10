package com.ufrpe.hmenon.infrastructure.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe que serve de base para todas as implementações de DAO do projeto.
 */
public abstract class DAO {
    /**
     * Instância do <code>OpenHelper</code> da classe.
     */
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

    /**
     * Abre comunicação para escrita com o banco de dados.
     */
    public void open(){
        setDb(getHelper().getWritableDatabase());
    }

    /**
     * Fecha a comunicação com o banco de dados.
     */
    public void close(){
        getDb().close();
    }
}
