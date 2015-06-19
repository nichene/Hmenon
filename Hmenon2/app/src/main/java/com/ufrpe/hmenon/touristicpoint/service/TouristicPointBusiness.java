package com.ufrpe.hmenon.touristicpoint.service;

import android.content.Context;
import android.util.Base64;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.dao.TouristicPointDao;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TouristicPointBusiness {

    private static final String ALGORITHM = "AES";
    private static final String MODE = "ECB";
    private static final String PADDING = "PKCS5Padding";

    public static final String STRING_KEY = "QABG0eSxEO2hzNldjfBHaQ==";
    private static SecretKey secretKey;
    private static Cipher cipher;

    public TouristicPointBusiness(Context context){
        dao.setUpAttributes(context);

        if ( (secretKey == null) || (cipher == null) ) {
            try {
                parseKeyFromString();
                setupCipher();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
    public TouristicPoint getTouristicPointById(String cipherMessage) throws InvalidKeyException,
    IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        long id = Long.parseLong(decryptString(cipherMessage));
        return dao.getPointFromId(id);
    }

    /**
     *
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    private static void setupCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance(ALGORITHM + '/' + MODE + '/' + PADDING);
    }

    /**
     * Decodifica a chave privada a partir do formato String para array de bytes para ser usado na
     * criptografia.
     */
    private static void parseKeyFromString() {
        byte[] byteKey = Base64.decode(STRING_KEY, Base64.DEFAULT);
        secretKey = new SecretKeySpec(byteKey, 0, byteKey.length, ALGORITHM);
    }

    /**
     * Criptografa e codifica um String base como outro String para ser utilizado no QR code, não é
     * utilizado na aplicação.
     *
     * @param id - String original a ser criptografado.
     * @return - Formatação em String do conteúdo criptografado.
     *
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encryptString(String id) throws UnsupportedEncodingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        byte[] plainText = id.getBytes("UTF8");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = cipher.doFinal(plainText);

        return Base64.encodeToString(cipherText, Base64.DEFAULT);
    }

    /**
     * Descriptografa o String fornecido.
     *
     * @param stringCipher - Codificação em utf-8 do string criptografado.
     * @return - Resultado descriptografado do string de entrada.
     *
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String decryptString(String stringCipher) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] byteCipher = Base64.decode(stringCipher, Base64.DEFAULT);
        byte[] newPlainText = cipher.doFinal(byteCipher);

        return new String(newPlainText, "UTF8");
    }
}
