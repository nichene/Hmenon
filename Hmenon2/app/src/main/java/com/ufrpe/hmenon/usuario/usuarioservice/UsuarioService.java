package com.ufrpe.hmenon.usuario.usuarioservice;

import android.content.Context;
import android.widget.Toast;

import com.ufrpe.hmenon.usuario.usuariogui.MainLogin;
import com.ufrpe.hmenon.usuario.dominio.Usuario;
import com.ufrpe.hmenon.usuario.usuariodao.UsuarioDAO;

public class UsuarioService {
    private UsuarioDAO dao;

    public UsuarioService(Context context) {
        dao = new UsuarioDAO(context);
    }

    public boolean validarCadastro(Usuario usuario){
        Usuario u = dao.buscar(usuario.getNome());
        boolean confirmado;
        if (u == null){
            dao.inserir(usuario);
            confirmado = true;
        }
        else {
            Toast.makeText(MainLogin.getContext(), "Esse Usuário já está cadastrado!", Toast.LENGTH_LONG).show();
            confirmado = false;

        }
        return confirmado;

    }


    public boolean validarLogin(String nome, String senha){
        boolean confirmado;
        Usuario usuario = dao.buscar(nome, senha);
        confirmado = usuario != null;
        return confirmado;
    }
}
