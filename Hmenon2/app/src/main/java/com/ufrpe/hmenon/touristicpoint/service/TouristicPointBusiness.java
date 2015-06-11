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

    public void checkInsert(ArrayList<TouristicPoint> list){
        if (isEmpty()){
            for (TouristicPoint point : list){
                dao.insert(point);
            }
        }
    }

    public ArrayList<TouristicPoint> checkGetAll(){
        return dao.returnAll();
    }

    private boolean isEmpty(){
        return dao.isEmpty();
    }
}
