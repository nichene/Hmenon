package com.ufrpe.hmenon.user.domain;

/**
 * User é uma classe simples para representação do usuário do aplicativo.
 * <p>
 * Apenas implementa getters e setters.
 */
public class User {
    /**
     * Identificador único para registro no banco de dados.
     */
    private long id;
    private String email;
    private String name;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }
}
