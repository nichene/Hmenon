package com.ufrpe.hmenon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.gui.MainSettings;
import com.ufrpe.hmenon.user.gui.MainLogin;


public class MainActivity extends ActionBarActivity {
    SearchView search;
    private Button btnSettings;



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
        search = (SearchView) findViewById(R.id.search);
        setContentView(R.layout.activity_main);

        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intentGoEdit = new Intent(MainActivity.this, MainSettings.class);
                startActivity(intentGoEdit);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
