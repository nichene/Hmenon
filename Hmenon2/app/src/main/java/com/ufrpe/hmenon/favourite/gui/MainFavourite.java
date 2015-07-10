package com.ufrpe.hmenon.favourite.gui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.domain.GPSTracker;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.infrastructure.gui.MainInitial;
import com.ufrpe.hmenon.favourite.service.FavouriteBusiness;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.gui.MainTuristicPoint;
import com.ufrpe.hmenon.touristicpoint.service.TouristicPointBusiness;
import com.ufrpe.hmenon.user.gui.MainMyPage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity responsável pela listagem de pontos turísticos favoritos.
 */
public class MainFavourite extends ActionBarActivity{

    private ListView favouritesList;
    private List<TouristicPoint> points = new ArrayList<>();
    private GPSTracker gps;
    private TouristicPointBusiness touristicPointBusiness;
    private FavouriteBusiness favouriteBusiness;
    private Context currentContext = MainFavourite.this;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentBackToMyPage = new Intent(MainFavourite.this, MainMyPage.class);
        startActivity(intentBackToMyPage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);

        gps = new GPSTracker(MainInitial.getContext());
        favouriteBusiness = new FavouriteBusiness(MainInitial.getContext());
        touristicPointBusiness = new TouristicPointBusiness(MainInitial.getContext());
        favouritesList = (ListView) findViewById(R.id.listFavourites);

        ArrayList<String> pointsIds = favouriteBusiness.getFavouritesPointsIds(
                StaticUser.getUser().getId());

        for (String id : pointsIds){
            TouristicPoint point = touristicPointBusiness.getTouristicPointById(id);
            points.add(point);
        }

        populate();
        favouritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TouristicPoint touristicPoint = points.get(position);
                MainTuristicPoint.setUpScreen(touristicPoint);
                Intent intentGoPointScreen = new Intent(MainFavourite.this, MainTuristicPoint.class);
                finish();
                startActivity(intentGoPointScreen);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intentGoHome = new Intent(currentContext, MainActivity.class);
                finish();
                startActivity(intentGoHome);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populate(){
        ArrayAdapter<TouristicPoint> adapter = new ContactListAdapter();
        favouritesList.setAdapter(adapter);
    }
    private class ContactListAdapter extends ArrayAdapter<TouristicPoint> {
        public ContactListAdapter(){
            super(MainFavourite.this, R.layout.list_item, points);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            }

            DecimalFormat format = new DecimalFormat("#.##");
            TouristicPoint currentPoint = points.get(position);
            TextView name = (TextView) view.findViewById(R.id.txtNome);
            name.setText(currentPoint.getName());
            TextView distance = (TextView) view.findViewById(R.id.txtDistance);
            Location destLocation = new Location("point");
            String[] parts = currentPoint.getCoordinates().split(",");
            double latitude = Double.parseDouble(parts[0]);
            destLocation.setLatitude(latitude);
            double longitude = Double.parseDouble(parts[1]);
            destLocation.setLongitude(longitude);

            try {
                Location currentLocation = gps.getLocation();
                double distanceTo = destLocation.distanceTo(currentLocation);
                String metric = "m";

                if (distanceTo > 1000) {
                    distanceTo = distanceTo / 1000;
                    metric = "km";
                }

                String dist = String.valueOf(format.format(distanceTo * 1.3));
                distance.setText("~" + dist + " " + metric);
            } catch (NullPointerException e){
                Toast.makeText(MainFavourite.this, getString(R.string.gps_offline),
                        Toast.LENGTH_LONG).show();
            }

            ImageView image = (ImageView) view.findViewById(R.id.imgPointIcon);

            int idIcon = getResources().getIdentifier("icon_"+currentPoint.getImage(), "drawable",
                    getPackageName());

            image.setImageResource(idIcon);

            return view;
        }
    }
}
