package com.ufrpe.hmenon.touristicpoint.service;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public abstract class CryptoHelper {

    private static final String ALGORITHM = "AES";
    private static final String MODE = "ECB";
    private static final String PADDING = "PKCS5Padding";

    public static final String STRING_KEY = "QABG0eSxEO2hzNldjfBHaQ==";
    private static SecretKey secretKey = null;
    private static Cipher cipher = null;

    /**
     * Seta o cifrador a partir do algoritmo, modo e padding.
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
    private static void setPrivateKeyFromString() {
        byte[] byteKey = Base64.decode(STRING_KEY, Base64.DEFAULT);
        secretKey = new SecretKeySpec(byteKey, 0, byteKey.length, ALGORITHM);
    }

    /**
     * Verifica se os atributos secretKey e cipher se encontram inicializados e os inicia caso
     * necessário.
     */
    private static void checkKeyAndCipherInitialization() {
        try {
            if (secretKey == null) {
                setPrivateKeyFromString();
            }
            if (cipher == null) {
                setupCipher();
            }
        }
        catch(NoSuchAlgorithmException | NoSuchPaddingException except) {
            except.printStackTrace();
        }
    }

    /**
     * Criptografa um String (não é utilizado na aplicação).
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

        checkKeyAndCipherInitialization();

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

        checkKeyAndCipherInitialization();

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] byteCipher = Base64.decode(stringCipher, Base64.DEFAULT);
        byte[] newPlainText = cipher.doFinal(byteCipher);

        return new String(newPlainText, "UTF8");
    }
}
