package com.ufrpe.hmenon.favourite.service;

import android.content.Context;

import com.ufrpe.hmenon.favourite.dao.FavoriteDAO;
import com.ufrpe.hmenon.favourite.domain.FavouritePoint;
import com.ufrpe.hmenon.user.domain.User;

import java.util.ArrayList;

/**
 * Faz a manipulação da tabela de favoritos no banco de dados.
 * <p>
 * Possui métodos para inserção, remoção e contagem de entradas na tabela.
 */
public class FavouriteBusiness {

    private FavoriteDAO dao = FavoriteDAO.getInstance();
    public FavouriteBusiness (Context context) { dao.setUpAttributes(context); }

    /**
     * Verifica se o ponto turístico já se encontra marcado como favorito antes de tentar
     * adicioná-lo ao banco.
     *
     * @param favouritePoint Ponto turístico favorito a ser marcado.
     */
    public void markPointAsFavourite(FavouritePoint favouritePoint) {
        dao.safeInsertFavourite(favouritePoint);
    }

    /**
     * Desmarca o ponto turísrico como favorito.
     *
     * @param favouritePoint Ponto favorito a ser desmarcado.
     */
    public void removeFavourite(FavouritePoint favouritePoint) {
        dao.removeFavourite(favouritePoint);
    }

    /**
     * Remove da tabela de favoritos o usuário fornecido e todos os seus pontos turísticos.
     *
     * @param user Usuário cujas entradas na tabela de favoritos devem ser removidas.
     */
    public void removeFavourite(User user) {
        dao.removeUserAndFavorites(user);
    }

    /**
     * Recupera o número de pontos favoritos do usuário fornecido.
     *
     * @param user Usuário a ser consultado
     * @return Número de pontos favoritos.
     */
    public int getCount(User user) { return dao.getUserFavouriteCount(user); }

    /**
     * Recupera o número total de favoritos no banco de dados.
     *
     * @return Número total de linhas da tabela.
     */
    public int getCount() {
        return dao.getTableFavouriteCount();
    }

    /**
     * Recupera do banco de dados todos os pontos turísticos marcados como favoritos pelo usuário
     * registrado sobre o id passado como argumento através de um <code>ArrayList</code>.
     *
     * @param userId Id do usuário cujos favoritos deverão ser consultados.
     * @return ArrayList contendo todos os pontos turísticos marcados pelo usuário.
     */
    public ArrayList<String> getFavouritesPointsIds(long userId){
        return dao.getAllFavouritePointsIds(userId);
    }

    /**
     * Consulta o banco de dados através da classe {@link FavoriteDAO} se um ponto turístico se
     * encontra marcado como favorito por um dado usuário.
     *
     * @param favouritePoint Objeto que relaciona o ponto turístico e o usuário a serem consultados
     *                       no banco de dados
     * @return Booleano referente ao registo do ponto turístico como favorito do usuário em questão.
     */
    public boolean checkIfFavourite(FavouritePoint favouritePoint) {
        return dao.checkIfPointIsFavourite(favouritePoint);
    }
}
