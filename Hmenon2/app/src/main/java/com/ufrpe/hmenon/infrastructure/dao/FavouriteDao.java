package com.ufrpe.hmenon.infrastructure.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.domain.User;


/**
 * Classe respons�vel por manipular sua respectiva tabela no banco de dados a partir das tabelas de
 * Usu�rios e Pontos tur�sticos.
 *
 * Possui m�todos para inser��o, remo��o e consulta da sua tabela no banco de dados.
 */
public class FavouriteDao extends DAO {
    private FavouriteDao() {}
    private static final FavouriteDao INSTANCE = new FavouriteDao();
    public static FavouriteDao getInstance() { return INSTANCE; }

    /**
     * Insere novo favorito no banco de dados.
     *
     * @param user Usu�rio que adiciona ponto tur�stico a lista de favoritos.
     * @param point Ponto tur�stico a ser marcado como favorito.
     */
    public void insertFavourite(User user, TouristicPoint point) {
        open();

        ContentValues values = new ContentValues();
        values.put(Helper.FAVOURITE_USER_ID, user.getId());
        values.put(Helper.FAVOURITE_POINT_ID, point.getId());

        getDb().insert(Helper.TABLE_FAVOURITE, null, values);
        close();
    }

    /**
     * Remove o ponto tur�stico da lista de favoritos.
     *
     * @param user Usu�rio que requesita a remo��o do ponto tur�stico da lista de favoritos.
     * @param point Ponto tur�stico a ser removido da lista.
     * @return Inteiro indicando o n�mero de linhas removidas no banco de dados.
     */
    public int removeFavourite(User user, TouristicPoint point) {
        String clause = Helper.FAVOURITE_USER_ID + " = ? AND "
                + Helper.FAVOURITE_POINT_ID + " = ? ";

        String arguments[] = new String[]{ Long.toString(user.getId()),
                Long.toString(point.getId()) };

        open();
        int deletionCount = getDb().delete(Helper.TABLE_FAVOURITE, clause, arguments);
        close();
        return deletionCount;
    }

    /**
     * Remove do banco de dados todos os favoritos do usu�rio fornecido.
     *
     * @param user Usu�rio cujos favoritos devem ser removidos.
     */
    public int removeSingleUserFavourites(User user) {
        String clause = Helper.FAVOURITE_USER_ID + " = ? ";

        String arguments[] = new String[]{ Long.toString(user.getId()) };

        open();
        int deletionCount = getDb().delete(Helper.TABLE_FAVOURITE, clause, arguments);
        close();
        return deletionCount;
    }

    /**
     * TESTES-APENAS_LIMPA_TABELA_FAVORITOS
     */
    public void clearTableFavourites() {
        open();
        getDb().delete(Helper.TABLE_FAVOURITE, null, null);
        close();
    }

    /**
     * TESTES-IMPRIME_EM_LOGCAT_TODOS_OS_FAVORITOS_DO_USUARIO
     *
     * @param user Usu�rio cujos favoritos ser�o consultados.
     */
    public void printUserFavourites(User user) {
        open();
        String selectionQuery = "SELECT * FROM " + Helper.TABLE_FAVOURITE
                + " WHERE " + Helper.FAVOURITE_USER_ID + " = ? ";

        Cursor cursor = getDb().rawQuery(selectionQuery, new String[]{
                String.valueOf(user.getId()) });

        if (cursor.moveToFirst()) {
            do {
                Log.d("UserFav", "tabId: " + cursor.getLong(0) + ", User: " + cursor.getLong(1)
                        + ", Point_ID: " + cursor.getLong(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
    }

    /**
     * TESTES-IMPRIME-TODAS_AS_ENTRADAS_NA_TABELA_DE_FAVORITOS
     */
    public void printTableFavourites() {
        open();
        String selectionQuery = "SELECT * FROM " + Helper.TABLE_FAVOURITE;
        Cursor cursor = getDb().rawQuery(selectionQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Log.d("TableFav", "tabId: " + cursor.getLong(0) + ", User: " + cursor.getLong(1)
                        + ", Point_ID: " + cursor.getLong(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
    }

    /**
     * Retorna o n�mero de entradas na tabela de favoritos para um usu�rio espec�fico.
     *
     * @param user Usu�rio cujo n�mero de favoritos deve ser consultado.
     * @return Quantidade de linhas na tabela de favoritos registrado relacionadas ao usu�rio
     * fornecido.
     */
    public int getUserFavouriteCount(User user) {
        open();
        String countQuery = "SELECT  * FROM " + Helper.TABLE_FAVOURITE
                + " WHERE " + Helper.FAVOURITE_USER_ID + " = ? ";

        Cursor cursor = getDb().rawQuery(countQuery, new String[]{
                String.valueOf(user.getId()) });

        int counter =  cursor.getCount();
        cursor.close();
        close();
        return counter;
    }

    /**
     * Retorna o n�mero de entradas na tabela de favoritos.
     *
     * @return Quantidade de linhas na tabela de favoritos.
     */
    public int getTableFavouriteCount() {
        open();
        String countQuery = "SELECT  * FROM " + Helper.TABLE_FAVOURITE;
        Cursor cursor = getDb().rawQuery(countQuery, null);

        int counter =  cursor.getCount();
        cursor.close();
        close();
        return counter;
    }
}
