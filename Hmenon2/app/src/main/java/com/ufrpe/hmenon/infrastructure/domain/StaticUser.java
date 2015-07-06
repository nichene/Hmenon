package com.ufrpe.hmenon.infrastructure.domain;

import android.content.Context;

import com.ufrpe.hmenon.graph.domain.Graph;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.domain.User;

/**
 * Classe para acesso ao usuário logado.
 *
 * Implementa getters, setters além do contexto e ponto turístico mais próximo da localização atual
 * do usuário.
 */
public class StaticUser {

    private static User user;
    private static TouristicPoint closerPoint;
    private static Context context;
    private static Graph graph;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        StaticUser.user = user;
    }

    public static TouristicPoint getCloserPoint() {
        return closerPoint;
    }

    public static void setCloserPoint(TouristicPoint closerPoint) {
        StaticUser.closerPoint = closerPoint;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        StaticUser.context = context;
    }

    public static Graph getGraph() {
        return graph;
    }

    public static void setGraph(Graph graph) {
        StaticUser.graph = graph;
    }
}
