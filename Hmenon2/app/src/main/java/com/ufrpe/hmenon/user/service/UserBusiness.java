package com.ufrpe.hmenon.user.service;

import android.content.Context;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.dao.UserDAO;
import com.ufrpe.hmenon.user.domain.User;

public class UserBusiness {
    private UserDAO dao = UserDAO.getInstance();

    public UserBusiness(Context context) {
        dao.setUpAttributes(context);
    }

    public void checkSignUp(User user, String confirmed) throws Exception {
        StringBuilder exception = new StringBuilder();
        if (!user.getSenha().equals(confirmed)) {
            exception.append("As senhas não estão equivalentes!");
        } else if (dao.search(user.getNome()) != null) {
            exception.append("Esse Usuário ja está cadastrado");
        } else {
            dao.insert(user);
            StaticUser.setUser(user);
        }

        if (exception.length() > 0) {
            throw new Exception(exception.toString());
        }
    }
    public void checkDelete(User user){
        dao.delete(user);
    }


    public void checkLogin(User user) throws Exception{
        StringBuilder exception = new StringBuilder();
        if (dao.search(user.getNome(), user.getSenha()) == null){
            exception.append("Usuário ou Senha inválida");
        } else {
            StaticUser.setUser(user);
        }
        if (exception.length() > 0){
            throw new Exception(exception.toString());
        }
    }
}
