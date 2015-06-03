package com.ufrpe.hmenon.user.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ufrpe.hmenon.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.user.domain.User;
import com.ufrpe.hmenon.user.service.UserBusiness;


public class MainLogin extends ActionBarActivity {
    private Button btnCadastreSe;
    private Button btnLogin;
    private EditText edtName;
    private EditText edtPassword;
    private UserBusiness service;
    private static Context context;

    public boolean isReady(EditText editText, int i){
        return editText.getText().toString().trim().length() > i;
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        service = new UserBusiness(context);


        setContentView(R.layout.activity_login);

        edtName = (EditText)findViewById(R.id.edtNome);
        edtPassword = (EditText)findViewById(R.id.edtSenha);
        btnCadastreSe = (Button)findViewById(R.id.btnCadastreSe);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                btnLogin.setEnabled(isReady(edtName, 3) && isReady(edtPassword, 3));
            }
        });


        btnCadastreSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intentGoSignUp = new Intent(MainLogin.this, MainSignUp.class);
                startActivity(intentGoSignUp);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                User user = new User();
                user.setName(name);
                user.setPassword(password);
                try {
                    service.checkLogin(user);
                    Toast.makeText(MainLogin.this, edtName.getText().toString() +" logado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intentGoMain = new Intent(MainLogin.this, MainActivity.class);
                    startActivity(intentGoMain);
                } catch (Exception e){
                    Toast.makeText(MainLogin.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
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
    public static Context getContext(){
        return MainLogin.context;
    }
}
