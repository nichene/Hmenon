package com.ufrpe.hmenon.user.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.favourite.gui.MainFavourite;
import com.ufrpe.hmenon.graph.gui.MainChoosePoints;
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
                finish();
                Intent intentGoChoosePoints = new Intent(MainMyPage.this, MainChoosePoints.class);
                startActivity(intentGoChoosePoints);
            }
        });
    }
}
