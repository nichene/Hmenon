package com.ufrpe.hmenon.turisticpoint.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.ufrpe.hmenon.infrastructure.dao.DAO;
import com.ufrpe.hmenon.infrastructure.dao.Helper;
import com.ufrpe.hmenon.turisticpoint.domain.History;
import com.ufrpe.hmenon.turisticpoint.domain.TouristicPoint;

import java.util.ArrayList;

public class TouristicPointDao extends DAO{
    private TouristicPointDao(){
    }
    private static final TouristicPointDao instance = new TouristicPointDao();

    public static TouristicPointDao getInstance(){
        return instance;
    }

    public void insert(TouristicPoint point){
        open();
        ContentValues values = new ContentValues();
        values.put(Helper.TOURISTICPOINT_NAME, point.getName());
        values.put(Helper.TOURISTICPOINT_RESUME, point.getHistory().getResume());
        values.put(Helper.TOURISTICPOINT_HISTORY, point.getHistory().getCompleteHistory());
        values.put(Helper.TOURISTICPOINT_IMAGE, point.getImage());
        getDb().insert(Helper.TABLE_TOURISTICPOINT, null, values);
        close();
    }
    public ArrayList<TouristicPoint> returnAll(ArrayList<TouristicPoint> points){
        open();
        Cursor cursor = getDb().query(Helper.TABLE_TOURISTICPOINT, new String[]{Helper.TOURISTICPOINT_ID, Helper.TOURISTICPOINT_NAME, Helper.TOURISTICPOINT_RESUME, Helper.TOURISTICPOINT_HISTORY, Helper.TOURISTICPOINT_IMAGE}, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                TouristicPoint point = new TouristicPoint();
                History history = new History();
                point.setHistory(history);
                point.setId(cursor.getLong(0));
                point.setName(cursor.getString(1));
                point.setHistoryResume(cursor.getString(2));
                point.setHistoryText(cursor.getString(3));
                point.setImage(cursor.getString(4));
                points.add(point);
            } while (cursor.moveToNext());
        }
        close();
        return points;
    }
    public void reset(){
        open();
        getDb().execSQL("drop table if exists " + Helper.TABLE_TOURISTICPOINT);
        getDb().execSQL("create table " + Helper.TABLE_TOURISTICPOINT + "(" +
                Helper.TOURISTICPOINT_ID + " integer primary key autoincrement," +
                Helper.TOURISTICPOINT_NAME + " text not null," +
                Helper.TOURISTICPOINT_RESUME + " text not null," +
                Helper.TOURISTICPOINT_HISTORY + " text not null," +
                Helper.TOURISTICPOINT_IMAGE + " text not null);");
        close();
    }
}
