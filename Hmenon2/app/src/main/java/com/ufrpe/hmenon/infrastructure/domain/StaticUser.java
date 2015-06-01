package com.ufrpe.hmenon.infrastructure.domain;

import com.ufrpe.hmenon.user.domain.User;


public class StaticUser {

    private static User user;



    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        StaticUser.user = user;
    }



}
