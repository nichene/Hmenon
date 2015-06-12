package com.ufrpe.hmenon.infrastructure.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.touristicpoint.domain.History;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.service.TouristicPointBusiness;
import com.ufrpe.hmenon.user.gui.MainLogin;
import java.util.ArrayList;

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

        points.add(createPoint("marcoZero"));
        points.add(createPoint("brennand"));
        points.add(createPoint("museuDePernambuco"));
        points.add(createPoint("sinagoga"));
        points.add(createPoint("forteCincoPontas"));
        touristicPointBusiness.checkInsert(points);


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
    public TouristicPoint createPoint(String pointName){
        TouristicPoint point = new TouristicPoint();
        int idName = getResources().getIdentifier(pointName+"Name", "string", getPackageName());
        int idResume = getResources().getIdentifier(pointName+"Resume", "string", getPackageName());
        int idHistory = getResources().getIdentifier(pointName+"History", "string", getPackageName());
        int idImage = getResources().getIdentifier(pointName+"Image", "string", getPackageName());
        int idActivityHistory = getResources().getIdentifier(pointName + "ActivityText", "string", getPackageName());
        point.setName(getResources().getString(idName));
        point.setHistory(new History());
        point.setHistoryResume(getResources().getString(idResume));
        point.setHistoryText(getResources().getString(idHistory));
        point.setImage(getResources().getString(idImage));
        point.setActivityText(getResources().getString(idActivityHistory));
        return point;
    }

    public static Context getContext() {
        return context;
    }
}
