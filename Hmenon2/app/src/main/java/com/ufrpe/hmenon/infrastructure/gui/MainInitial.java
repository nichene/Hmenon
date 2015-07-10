package com.ufrpe.hmenon.infrastructure.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.graph.domain.Graph;
import com.ufrpe.hmenon.graph.domain.Node;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.touristicpoint.domain.History;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.service.TouristicPointBusiness;
import com.ufrpe.hmenon.user.gui.MainLogin;

import java.util.ArrayList;

/**
 * Mostra a tela de abertura (splash screen) durante a inicialização do aplicativo enquanto cria e
 * insere os pontos turísticos no banco de dados, caso estes já não estejam incluidos.
 */
public class MainInitial extends ActionBarActivity {
    private static Context context;
    private TouristicPointBusiness touristicPointBusiness;
    private ArrayList<TouristicPoint> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        context = this;
        touristicPointBusiness = new TouristicPointBusiness(MainInitial.getContext());
        points = new ArrayList<>();
        Graph graph = new Graph();


        TouristicPoint marcoZero = createPoint("marcoZero");
        points.add(marcoZero);
        TouristicPoint brennand = createPoint("brennand");
        points.add(brennand);
        TouristicPoint museuDePernambuco = createPoint("museuDePernambuco");
        points.add(museuDePernambuco);
        TouristicPoint sinagoga = createPoint("sinagoga");
        points.add(sinagoga);
        TouristicPoint forteCincoPontas = createPoint("forteCincoPontas");
        points.add(forteCincoPontas);

        touristicPointBusiness.checkInsert(points);

        Node marcoZeroNode = new Node(marcoZero, 90);
        Node brennandNode = new Node(brennand, 120);
        Node museuNode = new Node(museuDePernambuco, 120);
        Node sinagogaNode = new Node(sinagoga, 90);
        Node forteNode = new Node(forteCincoPontas, 90);

        graph.addNode(marcoZeroNode);
        graph.addNode(brennandNode);
        graph.addNode(museuNode);
        graph.addNode(sinagogaNode);
        graph.addNode(forteNode);
        graph.addEdge(brennandNode, sinagogaNode, 30);
        graph.addEdge(brennandNode, marcoZeroNode , 30);
        graph.addEdge(brennandNode, forteNode , 30);
        graph.addEdge(brennandNode, museuNode , 30);
        graph.addEdge(marcoZeroNode, forteNode, 10);
        graph.addEdge(marcoZeroNode, museuNode, 15);
        graph.addEdge(marcoZeroNode, sinagogaNode, 5);
        graph.addEdge(sinagogaNode, museuNode, 15);
        graph.addEdge(sinagogaNode, forteNode, 5);
        graph.addEdge(forteNode, museuNode, 15);

        StaticUser.setGraph(graph);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intentGoLogin = new Intent(MainInitial.this, MainLogin.class);
                startActivity(intentGoLogin);
            }
        }, 3000);
    }

    /**
     * Instancia e retorna um ponto turístico a partir dos atributos salvos em um arquivo
     * <code>.xml</code>.
     *
     * @param pointName Nome do elemento no arquivo <code>.xml</code> que contém o ponto turístico.
     * @return Instância do ponto turístico
     */
    public TouristicPoint createPoint(String pointName){
        TouristicPoint point = new TouristicPoint();

        int idName = getResources().getIdentifier(pointName+"Name", "string", getPackageName());
        int idResume = getResources().getIdentifier(pointName+"Resume", "string", getPackageName());
        int idHistory = getResources().getIdentifier(pointName+"History", "string", getPackageName());
        int idImage = getResources().getIdentifier(pointName+"Image", "string", getPackageName());
        int idActivityHistory = getResources().getIdentifier(pointName + "ActivityText", "string", getPackageName());
        int idAddress = getResources().getIdentifier(pointName+"Address", "string", getPackageName());
        int idCoordinates = getResources().getIdentifier(pointName+"Coordinates", "string", getPackageName());
        int idMap = getResources().getIdentifier(pointName+"Map", "string", getPackageName());

        point.setMap(getResources().getString(idMap));
        point.setAddress(getResources().getString(idAddress));
        point.setCoordinates(getResources().getString(idCoordinates));
        point.setName(getResources().getString(idName));
        point.setHistory(new History());
        point.setHistoryResume(getResources().getString(idResume));
        point.setHistoryText(getResources().getString(idHistory));
        point.setImage(getResources().getString(idImage));
        point.setActivityText(getResources().getString(idActivityHistory));
        point.setChecked(0);

        return point;
    }

    public static Context getContext() {
        return context;
    }
}
