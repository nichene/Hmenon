package com.ufrpe.hmenon.usuarioservice;

import android.content.Context;

import com.ufrpe.hmenon.dominio.Usuario;
import com.ufrpe.hmenon.usuariodao.UsuarioDAO;

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
