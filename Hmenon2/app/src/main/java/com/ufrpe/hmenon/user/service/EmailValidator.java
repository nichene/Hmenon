package com.ufrpe.hmenon.user.service;
import java.util.regex.*;

/**
 * Verifica a validade do campo de texto <code>E-mail</code>.
 * <p>
 * @author marcio.pulcinelli
 */
public class EmailValidator
{
    /**
     * Verifica através de uma expressão regular se o string é um e-mail válido.
     *
     * @param email String a ser verificado.
     * @return Booleano referente a se o string é válido ou não.
     */
    public static boolean validar(String email)
    {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
}