package com.ufrpe.hmenon.infrastructure.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.touristicpoint.gui.MainTuristicPoint;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.service.TouristicPointBusiness;
import com.ufrpe.hmenon.user.gui.MainEditUserName;
import com.ufrpe.hmenon.user.gui.MainEditUserPassword;
import com.ufrpe.hmenon.user.gui.MainLogin;
import com.ufrpe.hmenon.user.service.UserBusiness;
import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private SearchView search;
    private UserBusiness userService;
    private TouristicPointBusiness touristicPointBusiness;
    private List<TouristicPoint> touristicPoints;
    private ListView lista;
    private Context currentContext = MainActivity.this;


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
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.listPoints);
        touristicPointBusiness = new TouristicPointBusiness(MainInitial.getContext());
        touristicPoints = touristicPointBusiness.checkGetAll();
        populate();
        setListHeight(lista);
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
            case R.id.scanQR:
                IntentIntegrator intentToScan = new IntentIntegrator(MainActivity.this);
                intentToScan.initiateScan();
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

            TouristicPoint currentPoint = touristicPoints.get(position);
            TextView name = (TextView) view.findViewById(R.id.textNome);
            name.setText(String.valueOf(position + 1) + ": " + currentPoint.getName());

            return view;
        }
    }
    public void setListHeight(ListView view){
        ListAdapter listAdapter = view.getAdapter();
        if (listAdapter != null){
            int number = listAdapter.getCount();
            int totalHeight = 0;
            for (int i = 0; i < number; i++){
                View item = listAdapter.getView(i,null,view);
                item.measure(0,0);
                totalHeight += item.getMeasuredHeight();
            }
            int totalDividersHeight = view.getDividerHeight()*(number-1);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = totalHeight+totalDividersHeight;
            view.setLayoutParams(layoutParams);
            view.requestLayout();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
                intent);

        if (scanResult != null) {
            Log.d("scanRes", "!null");
            if (scanResult.getContents() == null) {
                Log.d("scanRes.Cont", "null");
                Toast.makeText(currentContext, getString(R.string.canceled_scan),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d("scanRes.Contents", "!null");
                if (!scanResult.getFormatName().equals("QR_CODE")) {
                    Toast.makeText(currentContext, getString(R.string.not_a_qr_code),
                            Toast.LENGTH_SHORT).show();
                }

                try {
                    TouristicPoint point = touristicPointBusiness.getTouristicPointById(
                            Integer.parseInt(scanResult.getContents()));

                    if (point == null) {
                        throw new Exception(getString(R.string.invalid_qr_code));
                    }

                    MainTuristicPoint.setUpScreen(point);
                    Intent intentToPoint = new Intent(currentContext, MainTuristicPoint.class);
                    finish();
                    startActivity(intentToPoint);
                }
                catch (NumberFormatException numFormExcept) {
                    Toast.makeText(currentContext, getString(R.string.invalid_qr_code),
                            Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(currentContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
