package com.ufrpe.hmenon.touristicpoint.service;

import android.content.Context;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.dao.TouristicPointDao;

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
