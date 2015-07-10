package com.ufrpe.hmenon.favourite.domain;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.domain.User;

/**
 * Utilizada para manter uma ligação entre o ponto turístico a ser consultado e o usuário a quem o
 * ponto se relaciona no banco de dados.
 * <p>
 * Apenas implementa getters e setters.
 */
public class FavouritePoint {
    /**
     * Usuário cujo ponto turístico se relaciona.
     */
    private User user;
    /**
     * Ponto turístico cujo usuário se relaciona.
     */
    private TouristicPoint point;

    public User getUser() { return user; }

    public TouristicPoint getPoint() { return point; }

    public void setUser(User user) {
        this.user = user;
    }
    public void setPoint(TouristicPoint point) {
        this.point = point;
    }
}
