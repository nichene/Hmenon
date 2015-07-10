package com.ufrpe.hmenon.user.service;

import android.content.Context;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.dao.UserDAO;
import com.ufrpe.hmenon.user.domain.User;

/**
 * Realiza as operações de consulta de usuários ao banco de dados para a activity.
 */
public class UserBusiness {
    private UserDAO dao = UserDAO.getInstance();
    /**
     * Ajustando o contexto da classe DAO.
     *
     * @param context Contexto da activity.
     */
    public UserBusiness(Context context) {
        dao.setUpAttributes(context);
    }


    /**
     * Verifica se os campos de criação de conta foram preenchidos corretamente e insere o usuário
     * no banco de dados caso positivo enquanto levanta uma exceção genérica com mensagem de erro
     * caso existam campos inválidos.
     *
     * @param user Instância do usuário cujos atributos devem ser validados.
     * @param confirmed String contendo a entrada do campo "confirmação de senha".
     * @throws Exception Exceção genérica contendo a mensagem de erro.
     */
    public void checkSignUp(User user, String confirmed) throws Exception {
        StringBuilder exception = new StringBuilder();
        if (!user.getPassword().equals(confirmed)) {
            exception.append("As senhas não estão equivalentes!");
        } else if (dao.search(user.getEmail()) != null) {
            exception.append("Esse Usuário ja está cadastrado");
        } else if (!EmailValidator.validar(user.getEmail())){
            exception.append("Email Inválido");
        } else {
            dao.insert(user);
        }

        if (exception.length() > 0) {
            throw new Exception(exception.toString());
        }
    }

    /**
     * Remove do banco de dados o usuário verificando se o mesmo se encontra no banco e se o string
     * senha é igual ao seu atributo password.
     *
     * @param user Usuário a ser removido do banco de dados.
     * @param password String contendo a senha a ser comparada com a senha do usuário.
     * @throws Exception Exceção genérica contendo uma mensagem informando os erros ocorridos
     * durante a remoção.
     */
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

    /**
     * Altera o nome do usuário logado e salva a alteração no banco de dados.
     *
     * @param name String contendo o novo nome do usuário.
     * @param confirmedName String do campo "confirmação de novo nome".
     * @param password String contendo a senha do usuário, utilizado para verificação de segurança.
     * @throws Exception Exceção genérica contendo uma mensagem informando os erros ocorridos
     * durante a atualização.
     */
    public void checkNameUpdate(String name, String confirmedName, String password)
            throws Exception{

        StringBuilder exception = new StringBuilder();
        User user = StaticUser.getUser();

        if (!name.equals(confirmedName)){
            exception.append("Os nomes não estão equivalentes!");
//        } else if (dao.search(name) != null){
//            exception.append("O nome já está sendo usado");
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

    /**
     * Altera a senha do usuário logado e salva a alteração no banco de dados.
     *
     * @param password String contendo a nova senha.
     * @param confirmedPassword String do campo "confirmação de senha".
     * @param oldPassword String contendo a antiga senha do usuário, utilizado apenas para
     *                    verificação de segurança.
     * @throws Exception Exceção genérica contendo uma mensagem informando os erros ocorridos
     * durante a atualização.
     */
    public void checkPasswordUpdate(String password, String confirmedPassword, String oldPassword)
            throws Exception{

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

    /**
     * Verifica se o usuário fornecido se encontra no banco de dados.
     *
     * @param user Usuário a ser consultado no banco de dados.
     * @throws Exception Exceção genérica contendo uma mensagem informando o erro ao procurar o
     * usuário fornecido no banco de dados.
     */
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
