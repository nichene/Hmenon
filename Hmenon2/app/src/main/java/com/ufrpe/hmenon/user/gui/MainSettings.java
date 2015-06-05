package com.ufrpe.hmenon.user.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


import com.ufrpe.hmenon.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;


public class MainSettings extends ActionBarActivity {

    private Button btnEditName;
    private Button btnEditPassword;
    private Button btnDelete;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StaticUser.setUser(null);
        Intent intentGoMain = new Intent(MainSettings.this, MainActivity.class);
        startActivity(intentGoMain);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnEditName = (Button) findViewById(R.id.btnEditName);
        btnEditPassword = (Button) findViewById(R.id.btnEditPassword);
        btnDelete = (Button) findViewById(R.id.btnDeleteUser);

        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intentGoEditUserName = new Intent(MainSettings.this, MainEditUserName.class);
                startActivity(intentGoEditUserName);
            }

        });
        btnEditPassword.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
                Intent intentGoEditUserPassword = new Intent(MainSettings.this, MainEditUserPassword.class);
                startActivity(intentGoEditUserPassword);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
                Intent intentGoDeleteUser = new Intent(MainSettings.this, MainDelete.class);
                startActivity(intentGoDeleteUser);
            }
        });
}
}

