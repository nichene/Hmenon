package com.ufrpe.hmenon.turisticpoint;

import android.content.ContentValues;
import android.database.Cursor;
import com.ufrpe.hmenon.infrastructure.dao.DAO;
import com.ufrpe.hmenon.infrastructure.dao.Helper;

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
        values.put(Helper.TURISTICPOINT_NAME, point.getName());
        values.put(Helper.TURISTICPOINT_RESUME, point.getHistory().getResume());
        values.put(Helper.TURISTICPOINT_HISTORY, point.getHistory().getCompleteHistory());
        getDb().insert(Helper.TABLE_TURISTICPOINT, null, values);
        close();
    }
    public ArrayList<TouristicPoint> returnAll(ArrayList<TouristicPoint> points){
        open();
        Cursor cursor = getDb().query(Helper.TABLE_TURISTICPOINT, new String[]{Helper.TURISTICPOINT_ID, Helper.TURISTICPOINT_NAME, Helper.TURISTICPOINT_RESUME, Helper.TURISTICPOINT_HISTORY}, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                TouristicPoint point = new TouristicPoint();
                History history = new History();
                point.setHistory(history);
                point.setId(cursor.getLong(0));
                point.setName(cursor.getString(1));
                point.setHistoryResume(cursor.getString(2));
                point.setHistoryText(cursor.getString(3));
                points.add(point);
            } while (cursor.moveToNext());
        }
        close();
        return points;
    }
    public void reset(){
        open();
        getDb().execSQL("drop table if exists "+ Helper.TABLE_TURISTICPOINT);
        getDb().execSQL("create table "+ Helper.TABLE_TURISTICPOINT +"(" +
                Helper.TURISTICPOINT_ID +" integer primary key autoincrement," +
                Helper.TURISTICPOINT_NAME+ " text not null,"+
                Helper.TURISTICPOINT_RESUME +" text not null," +
                Helper.TURISTICPOINT_HISTORY +" text not null);");
        close();
    }
}
