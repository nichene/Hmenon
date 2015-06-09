package com.ufrpe.hmenon.turisticpoint;

import android.content.Context;

import java.util.ArrayList;

public class TouristicPointBusiness {

    public TouristicPointBusiness(Context context){
        dao.setUpAttributes(context);
    }

    private TouristicPointDao dao = TouristicPointDao.getInstance();

    public void checkInsert(TouristicPoint point){
        dao.insert(point);
    }
    public ArrayList<TouristicPoint> checkGetAll(ArrayList<TouristicPoint> points){
        dao.returnAll(points);
        return points;
    }
    public void checkReset(){
        dao.reset();
    }
}
