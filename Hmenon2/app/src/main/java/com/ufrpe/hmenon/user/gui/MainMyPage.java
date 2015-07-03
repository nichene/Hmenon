package com.ufrpe.hmenon.user.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.favourite.gui.MainFavourite;
import com.ufrpe.hmenon.graph.gui.MainRouteSugestion;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.domain.User;

/**
 * Created by Ricardo on 02/07/2015.
 */
public class MainMyPage extends ActionBarActivity {
    private TextView txtNome;
    private Button btnFavourite;
    private Button btnScript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        txtNome = (TextView) findViewById(R.id.txtUserName);
        btnFavourite = (Button) findViewById(R.id.btnFavourite);
        btnScript = (Button) findViewById(R.id.btnRoute);
        User user = StaticUser.getUser();
        String name = user.getName();
        txtNome.setText(name);
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intentGoFavourites = new Intent(MainMyPage.this, MainFavourite.class);
                startActivity(intentGoFavourites);
            }
        });
        btnScript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(MainMyPage.this);
            }
        });
    }
    public void showDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Ainda não há roteiro definido");
        builder.setMessage("Quantas horas você tem disponível?");
        final EditText prompt = new EditText(this);
        prompt.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(prompt);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int time = Integer.parseInt(prompt.getText().toString());
                    MainRouteSugestion.setTimeLimit(time*60);
                    finish();
                    Intent intentGoRouteSuggestion = new Intent(MainMyPage.this, MainRouteSugestion.class);
                    startActivity(intentGoRouteSuggestion);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.show();
    }



    }
