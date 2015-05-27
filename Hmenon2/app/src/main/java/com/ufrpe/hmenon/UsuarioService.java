package com.ufrpe.hmenon;

import android.content.Context;

/**
 * Created by Ricardo on 26/05/2015.
 */
public class UsuarioService {
    private UsuarioDAO dao;

    public UsuarioService(Context context) {
        dao = new UsuarioDAO(context);
    }

    public void validarCadastro(){
        dao.addAll();
    }


    public boolean validarLogin(String nome, String senha){
        boolean confirmado;
        Usuario usuario = dao.buscar(nome);
        try{
            if (usuario.getSenha().equals(senha)){
                confirmado = true;
            }
            else {
                confirmado = false;
            }
        }
        catch (NullPointerException n){
            confirmado = false;
        }
        return confirmado;
    }
}
