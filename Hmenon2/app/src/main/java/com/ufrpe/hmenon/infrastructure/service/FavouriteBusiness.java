package com.ufrpe.hmenon.infrastructure.service;

import android.content.Context;

import com.ufrpe.hmenon.favourite.FavoriteDAO;
import com.ufrpe.hmenon.favourite.FavouritePoint;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.domain.User;

import java.util.ArrayList;

/**
 * Faz a manipulação da tabela de favoritos no banco de dados.
 *
 * Possui métodos para inserção, remoção e contagem de entradas na tabela.
 */
public class FavouriteBusiness {

    private FavoriteDAO dao = FavoriteDAO.getInstance();
    public FavouriteBusiness (Context context) { dao.setUpAttributes(context); }

    /**
     * Marca o ponto turístico como favorito.
     *
     * @param user Usuário que adiciona o ponto turistico como favorito.
     * @param point Ponto turístico a ser marcado como favorito.
     */
    public void markPointAsFavourite(User user, TouristicPoint point) {
        dao.insertFavourite(user, point);
    }

    /**
     * Verifica se o ponto turístico já se encontra marcado como favorito antes de tentar
     * adicioná-lo ao banco.
     * @param favouritePoint Ponto turístico favorito a ser marcado.
     */
    public void markPointAsFavourite(FavouritePoint favouritePoint) {
        dao.safeInsertFavourite(favouritePoint);
    }

    /**
     * Desmarca o ponto turístico como favorito.
     *
     * @param user Usuário que remove o ponto turístico da lista de favoritos.
     * @param point Ponto turístico a ser desmarcado como favorito.
     */
    public void removeFavourite(User user, TouristicPoint point) {
        dao.removeFavourite(user, point);
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
     * Remove do banco de dados de favoritos o usuário fornecido e seus pontos turísticos.
     *
     * @param user Usuário cujos favoritos devem ser removidos.
     */
    public void removeFavourite(User user) {
        dao.removeSingleUserFavourites(user);
    }


    /**
     * TESTES-LIMPA_TABELA_DE_FAVORITOS
     */
    public void clearFavourites() { dao.clearTableFavourites(); }

    /**
     * Conta o número de pontos favoritos do usuário fornecido.
     *
     * @param user Usuário a ser consultado
     * @return Número de pontos favoritos.
     */
    public int getCount(User user) { return dao.getUserFavouriteCount(user); }

    /**
     * Conta o número de linhas da tabela no banco de dados.
     *
     * @return Número de linhas da tabela.
     */
    public int getCount() {
        return dao.getTableFavouriteCount();
    }

    public ArrayList<String> getFavouritesPointsIds(long userId){
        return dao.getAllFavouritePointsIds(userId);
    }

    /**
     * Consulta o banco de dados através da classe FavouritePointDAO se um ponto turístico se
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
