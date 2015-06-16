package com.ufrpe.hmenon.touristicpoint.gui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;

public class MainTuristicPoint extends ActionBarActivity{

    private TextView name;
    private TextView resume;
    private ImageView image;
    private TextView activity;
    private TextView address;
    private ImageView map;
    private Button googleMaps;
    private static String nameStatic;
    private static String resumeStatic;
    private static String imageStatic;
    private static String activityStatic;
    private static String addressStatic;
    private static String mapStatic;
    private static String coordinatesStatic;

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

        name = (TextView) findViewById(R.id.txtLocalName);
        resume = (TextView) findViewById(R.id.txtLocalResume);
        image = (ImageView) findViewById(R.id.image1);
        activity = (TextView) findViewById(R.id.txtActivity);
        address = (TextView) findViewById(R.id.txtAddress);
        map = (ImageView) findViewById(R.id.imgMap);

        name.setText(nameStatic);
        resume.setText(resumeStatic);
        activity.setText(activityStatic);
        address.setText(addressStatic);
        int idMap = getResources().getIdentifier(mapStatic, "drawable", getPackageName());
        int idImage = getResources().getIdentifier(imageStatic, "drawable", getPackageName());
        image.setImageResource(idImage);
        map.setImageResource(idMap);
        googleMaps = (Button) findViewById(R.id.btnGoMaps);
        googleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMaps = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+coordinatesStatic));
                startActivity(goMaps);
            }
        });

    }
    public static void setUpScreen(TouristicPoint point){
        nameStatic = point.getName();
        resumeStatic = point.getHistory().getResume();
        imageStatic = point.getImage();
        activityStatic = point.getActivityText();
        mapStatic = point.getMap();
        coordinatesStatic = point.getCoordinates();
        addressStatic = point.getAddress();
    }
}
