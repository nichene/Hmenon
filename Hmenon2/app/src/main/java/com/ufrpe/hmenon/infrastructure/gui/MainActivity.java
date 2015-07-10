package com.ufrpe.hmenon.infrastructure.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.favourite.domain.FavouritePoint;
import com.ufrpe.hmenon.infrastructure.domain.GPSTracker;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.favourite.service.FavouriteBusiness;
import com.ufrpe.hmenon.touristicpoint.gui.MainTuristicPoint;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.service.TouristicPointBusiness;
import com.ufrpe.hmenon.user.gui.MainEditUserName;
import com.ufrpe.hmenon.user.gui.MainEditUserPassword;
import com.ufrpe.hmenon.user.gui.MainLogin;
import com.ufrpe.hmenon.user.gui.MainMyPage;
import com.ufrpe.hmenon.user.service.UserBusiness;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * Activity responsável por implementar as funcionalidades da tela principal do aplicativo.
 */
public class MainActivity extends ActionBarActivity {
    private SearchView search;
    private UserBusiness userService;
    private TouristicPointBusiness touristicPointBusiness;
    private List<TouristicPoint> touristicPoints;
    private List<TouristicPoint> allPoints;
    private ListView list;
    private GPSTracker gps;
    private Context currentContext = MainActivity.this;
    private FavouriteBusiness favouriteBusiness;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        StaticUser.setUser(null);
        Intent intentGoLogin = new Intent(MainActivity.this, MainLogin.class);
        startActivity(intentGoLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StaticUser.setContext(this);
        gps = new GPSTracker(StaticUser.getContext());
        setContentView(R.layout.activity_main);

        gps = new GPSTracker(MainInitial.getContext());
        ArrayList<FavouritePoint> favouritePoints = new ArrayList<>();
        favouriteBusiness = new FavouriteBusiness(StaticUser.getContext());
        touristicPointBusiness = new TouristicPointBusiness(StaticUser.getContext());
        ArrayList<String> pointsIds = favouriteBusiness.getFavouritesPointsIds(
                StaticUser.getUser().getId());

        for (String id : pointsIds){
            FavouritePoint favouritePoint = new FavouritePoint();
            TouristicPoint point = touristicPointBusiness.getTouristicPointById(id);
            favouritePoint.setPoint(point);
            favouritePoints.add(favouritePoint);
        }

        list = (ListView) findViewById(R.id.listPoints);
        touristicPointBusiness = new TouristicPointBusiness(StaticUser.getContext());
        touristicPoints = touristicPointBusiness.checkGetAll();
        allPoints = touristicPoints;
        populate();
        getOverflowMenu();
        userService = new UserBusiness(StaticUser.getContext());

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TouristicPoint touristicPoint = touristicPoints.get(position);
                MainTuristicPoint.setUpScreen(touristicPoint);
                Intent intentGoPointScreen = new Intent(MainActivity.this, MainTuristicPoint.class);
                finish();
                startActivity(intentGoPointScreen);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        search = (SearchView) MenuItemCompat.getActionView(searchItem);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    ArrayList<TouristicPoint> listAux = new ArrayList<>();
                    for (TouristicPoint point : touristicPoints) {
                        if (point.getName().toLowerCase().contains(newText.toLowerCase())) {
                            listAux.add(point);
                        }
                    }
                    touristicPoints = listAux;
                } else {
                    touristicPoints = allPoints;
                }
                populate();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.editName:
                finish();
                Intent intentGoEditUserName = new Intent(this, MainEditUserName.class);
                startActivity(intentGoEditUserName);
                break;
            case R.id.editPassword:
                finish();
                Intent intentGoEditUserPassword = new Intent(this, MainEditUserPassword.class);
                startActivity(intentGoEditUserPassword);
                break;
            case R.id.deleteUser:
                showDeleteDialog(MainActivity.this);
                break;
            case R.id.scanQR:
                IntentIntegrator intentToScan = new IntentIntegrator(MainActivity.this);
                intentToScan.initiateScan();
                break;
            case R.id.mainToMyPage:
                finish();
                Intent intentGoMyPage = new Intent(MainActivity.this, MainMyPage.class);
                startActivity(intentGoMyPage);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void populate(){
        ArrayAdapter<TouristicPoint> adapter = new ContactListAdapter();
        list.setAdapter(adapter);
    }
    private class ContactListAdapter extends ArrayAdapter<TouristicPoint> {
        private double min = Double.MAX_VALUE;
        public ContactListAdapter(){
            super(MainActivity.this, R.layout.list_item, touristicPoints);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            }

            DecimalFormat format = new DecimalFormat("#.##");
            TouristicPoint currentPoint = touristicPoints.get(position);
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
                if (distanceTo < min){
                    min = distanceTo;
                    StaticUser.setCloserTime((long) ((distanceTo * 1.3) / 5.5) / 60);
                    StaticUser.setCloserPoint(currentPoint);
                }
                String metric = "m";
                if (distanceTo > 1000) {
                    distanceTo = distanceTo / 1000;
                    metric = "km";
                }
                String dist = String.valueOf(format.format(distanceTo * 1.3));
                distance.setText("~" + dist + " " + metric);
            } catch (NullPointerException e){
                Toast.makeText(MainActivity.this, getString(R.string.gps_offline), Toast.LENGTH_LONG).show();
            }
            ImageView image = (ImageView) view.findViewById(R.id.imgPointIcon);

            int idIcon = getResources().getIdentifier("icon_"+currentPoint.getImage(), "drawable",
                    getPackageName());

            image.setImageResource(idIcon);

            return view;
        }
    }
    public void showDeleteDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getString(R.string.delete_account));
        builder.setMessage(getString(R.string.confirm_password_to_delete_account));
        final EditText prompt = new EditText(this);
        prompt.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(prompt);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userService.checkDelete(StaticUser.getUser(), prompt.getText().toString());
                    StaticUser.setUser(null);
                    finish();
                    Intent intentGoLogin = new Intent(MainActivity.this, MainLogin.class);
                    startActivity(intentGoLogin);

                } catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.show();
    }

    private void getOverflowMenu(){
        try {
            ViewConfiguration configuration = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null){
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(configuration, false);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Lidando com o resultado do scan.
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
                intent);

        if (scanResult != null) {

            if (scanResult.getContents() == null) {
                Toast.makeText(currentContext, getString(R.string.canceled_scan),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                if (!scanResult.getFormatName().equals("QR_CODE")) {
                    Toast.makeText(currentContext, getString(R.string.not_a_qr_code),
                            Toast.LENGTH_SHORT).show();
                }
                try {
                    TouristicPoint point = touristicPointBusiness.getTouristicPointByIdEncrypted(
                            scanResult.getContents());

                    if (point == null) {
                        throw new NullPointerException(getString(R.string.invalid_qr_code));
                    }

                    MainTuristicPoint.setUpScreen(point);
                    Intent intentToPoint = new Intent(currentContext, MainTuristicPoint.class);
                    finish();
                    startActivity(intentToPoint);
                }
                catch (NullPointerException | NumberFormatException | InvalidKeyException |
                        IllegalBlockSizeException | BadPaddingException |
                        UnsupportedEncodingException except) {

                    Toast.makeText(currentContext, getString(R.string.invalid_qr_code),
                            Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(currentContext, getString(R.string.invalid_qr_code),
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }

}
