package com.ufrpe.hmenon.turisticpoint.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.R;

public class MainTuristicPoint extends ActionBarActivity{

    private TextView name;
    private TextView resume;
    private ImageView image;
    private static String nameStatic;
    private static String resumeStatic;
    private static String imageStatic;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoMain = new Intent(MainTuristicPoint.this, MainActivity.class);
        startActivity(intentGoMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turistic_point);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("História");
        tabSpec.setContent(R.id.history);
        tabSpec.setIndicator("História");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Atividade");
        tabSpec.setContent(R.id.activity);
        tabSpec.setIndicator("Atividade");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Local");
        tabSpec.setContent(R.id.local);
        tabSpec.setIndicator("Local");
        tabHost.addTab(tabSpec);

        name = (TextView) findViewById(R.id.textLocalName);
        resume = (TextView) findViewById(R.id.textLocalResume);
        image = (ImageView) findViewById(R.id.image1);
        name.setText(nameStatic);
        resume.setText(resumeStatic);
        int resId = getResources().getIdentifier(imageStatic, "drawable", getPackageName());
        image.setImageResource(resId);

    }
    public static void setUpScreen(String name, String resume, String image){
        nameStatic = name;
        resumeStatic = resume;
        imageStatic = image;
    }
}
