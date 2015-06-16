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

    /**
     * Recupera um ponto turístico do banco de dados a partir de um id da tabela do banco e retorna
     * uma instância de TouristicPoint ou null, caso o 'id' não exista no banco de dados.
     *
     * @param id - Id de alguma linha do banco de dados contendo um ponto turístico.
     * @return - Ponto turístico como instância da classe TouristicPoint.
     */
    public TouristicPoint getTouristicPointById(long id) {
        return dao.getPointFromId(id);
    }
}
