package com.ufrpe.hmenon.touristicpoint.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.ufrpe.hmenon.infrastructure.dao.DAO;
import com.ufrpe.hmenon.infrastructure.dao.Helper;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import java.util.ArrayList;

/**
 * Realiza as operações de consulta de pontos turísticos no banco de dados.
 */
public class TouristicPointDAO extends DAO{
    private TouristicPointDAO(){}

    private static final TouristicPointDAO instance = new TouristicPointDAO();

    public static TouristicPointDAO getInstance(){
        return instance;
    }

    /**
     * Recupera ponto turístico armazenado no <code>cursor</code>.
     *
     * @param cursor Objeto <code>cursor</code> com o ponto turístico recuperado do banco de dados.
     * @return Ponto turístico do <code>cursor</code> como instância da classe TouristicPoint.
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
        point.setChecked(cursor.getInt(8));
        point.setCoordinates(cursor.getString(9));

        return point;
    }

    /**
     * Insere o ponto turístico fornecido na sua tabela no banco de dados.
     *
     * @param point Ponto turístico a ser inserido.
     */
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
        values.put(Helper.TOURISTICPOINT_CHECKED, point.getChecked());
        values.put(Helper.TOURISTICPOINT_COORDINATES, point.getCoordinates());
        getDb().insert(Helper.TABLE_TOURISTICPOINT, null, values);
        close();
    }

    public void updateChecked(String name, int newChecked){
        open();
        ContentValues values = new ContentValues();
        values.put(Helper.TOURISTICPOINT_CHECKED, newChecked);
        getDb().update(Helper.TABLE_TOURISTICPOINT, values, Helper.TOURISTICPOINT_NAME +" = ?", new String[]{name});
        close();
    }

    /**
     * Recupera do banco todos os pontos turísticos cadastrados.
     *
     * @return <code>ArrayList</code> contendo todos os pontos turísticos inseridos no banco de
     * dados.
     */
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
                Helper.TOURISTICPOINT_CHECKED,
                Helper.TOURISTICPOINT_COORDINATES}, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                points.add(recoverPointFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        close();
        return points;
    }

    /**
     * Verifica se a tabela dos pontos turísticos no banco de dados se encontra vazia.
     *
     * @return <code>Booleano</code> referente ao banco estar vazio ou não.
     */
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
        cursor.close();
        close();
        return empty;
    }

    /**
     * Recupera um ponto turístico do banco de dados a partir de do id no qual o ponto foi
     * armazenado.
     *
     * @param id Id da linha requisitada.
     * @return Instância de {@link TouristicPoint} referente à linha requisitada.
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
                        Helper.TOURISTICPOINT_CHECKED,
                        Helper.TOURISTICPOINT_COORDINATES},
                Helper.TOURISTICPOINT_ID + "=?", new String[]{ String.valueOf(id) },
                null, null, null);

        if (cursor.moveToFirst()) {
            return recoverPointFromCursor(cursor);
        }
        return null;
    }

    /**
     * Recupera um ponto turístico do banco de dados a partir de do nome no qual o ponto foi
     * armazenado.
     *
     * @param nome Nome do ponto turístico a ser consultado no banco de dados.
     * @return Instância de {@link TouristicPoint} referente à linha requisitada
     */
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
                        Helper.TOURISTICPOINT_CHECKED,
                        Helper.TOURISTICPOINT_COORDINATES},
                Helper.TOURISTICPOINT_NAME + "=?", new String[]{nome},
                null, null, null);

        if (cursor.moveToFirst()) {
            return recoverPointFromCursor(cursor);
        }
        return null;
    }
}