package com.ufrpe.hmenon.touristicpoint.service;

import android.content.Context;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.dao.TouristicPointDAO;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * Classe responsável pela consulta à tabela de pontos turísticos no banco de dados.
 */
public class TouristicPointBusiness {

    public TouristicPointBusiness(Context context){
        dao.setUpAttributes(context);
    }

    private TouristicPointDAO dao = TouristicPointDAO.getInstance();

    /**
     * Insere uma lista de pontos turísticos no banco de dados caso este esteja vazio.
     *
     * @param list <code>ArrayList</code> contendo todos os pontos turísticos a serem inseridos.
     */
    public void checkInsert(ArrayList<TouristicPoint> list){
        if (isEmpty()){
            for (TouristicPoint point : list){
                dao.insert(point);
            }
        }
    }

    public void checkUpdateChecked(String name, int newChecked){
        try {
            dao.updateChecked(name, newChecked);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Recupera uma lista contendo todos os pontos turísticos cadastrados no banco de dados.
     *
     * @return ArrayList contendo todos os pontos cadastrados.
     */
    public ArrayList<TouristicPoint> checkGetAll(){
        return dao.returnAll();
    }

    /**
     * Verifica se a tabela de pontos turísticos no banco de dados se encontra vazia.
     *
     * @return Booleano referente ao banco estar vazio ou não.
     */
    private boolean isEmpty(){
        return dao.isEmpty();
    }

    /**
     * Recupera um ponto turístico do banco de dados a partir de um id da tabela e o retorna como
     * uma instância da classe {@link TouristicPoint} ou <code>'null'</code>, caso o
     * <code>id</code> não esteja cadastrado no banco.
     *
     * @param cipherMessage String a ser descriptografado.
     * @return Ponto turístico como instância da classe {@link TouristicPoint}, caso exista no
     * banco.
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
