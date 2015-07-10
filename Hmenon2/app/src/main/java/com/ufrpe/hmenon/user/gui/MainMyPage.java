package com.ufrpe.hmenon.user.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.favourite.gui.MainFavourite;
import com.ufrpe.hmenon.graph.gui.MainRouteSugestion;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.user.domain.User;

/**
 * Activity responsável por mostrar a página do usuário e oferecer as opções de visualizar favoritos
 * ou criar rotas de visitação.
 */
public class MainMyPage extends ActionBarActivity {
    private TextView txtNome;
    private Button btnFavourite;
    private Button btnScript;
    private Context currentContext = MainMyPage.this;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoMain = new Intent(MainMyPage.this, MainActivity.class);
        startActivity(intentGoMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);

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
        builder.setTitle(getString(R.string.define_new_script));
        builder.setMessage(getString(R.string.how_many_avaliable_hours));
        final EditText prompt = new EditText(this);
        prompt.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(prompt);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int time = Integer.parseInt(prompt.getText().toString());

                    if (time <= 24 && time > 0) {
                        MainRouteSugestion.setTimeLimit(time * 60);
                        finish();

                        Intent intentGoRouteSuggestion = new Intent(MainMyPage.this,
                                MainRouteSugestion.class);

                        startActivity(intentGoRouteSuggestion);
                    }
                    else {
                        Toast.makeText(currentContext, getString(R.string.invalid_number_of_hours),
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.show();
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
}
