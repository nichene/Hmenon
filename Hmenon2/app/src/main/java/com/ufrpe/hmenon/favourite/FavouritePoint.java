package com.ufrpe.hmenon.favourite;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.domain.User;

/**
 * Utilizada para manter uma ligação entre o ponto turístico a ser consultado e o usuário a quem o
 * ponto se relaciona no banco de dados.
 */
public class FavouritePoint {
    private User user;
    private TouristicPoint point;

    public User getUser() { return user; }
    public TouristicPoint getPoint() {
        return point;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setPoint(TouristicPoint point) {
        this.point = point;
    }
}
