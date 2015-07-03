package com.ufrpe.hmenon.favourite.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.ufrpe.hmenon.favourite.domain.FavouritePoint;
import com.ufrpe.hmenon.infrastructure.dao.DAO;
import com.ufrpe.hmenon.infrastructure.dao.Helper;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.domain.User;

import java.util.ArrayList;

/**
 * Classe responsável por manipular sua respectiva tabela no banco de dados a partir das tabelas de
 * Usuários e Pontos turísticos.
 *
 * Possui métodos para inserção, remoção e consulta da sua tabela no banco de dados.
 */
public class FavoriteDAO extends DAO {
    private FavoriteDAO() {}
    private static final FavoriteDAO INSTANCE = new FavoriteDAO();
    public static FavoriteDAO getInstance() { return INSTANCE; }

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

    public void insertFavourite(FavouritePoint favouritePoint) {
        open();
        ContentValues values = new ContentValues();
        values.put(Helper.FAVOURITE_USER_ID, favouritePoint.getUser().getId());
        values.put(Helper.FAVOURITE_POINT_ID, favouritePoint.getPoint().getId());
        getDb().insert(Helper.TABLE_FAVOURITE, null, values);
        close();
    }

    /**
     * Verifica se o ponto já encontra-se como favorito antes de tentar inserí-lo no banco.
     *
     * @param favouritePoint Ponto a ser inserido.
     */
    public void safeInsertFavourite(FavouritePoint favouritePoint) {
        if (!checkIfPointIsFavourite(favouritePoint)) {
            open();
            ContentValues values = new ContentValues();
            values.put(Helper.FAVOURITE_USER_ID, favouritePoint.getUser().getId());
            values.put(Helper.FAVOURITE_POINT_ID, favouritePoint.getPoint().getId());
            getDb().insert(Helper.TABLE_FAVOURITE, null, values);
            close();
        }
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
     * Remove o ponto turístico do usuário da tabela de pontos favoritos no banco de dados.
     *
     * @param favouritePoint ponto a ser removido.
     * @return um número aí.
     */
    public int removeFavourite(FavouritePoint favouritePoint) {
        String clause = Helper.FAVOURITE_USER_ID + " = ? AND "
                + Helper.FAVOURITE_POINT_ID + " = ? ";

        String arguments[] = new String[]{ Long.toString(favouritePoint.getUser().getId()),
                Long.toString(favouritePoint.getPoint().getId()) };

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
        String countQuery = "SELECT  * FROM " + Helper.TABLE_FAVOURITE + " WHERE "
                + Helper.FAVOURITE_USER_ID + " = ? ";

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

    /**
     * Retorna uma lista por implementação de ArrayList contendo todos os id's dos objetos pontos
     * turísticos armazenados no banco de dados.
     *
     * @param userId Id do usuário a ser consultado.
     * @return Lista de Id's de todos os pontos turísticos marcados pelo usuário.
     */
    public ArrayList<String> getAllFavouritePointsIds(long userId){
        ArrayList<String> pointsIds = new ArrayList<>();
        open();

        Cursor cursor = getDb().rawQuery("select " + Helper.FAVOURITE_POINT_ID + " from "
                + Helper.TABLE_FAVOURITE + " where " + Helper.FAVOURITE_USER_ID + " = ?"
                , new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()){
            do {
                pointsIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        close();
        return  pointsIds;
    }

    /**
     * TESTE_TESTE
     * @param favouritePoint wrapper 'usuario + ponto' para remover
     * @return flag booleana
     */
    public boolean checkIfPointIsFavourite(FavouritePoint favouritePoint) {
        open();

        Cursor cursor = getDb().rawQuery("SELECT 1 FROM " + Helper.TABLE_FAVOURITE + " WHERE "
                + Helper.FAVOURITE_USER_ID + " = ? AND " + Helper.FAVOURITE_POINT_ID + " = ?"
                , new String[]{String.valueOf(favouritePoint.getUser().getId())
                , String.valueOf(favouritePoint.getPoint().getId())});

        boolean isFavouriteBool = cursor.moveToFirst();
        cursor.close();
        close();
        return isFavouriteBool;
    }
}
