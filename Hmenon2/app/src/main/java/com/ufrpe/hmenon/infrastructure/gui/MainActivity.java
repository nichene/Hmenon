package com.ufrpe.hmenon.infrastructure.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.domain.GPSTracker;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.touristicpoint.gui.MainTuristicPoint;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.service.TouristicPointBusiness;
import com.ufrpe.hmenon.user.gui.MainEditUserName;
import com.ufrpe.hmenon.user.gui.MainEditUserPassword;
import com.ufrpe.hmenon.user.gui.MainLogin;
import com.ufrpe.hmenon.user.service.UserBusiness;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private SearchView search;
    private UserBusiness userService;
    private TouristicPointBusiness touristicPointBusiness;
    private List<TouristicPoint> touristicPoints;
    private ListView lista;
    private GPSTracker gps;


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
        gps = new GPSTracker(MainInitial.getContext());
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.listPoints);
        touristicPointBusiness = new TouristicPointBusiness(MainInitial.getContext());
        touristicPoints = touristicPointBusiness.checkGetAll();
        populate();
        getOverflowMenu();
        userService = new UserBusiness(MainInitial.getContext());
        search = (SearchView) findViewById(R.id.search);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        }

        return super.onOptionsItemSelected(item);
    }
    private void populate(){
        ArrayAdapter<TouristicPoint> adapter = new ContactListAdapter();
        lista.setAdapter(adapter);

    }
    private class ContactListAdapter extends ArrayAdapter<TouristicPoint> {
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
                String metric = "m";
                if (distanceTo > 1000) {
                    distanceTo = distanceTo / 1000;
                    metric = "km";
                }
                String dist = String.valueOf(format.format(distanceTo * 1.3));
                distance.setText("~" + dist + " " + metric);
            } catch (NullPointerException e){
                Toast.makeText(MainActivity.this, "GPS ou Rede desligados", Toast.LENGTH_LONG).show();
            }
            ImageView image = (ImageView) view.findViewById(R.id.imgPointIcon);
            int idIcon = getResources().getIdentifier("icon_"+currentPoint.getImage(), "drawable", getPackageName());
            image.setImageResource(idIcon);

            return view;
        }
    }
    public void showDeleteDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Apagar Conta");
        builder.setMessage("Confirme sua senha para apagar conta");
        final EditText prompt = new EditText(this);
        prompt.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(prompt);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
        builder.setNegativeButton("CANCELAR", null);
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

}
