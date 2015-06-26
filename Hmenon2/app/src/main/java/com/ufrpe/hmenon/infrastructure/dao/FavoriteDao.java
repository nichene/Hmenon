package com.ufrpe.hmenon.infrastructure.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.domain.User;

import java.util.ArrayList;

/**
 * Classe responsável por manipular sua respectiva tabela no banco de dados a partir das tabelas de
 * Usuários e Pontos turísticos.
 *
 * Possui métodos para inserção, remoção e consulta da sua tabela no banco de dados.
 */
public class FavoriteDao extends DAO {
    private FavoriteDao() {}
    private static final FavoriteDao INSTANCE = new FavoriteDao();
    public static FavoriteDao getInstance() { return INSTANCE; }

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
    public ArrayList<String> getAllFavouritePointsIds(long userId){
        ArrayList<String> pointsIds = new ArrayList<>();
        open();
        Cursor cursor = getDb().rawQuery("select " + Helper.FAVOURITE_POINT_ID + " from " + Helper.TABLE_FAVOURITE
                + " where " + Helper.FAVOURITE_USER_ID
                + " = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()){
            do {
                pointsIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        close();
        return  pointsIds;
    }
}

