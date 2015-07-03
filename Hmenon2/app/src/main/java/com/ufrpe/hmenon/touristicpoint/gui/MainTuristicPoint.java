package com.ufrpe.hmenon.touristicpoint.gui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ufrpe.hmenon.favourite.FavouritePoint;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.gui.MainInitial;
import com.ufrpe.hmenon.infrastructure.service.FavouriteBusiness;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;

/**
 * Activity responsável pela implementação da tela de informações sobre o ponto turístico.
 */
public class MainTuristicPoint extends ActionBarActivity {

    private TextView name;
    private TextView resume;
    private ImageView image;
    private TextView activity;
    private TextView address;
    private ImageView map;
    private ImageView favouriteImage;
    private static TouristicPoint pointStatic;
    private boolean isFavourite;
    private FavouriteBusiness favouriteBusiness;
    private FavouritePoint favouritePoint;

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

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(getString(R.string.history));
        tabSpec.setContent(R.id.history);
        tabSpec.setIndicator(getString(R.string.history));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getString(R.string.activity));
        tabSpec.setContent(R.id.activity);
        tabSpec.setIndicator(getString(R.string.activity));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getString(R.string.location));
        tabSpec.setContent(R.id.local);
        tabSpec.setIndicator(getString(R.string.location));
        tabHost.addTab(tabSpec);

        name = (TextView) findViewById(R.id.txtLocalName);
        resume = (TextView) findViewById(R.id.txtLocalResume);
        image = (ImageView) findViewById(R.id.image1);
        activity = (TextView) findViewById(R.id.txtActivity);
        address = (TextView) findViewById(R.id.txtAddress);
        map = (ImageView) findViewById(R.id.imgMap);
        favouriteImage = (ImageView) findViewById(R.id.imgFavorite);
        favouriteBusiness = new FavouriteBusiness(MainInitial.getContext());

        favouritePoint = new FavouritePoint();
        favouritePoint.setUser(StaticUser.getUser());
        favouritePoint.setPoint(pointStatic);

        isFavourite = favouriteBusiness.checkIfFavourite(favouritePoint);

        if (isFavourite) {
            favouriteImage.setImageResource(R.drawable.favourite_on_icon);
        }

        favouriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favouriteBusiness.checkIfFavourite(favouritePoint)) {
                    flipFavouriteBoolean();
                    favouriteBusiness.removeFavourite(favouritePoint);

                } else {
                    flipFavouriteBoolean();
                    favouriteBusiness.markPointAsFavourite(favouritePoint);
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
                Intent goMaps = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + pointStatic.getCoordinates()));
                startActivity(goMaps);
            }
        });

    }

    @Override
    protected void onStop() {
        updateFavouriteStatusOnDb(favouritePoint);
        super.onStop();
    }

    /**
     * Alterna o estatus do ponto turístico entre favorito e não-favorito e muda a imagem do ícone
     * sempre que o ícone for clicado.
     */
    private void flipFavouriteBoolean() {
        if (isFavourite) {
            isFavourite = false;
            favouriteImage.setImageResource(R.drawable.favourite_off_icon);
        } else {
            isFavourite = true;
            favouriteImage.setImageResource(R.drawable.favourite_on_icon);
        }
    }

    /**
     * Atualiza no banco de dados o estatus de favorito do ponto atualmente aberto pela activity,
     * deve ser chamado apenas uma vez, logo antes da activity ser parada.
     */
    private void updateFavouriteStatusOnDb(FavouritePoint favouritePoint) {
        if (isFavourite) {
            favouriteBusiness.markPointAsFavourite(favouritePoint);
        } else {
            favouriteBusiness.removeFavourite(favouritePoint);
        }
    }

    /**
     * Ajusta o usuário logado e o ponto turístico a ser aberto pela activity.
     * A chamada deste método ocorre antes da inicialização da instância de Intent para esta
     * activity.
     *
     * @param point Ponto turístico a ser mostrado pela activity.
     */
    public static void setUpScreen(TouristicPoint point) {
        pointStatic = point;
    }
}