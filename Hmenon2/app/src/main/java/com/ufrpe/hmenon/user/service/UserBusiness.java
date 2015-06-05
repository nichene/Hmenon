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
        if (!user.getPassword().equals(confirmed)) {
            exception.append("As senhas não estão equivalentes!");
        } else if (dao.search(user.getName()) != null) {
            exception.append("Esse Usuário ja está cadastrado");
        } else {
            dao.insert(user);
            StaticUser.setUser(user);
        }

        if (exception.length() > 0) {
            throw new Exception(exception.toString());
        }
    }
    public void checkDelete(User user) throws Exception{
        StringBuilder exception = new StringBuilder();
        if (dao.search(user.getName())== null){
            exception.append("Erro ao deletar, usuário não existe");
        } else {
            dao.delete(user);
        }
        if (exception.length() > 0){
            throw new Exception(exception.toString());
        }
    }

    public void checkNameUpdate(String name, String confirmedName) throws Exception{
        StringBuilder exception = new StringBuilder();
        if (!name.equals(confirmedName)){
            exception.append("Os nomes não estão equivalentes!");
        } else if (dao.search(name) != null){
            exception.append("O nome já está sendo usado");
        } else {
            dao.updateName(name);
            StaticUser.getUser().setName(name);
        }

        if (exception.length() > 0){
            throw new Exception(exception.toString());
        }

    }

    public void checkPasswordUpdate(String password, String confirmedPassword) throws Exception{
        StringBuilder exception = new StringBuilder();
        if (!password.equals(confirmedPassword)) {
            exception.append("As senhas não estão equivalentes!");
        } else {
            dao.updatePassword(password);
        }
        if (exception.length() > 0){
            throw new Exception(exception.toString());
        }

    }

    public void checkLogin(User user) throws Exception{
        StringBuilder exception = new StringBuilder();
        if (dao.search(user.getName(), user.getPassword()) == null){
            exception.append("Usuário ou Senha inválida");
        } else {
            StaticUser.setUser(user);
        }
        if (exception.length() > 0){
            throw new Exception(exception.toString());
        }
    }
}
