package com.ufrpe.hmenon.user.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ufrpe.hmenon.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.service.UserBusiness;

public class MainDelete extends ActionBarActivity{
    private Button btnConfirmDelete;
    private Button btnCancelDelete;
    private UserBusiness service;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoMain = new Intent(MainDelete.this, MainActivity.class);
        startActivity(intentGoMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteuser);

        service = new UserBusiness(MainLogin.getContext());
        btnConfirmDelete = (Button) findViewById(R.id.btnConfirmDelete);
        btnCancelDelete = (Button) findViewById(R.id.btnCancelDelete);

        btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    service.checkDelete(StaticUser.getUser());
                    StaticUser.setUser(null);
                    finish();
                    Intent intentGoLogin = new Intent(MainDelete.this, MainLogin.class);
                    startActivity(intentGoLogin);

                } catch (Exception e){
                    Toast.makeText(MainDelete.this, e.getMessage().toString(), Toast.LENGTH_LONG);
                }
            }
        });
        btnCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
