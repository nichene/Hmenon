package com.ufrpe.hmenon.favourite.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.ufrpe.hmenon.favourite.domain.FavouritePoint;
import com.ufrpe.hmenon.infrastructure.dao.DAO;
import com.ufrpe.hmenon.infrastructure.dao.Helper;
import com.ufrpe.hmenon.user.domain.User;

import java.util.ArrayList;

/**
 * Classe responsável por manipular sua respectiva tabela no banco de dados a partir das tabelas de
 * Usuários e Pontos turísticos.
 * <p>
 * Possui métodos para inserção, remoção e consulta da sua tabela no banco de dados.
 */
public class FavoriteDAO extends DAO {
    /**
     * Restringe o acesso ao construtor padrão da classe.
     */
    private FavoriteDAO() {}

    /**
     * Instância do objeto banco de dados.
     */
    private static final FavoriteDAO INSTANCE = new FavoriteDAO();

    public static FavoriteDAO getInstance() { return INSTANCE; }

    /**
     * Verifica se o ponto já encontra-se como favorito antes de tentar inserí-lo no banco.
     *
     * @param favouritePoint Ponto turístico a ser inserido.
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
     * Remove o ponto turístico do usuário da tabela de favoritos.
     *
     * @param favouritePoint Ponto turístico a ser removido.
     */
    public void removeFavourite(FavouritePoint favouritePoint) {
        String clause = Helper.FAVOURITE_USER_ID + " = ? AND "
                + Helper.FAVOURITE_POINT_ID + " = ? ";

        String arguments[] = new String[]{ Long.toString(favouritePoint.getUser().getId()),
                Long.toString(favouritePoint.getPoint().getId()) };

        open();
        getDb().delete(Helper.TABLE_FAVOURITE, clause, arguments);
        close();
    }

    /**
     * Remove o usuário e seus pontos da tabela de favoritos no banco de dados.
     *
     * @param user Usuário cujas entradas na tabela de favoritos devem ser removidas.
     */
    public void removeUserAndFavorites(User user) {
        String clause = Helper.FAVOURITE_USER_ID + " = ? ";

        String arguments[] = new String[]{ Long.toString(user.getId()) };

        open();
        getDb().delete(Helper.TABLE_FAVOURITE, clause, arguments);
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
     * Retorna uma lista por implementação de <code>ArrayList</code> contendo todos os id's dos
     * pontos turísticos armazenados no banco de dados.
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
        cursor.close();
        close();
        return  pointsIds;
    }

    /**
     * Verifica se o ponto turístico foi marcado como favorito pelo usuário.
     *
     * @param favouritePoint Objeto que reúne ambos ponto turístico e usuário.
     * @return Booleano referente a o ponto estar marcado como favorito ou não.
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
