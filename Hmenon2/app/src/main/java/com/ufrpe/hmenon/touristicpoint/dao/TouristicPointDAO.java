package com.ufrpe.hmenon.touristicpoint.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.ufrpe.hmenon.infrastructure.dao.DAO;
import com.ufrpe.hmenon.infrastructure.dao.Helper;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import java.util.ArrayList;

public class TouristicPointDAO extends DAO{
    private TouristicPointDAO(){
    }
    private static final TouristicPointDAO instance = new TouristicPointDAO();

    public static TouristicPointDAO getInstance(){
        return instance;
    }

    /**
     * Recupera ponto turístico armazenado no cursor.
     *
     * @param cursor - Objeto cursor com o ponto turístico recuperado do banco de dados.
     * @return - Ponto turístico do cursor como instância da classe TouristicPoint.
     */
    private static TouristicPoint recoverPointFromCursor(Cursor cursor) {
        TouristicPoint point = new TouristicPoint();

        point.setId(cursor.getLong(0));
        point.setName(cursor.getString(1));
        point.setHistoryResume(cursor.getString(2));
        point.setHistoryText(cursor.getString(3));
        point.setImage(cursor.getString(4));
        point.setActivityText(cursor.getString(5));
        point.setAddress(cursor.getString(6));
        point.setMap(cursor.getString(7));
        point.setCoordinates(cursor.getString(8));

        return point;
    }
    public void insert(TouristicPoint point){
        open();
        ContentValues values = new ContentValues();
        values.put(Helper.TOURISTICPOINT_NAME, point.getName());
        values.put(Helper.TOURISTICPOINT_RESUME, point.getHistory().getResume());
        values.put(Helper.TOURISTICPOINT_HISTORY, point.getHistory().getCompleteHistory());
        values.put(Helper.TOURISTICPOINT_IMAGE, point.getImage());
        values.put(Helper.TOURISTICPOINT_ACTIVITYTEXT, point.getActivityText());
        values.put(Helper.TOURISTICPOINT_ADDRESS, point.getAddress());
        values.put(Helper.TOURISTICPOINT_MAP, point.getMap());
        values.put(Helper.TOURISTICPOINT_COORDINATES, point.getCoordinates());
        getDb().insert(Helper.TABLE_TOURISTICPOINT, null, values);
        close();
    }
    public ArrayList<TouristicPoint> returnAll(){
        open();
        ArrayList<TouristicPoint> points = new ArrayList<>();
        Cursor cursor = getDb().query(Helper.TABLE_TOURISTICPOINT, new String[]{
                Helper.TOURISTICPOINT_ID,
                Helper.TOURISTICPOINT_NAME,
                Helper.TOURISTICPOINT_RESUME,
                Helper.TOURISTICPOINT_HISTORY,
                Helper.TOURISTICPOINT_IMAGE,
                Helper.TOURISTICPOINT_ACTIVITYTEXT,
                Helper.TOURISTICPOINT_ADDRESS,
                Helper.TOURISTICPOINT_MAP,
                Helper.TOURISTICPOINT_COORDINATES}, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                points.add(recoverPointFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        close();
        return points;
    }
    public boolean isEmpty(){
        open();
        boolean empty;
        Cursor cursor = getDb().query(Helper.TABLE_TOURISTICPOINT, new String[]{
                Helper.TOURISTICPOINT_ID,
                Helper.TOURISTICPOINT_NAME,
                Helper.TOURISTICPOINT_RESUME,
                Helper.TOURISTICPOINT_HISTORY,
                Helper.TOURISTICPOINT_IMAGE,
                Helper.TOURISTICPOINT_ACTIVITYTEXT,
                Helper.TOURISTICPOINT_ADDRESS,
                Helper.TOURISTICPOINT_MAP,
                Helper.TOURISTICPOINT_COORDINATES}, null, null, null, null, null);
        empty = !cursor.moveToFirst();
        close();
        return empty;
    }

    /**
     * Recupera um ponto turístico do banco de dados a partir de do id no qual o ponto turístico foi
     * armazenado.
     *
     * @param id - Id da linha requisitada.
     * @return - Instância de TouristicPoint referente à linha requisitada
     */
    public TouristicPoint getPointFromId(long id) {
        this.open();
        Cursor cursor = getDb().query(Helper.TABLE_TOURISTICPOINT, new String[]{
                        Helper.TOURISTICPOINT_ID,
                        Helper.TOURISTICPOINT_NAME,
                        Helper.TOURISTICPOINT_RESUME,
                        Helper.TOURISTICPOINT_HISTORY,
                        Helper.TOURISTICPOINT_IMAGE,
                        Helper.TOURISTICPOINT_ACTIVITYTEXT,
                        Helper.TOURISTICPOINT_ADDRESS,
                        Helper.TOURISTICPOINT_MAP,
                        Helper.TOURISTICPOINT_COORDINATES},
                Helper.TOURISTICPOINT_ID + "=?", new String[]{ String.valueOf(id) },
                null, null, null);

        if (cursor.moveToFirst()) {
            return recoverPointFromCursor(cursor);
        }
        return null;
    }
    public TouristicPoint getPointFromName(String nome) {
        this.open();
        Cursor cursor = getDb().query(Helper.TABLE_TOURISTICPOINT, new String[]{
                        Helper.TOURISTICPOINT_NAME,
                        Helper.TOURISTICPOINT_ID,
                        Helper.TOURISTICPOINT_RESUME,
                        Helper.TOURISTICPOINT_HISTORY,
                        Helper.TOURISTICPOINT_IMAGE,
                        Helper.TOURISTICPOINT_ACTIVITYTEXT,
                        Helper.TOURISTICPOINT_ADDRESS,
                        Helper.TOURISTICPOINT_MAP,
                        Helper.TOURISTICPOINT_COORDINATES},
                Helper.TOURISTICPOINT_NAME + "=?", new String[]{nome},
                null, null, null);

        if (cursor.moveToFirst()) {
            return recoverPointFromCursor(cursor);
        }
        return null;
    }
}