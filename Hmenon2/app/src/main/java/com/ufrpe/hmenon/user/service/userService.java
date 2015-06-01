package com.ufrpe.hmenon.user.service;

import android.content.Context;
import android.widget.Toast;

import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.dao.UserDAO;
import com.ufrpe.hmenon.user.domain.User;
import com.ufrpe.hmenon.user.gui.MainLogin;

public class userService {
    private UserDAO dao;

    StaticUser sUser = new StaticUser();

    public userService(Context context) {
        dao = new UserDAO(context);
    }

    public User checkSignUp(User user){
        User u = dao.search(user.getNome());
        if (u == null){
            dao.insert(user);
            StaticUser.setUser(user);
            Toast.makeText(MainLogin.getContext(), "Cadastro realizados com sucesso!", Toast.LENGTH_LONG).show();
            return user;

        }
        else {
            Toast.makeText(MainLogin.getContext(), "Esse Usuário já está cadastrado!", Toast.LENGTH_LONG).show();
            return null;


        }


    }

    public void checkDelete(User user){
        dao.delete(user);
    }


    public User checkLogin(String nome, String senha){

        User user = dao.search(nome, senha);

        StaticUser.setUser(user);

        return user;
    }
}
