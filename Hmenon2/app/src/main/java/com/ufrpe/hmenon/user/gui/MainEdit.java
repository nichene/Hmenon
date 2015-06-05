package com.ufrpe.hmenon.user.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ufrpe.hmenon.R;

public class MainEdit extends ActionBarActivity {
    private Button btnEditPass;
    private Button btnEditName;

    public boolean isReady(EditText editText, int i){
        return editText.getText().toString().trim().length() > i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);

        btnEditName = (Button) findViewById(R.id.btnEditName);
        btnEditPass = (Button) findViewById(R.id.btnEditPass);

        btnEditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoEditPass = new Intent(MainEdit.this, MainEditPass.class);
                startActivity(intentGoEditPass);
            }
        });

        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoEditName = new Intent(MainEdit.this, MainEditName.class);
                startActivity(intentGoEditName);
            }
        });


    }
}



