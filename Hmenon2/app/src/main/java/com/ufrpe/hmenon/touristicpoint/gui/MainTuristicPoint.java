package com.ufrpe.hmenon.touristicpoint.gui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.gui.MainInitial;
import com.ufrpe.hmenon.infrastructure.service.FavouriteBusiness;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;

public class MainTuristicPoint extends ActionBarActivity{

    private TextView name;
    private TextView resume;
    private ImageView image;
    private TextView activity;
    private TextView address;
    private ImageView map;
    private ImageView favouriteImage;
    private static TouristicPoint pointStatic;
    private static boolean isFavourite;
    private FavouriteBusiness favouriteBusiness;

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
        favouriteImage = (ImageView) findViewById(R.id.imgFavorite);
        favouriteBusiness = new FavouriteBusiness(MainInitial.getContext());

        if (isFavourite){
            favouriteImage.setImageResource(R.drawable.favourite_on_icon);
        }
        favouriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticUser.getUser().isFavourite(pointStatic.getName())){
                    StaticUser.getUser().removeFavourite(pointStatic);
                    favouriteImage.setImageResource(R.drawable.favourite_off_icon);
                    favouriteBusiness.removeFavourite(StaticUser.getUser(), pointStatic);

                }
                else {
                    StaticUser.getUser().addFavourite(pointStatic);
                    favouriteImage.setImageResource(R.drawable.favourite_on_icon);
                    favouriteBusiness.markPointAsFavourite(StaticUser.getUser(), pointStatic);
                }
            }
        });

        name.setText(pointStatic.getName());
        resume.setText(pointStatic.getHistory().getResume());
        activity.setText(pointStatic.getActivityText());
        address.setText(pointStatic.getAddress());
        int idMap = getResources().getIdentifier(pointStatic.getMap(), "drawable", getPackageName());
        int idImage = getResources().getIdentifier(pointStatic.getImage(), "drawable", getPackageName());
        image.setImageResource(idImage);
        map.setImageResource(idMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMaps = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+pointStatic.getCoordinates()));
                startActivity(goMaps);
            }
        });

    }
    public static void setUpScreen(TouristicPoint point, boolean favourite){
        pointStatic = point;
        isFavourite = favourite;
    }
}
