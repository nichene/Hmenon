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
        } else if (dao.search(user.getEmail()) != null) {
            exception.append("Esse Usuário ja está cadastrado");
        } else if (!user.getEmail().contains("@")){
            exception.append("Email Inválido");
        } else {
            dao.insert(user);
            StaticUser.setUser(user);
        }

        if (exception.length() > 0) {
            throw new Exception(exception.toString());
        }
    }
    public void checkDelete(User user, String password) throws Exception{
        StringBuilder exception = new StringBuilder();
        if (dao.search(user.getEmail())== null){
            exception.append("Erro ao deletar, usuário não existe");
        } else if (!user.getPassword().equals(password)){
            exception.append("Senha Incorreta");
        } else {
            dao.delete(user);
        }
        if (exception.length() > 0){
            throw new Exception(exception.toString());
        }
    }

    public void checkNameUpdate(String name, String confirmedName, String password) throws Exception{
        StringBuilder exception = new StringBuilder();
        User user = StaticUser.getUser();
        if (!name.equals(confirmedName)){
            exception.append("Os nomes não estão equivalentes!");
        } else if (dao.search(name) != null){
            exception.append("O nome já está sendo usado");
        } else if (!user.getPassword().equals(password)){
            exception.append("Senha Incorreta");
        } else {
            dao.updateName(name);
            StaticUser.getUser().setName(name);
        }
        if (exception.length() > 0){
            throw new Exception(exception.toString());
        }

    }

    public void checkPasswordUpdate(String password, String confirmedPassword, String oldPassword) throws Exception{
        StringBuilder exception = new StringBuilder();
        User user = StaticUser.getUser();
        if (!password.equals(confirmedPassword)) {
            exception.append("As senhas não estão equivalentes!");
        } else if (!user.getPassword().equals(oldPassword)){
            exception.append("Senha Incorreta");
        }
        else {
            dao.updatePassword(password);
        }
        if (exception.length() > 0){
            throw new Exception(exception.toString());
        }

    }

    public void checkLogin(User user) throws Exception{
        StringBuilder exception = new StringBuilder();
        User u = dao.search(user.getEmail(), user.getPassword());
        if (u == null){
            exception.append("Email ou Senha inválida");
        } else {
            StaticUser.setUser(u);
        }
        if (exception.length() > 0){
            throw new Exception(exception.toString());
        }
    }
}

