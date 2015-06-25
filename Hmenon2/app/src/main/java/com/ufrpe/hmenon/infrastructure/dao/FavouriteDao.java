package com.ufrpe.hmenon.infrastructure.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.domain.User;


/**
 * Classe responsável por manipular sua respectiva tabela no banco de dados a partir das tabelas de
 * Usuários e Pontos turísticos.
 *
 * Possui métodos para inserção, remoção e consulta da sua tabela no banco de dados.
 */
public class FavouriteDao extends DAO {
    private FavouriteDao() {}
    private static final FavouriteDao INSTANCE = new FavouriteDao();
    public static FavouriteDao getInstance() { return INSTANCE; }

    /**
     * Insere novo favorito no banco de dados.
     *
     * @param user Usuário que adiciona ponto turístico a lista de favoritos.
     * @param point Ponto turístico a ser marcado como favorito.
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
     * Remove o ponto turístico da lista de favoritos.
     *
     * @param user Usuário que requesita a remoção do ponto turístico da lista de favoritos.
     * @param point Ponto turístico a ser removido da lista.
     * @return Inteiro indicando o número de linhas removidas no banco de dados.
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
     * Remove do banco de dados todos os favoritos do usuário fornecido.
     *
     * @param user Usuário cujos favoritos devem ser removidos.
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
     * @param user Usuário cujos favoritos serão consultados.
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
     * Retorna o número de entradas na tabela de favoritos para um usuário específico.
     *
     * @param user Usuário cujo número de favoritos deve ser consultado.
     * @return Quantidade de linhas na tabela de favoritos registrado relacionadas ao usuário
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
     * Retorna o número de entradas na tabela de favoritos.
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
