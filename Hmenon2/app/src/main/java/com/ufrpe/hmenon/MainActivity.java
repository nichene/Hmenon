package com.ufrpe.hmenon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.gui.MainEditUserName;
import com.ufrpe.hmenon.user.gui.MainEditUserPassword;
import com.ufrpe.hmenon.user.gui.MainLogin;
import com.ufrpe.hmenon.user.service.UserBusiness;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private SearchView search;
    private UserBusiness service;
    private List<String> nomes;
    private ListView lista;


    public void showDeleteDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Apagar Conta");
        builder.setMessage("Confirme sua senha para apagar conta");
        final EditText prompt = new EditText(this);
        prompt.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(prompt);
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    service.checkDelete(StaticUser.getUser(), prompt.getText().toString());
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
        nomes = new ArrayList<>();
        nomes.add("Lalala");
        nomes.add("Lelele");
        nomes.add("Lilili");
        nomes.add("Lololo");
        nomes.add("Lululu");
        nomes.add("Lalal");
        nomes.add("Lelel");
        nomes.add("Lilil");
        nomes.add("Lolol");
        nomes.add("Lulul");
        populate();
        setListHeight(lista);
        getOverflowMenu();
        service = new UserBusiness(MainLogin.getContext());
        search = (SearchView) findViewById(R.id.search);

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
        ArrayAdapter<String> adapter = new ContactListAdapter();
        lista.setAdapter(adapter);

    }
    private class ContactListAdapter extends ArrayAdapter<String> {
        public ContactListAdapter(){
            super(MainActivity.this, R.layout.list_item, nomes);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_item, parent, false);

            String currentContact = nomes.get(position);

            TextView name = (TextView) view.findViewById(R.id.textNome);
            name.setText(currentContact);

            return view;
        }
    }

}
