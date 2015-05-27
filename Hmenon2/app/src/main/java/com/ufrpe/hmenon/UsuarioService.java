package com.ufrpe.hmenon;

import android.content.Context;
import android.widget.Toast;

public class UsuarioService {
    private UsuarioDAO dao;

    public UsuarioService(Context context) {
        dao = new UsuarioDAO(context);
    }

    public void validarCadastro(Usuario usuario){
        if (dao.buscar(usuario.getNome()) != null){
            dao.inserir(usuario);
        }
        else {
            Toast.makeText(MainLogin.getContext(), "Esse Usuário já está cadastrado!", Toast.LENGTH_LONG).show();
        }
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
