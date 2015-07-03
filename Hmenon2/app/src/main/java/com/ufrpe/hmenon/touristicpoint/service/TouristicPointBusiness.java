package com.ufrpe.hmenon.touristicpoint.service;

import android.content.Context;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.dao.TouristicPointDAO1;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class TouristicPointBusiness {

    public TouristicPointBusiness(Context context){
        dao.setUpAttributes(context);
    }

    private TouristicPointDAO1 dao = TouristicPointDAO1.getInstance();

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
     * Recupera um ponto turístico do banco de dados a partir de um id da tabela e o retorna como
     * uma instância da classe TouristicPoint ou 'null', caso o id não esteja cadastrado no banco.
     *
     * @param cipherMessage - String a ser descriptografado.
     * @return - Ponto turístico como instância da classe TouristicPoint, caso exista no banco.
     *
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public TouristicPoint getTouristicPointByIdEncrypted(String cipherMessage) throws InvalidKeyException,
    IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        long id = Long.parseLong(CryptoHelper.decryptString(cipherMessage));
        return dao.getPointFromId(id);
    }
    public TouristicPoint getTouristicPointById(String id){
        return dao.getPointFromId(Long.parseLong(id));
    }
}
