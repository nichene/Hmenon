package com.ufrpe.hmenon.infrastructure.service;

import android.content.Context;

import com.ufrpe.hmenon.infrastructure.dao.FavouriteDao;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.domain.User;

/**
 * Faz a manipulação da tabela de favoritos no banco de dados.
 *
 * Possui métodos para inserção, remoção e contagem de entradas na tabela.
 */
public class FavouriteBusiness {

    private FavouriteDao dao = FavouriteDao.getInstance();
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
     * APENAS PARA TESTES.
     *
     * @param user Usuário cujos id's dos pontos turísticos favoritos devem ser consultados.
     */
    public void printAllFavsUnderUser(User user) {
        dao.getUserFavouritesIDs(user);
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
     * Conta o número de linhas da tabela no banco de dados.
     *
     * @return Número de linhas da tabela.
     */
    public int getCount() {
        return dao.getFavouriteCount();
    }
}
