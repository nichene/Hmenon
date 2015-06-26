package com.ufrpe.hmenon.infrastructure.domain;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;

public class FavouritePoint {
    private TouristicPoint point;

    public TouristicPoint getPoint() {
        return point;
    }

    public void setPoint(TouristicPoint point) {
        this.point = point;
    }
}
