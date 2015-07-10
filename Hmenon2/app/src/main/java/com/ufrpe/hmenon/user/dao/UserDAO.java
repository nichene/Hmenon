package com.ufrpe.hmenon.user.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.ufrpe.hmenon.infrastructure.dao.DAO;
import com.ufrpe.hmenon.infrastructure.dao.Helper;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.domain.User;

/**
 * Faz a consulta e escrita de usuários em sua respectiva tabela no banco de dados.
 */
public class UserDAO extends DAO{
    /**
     * Restringindo acesso ao construtor padrão.
     */
    private UserDAO(){}

    /**
     * Instância da classe para acesso ao DAO.
     */
    private static final UserDAO instance = new UserDAO();

    public static UserDAO getInstance() {
        return instance;
    }

    /**
     * Insere os usuário passado como argumento no banco de dados.
     *
     * @param user Objeto usuário a ser inserido no banco.
     */
    public void insert(User user){
        open();
        ContentValues values = new ContentValues();
        values.put(Helper.USER_EMAIL, user.getEmail());
        values.put(Helper.USER_NAME, user.getName());
        values.put(Helper.USER_PASSWORD, user.getPassword());
        getDb().insert(Helper.TABLE_USER, null, values);
        close();
    }

    /**
     * Remove se possível o usuário do banco de dados através do atributo <code>email</code>.
     *
     * @param user Usuário a ser removido do banco.
     */
    public void delete(User user){
        open();
        getDb().delete(Helper.TABLE_USER, Helper.USER_EMAIL + " = ?", new String[]{user.getEmail()});
        close();
    }

    /**
     * Consulta o banco de dados por usuários cadastrados com o e-mail fornecido, e o retorna como
     * uma instância da classe {@link User} ou retorna <code>null</code> caso contrário.
     *
     * @param email Chave utilizada para procura no banco de dados.
     * @return Usuário como instância da classe {@link User} ou <code>null</code> caso não existir
     * no banco de dados.
     */
    public User search(String email){
        open();
        Cursor cursor = getDb().rawQuery("select * from " + Helper.TABLE_USER
                + " where " + Helper.USER_EMAIL
                + " = ?", new String[]{email});
        User user = null;
        if (cursor.moveToFirst()){
            user = new User();
            user.setId(cursor.getLong(0));
            user.setEmail(cursor.getString(1));
            user.setName(cursor.getString(2));
            user.setPassword(cursor.getString(3));
        }
        cursor.close();
        close();
        return user;
    }

    /**
     * Atualiza no banco de dados o nome do usuário logado.
     *
     * @param name Novo atributo nome do usuário logado a ser atualizado no banco de dados.
     */
    public void updateName(String name){
        open();
        User user = StaticUser.getUser();
        ContentValues values = new ContentValues();
        values.put(Helper.USER_NAME, name);

        getDb().update(Helper.TABLE_USER, values, Helper.USER_EMAIL + " = ?",
                new String[]{user.getEmail()});

        close();
    }

    /**
     * Atualiza no banco de dados a senha do usuário logado.
     *
     * @param password Novo atributo senha do usuário logado a ser atualizado no banco de dados.
     */
    public void updatePassword(String password){
        open();
        User user = StaticUser.getUser();
        ContentValues values = new ContentValues();
        values.put(Helper.USER_PASSWORD, password);
        getDb().update(Helper.TABLE_USER, values, Helper.USER_EMAIL + " = ?", new String[]{user.getEmail()});
    }

    /**
     * Consulta o banco de dados por usuários cadastrados com ambos o E-mail e senha fornecidos e o
     * retorna caso exista como uma instância da classe {@link User} enquanto retorna
     * <code>null</code> caso contrário.
     *
     * @param email E-mail do usuário a ser consultado.
     * @param password Senha do usuário a ser consultado.
     * @return Usuário como instância da classe {@link User} ou <code>null</code> caso não existir
     * no banco de dados.
     */
    public User search(String email, String password){
        open();
        Cursor cursor = getDb().rawQuery("select * from " + Helper.TABLE_USER
                + " where " + Helper.USER_EMAIL
                + " = ? and " + Helper.USER_PASSWORD + " = ?", new String[]{email, password});
        User user = null;
        if (cursor.moveToFirst()){
            user = new User();
            user.setId(cursor.getLong(0));
            user.setEmail(cursor.getString(1));
            user.setName(cursor.getString(2));
            user.setPassword(cursor.getString(3));
        }
        close();
        return user;
    }
}
