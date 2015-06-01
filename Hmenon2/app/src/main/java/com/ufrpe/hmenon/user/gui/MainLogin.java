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
import com.ufrpe.hmenon.user.service.userService;


public class MainLogin extends ActionBarActivity {
    private Button btnCadastrar;
    private Button btnLogin;
    private EditText edtNome;
    private EditText edtSenha;
    private userService service;
    private static Context context;


    public boolean isReady(EditText editText){
        return editText.getText().toString().trim().length() > 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new userService(this);

        context = this;

        setContentView(R.layout.activity_login);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtSenha = (EditText)findViewById(R.id.edtSenha);

        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        edtSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnLogin.setEnabled(isReady(edtNome) && isReady(edtSenha));
            }
        });


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentGoSignUp = new Intent(MainLogin.this, MainSignUp.class);
                startActivity(intentGoSignUp);



            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean confirmed;


                User user = service.checkLogin(edtNome.getText().toString(), edtSenha.getText().toString());
                confirmed = user != null;

                if (confirmed){


                    Intent intentGoMain = new Intent(MainLogin.this, MainActivity.class);
                    startActivity(intentGoMain);


                    Toast.makeText(MainLogin.this, edtNome.getText().toString() +" logado com sucesso!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainLogin.this, "Usuario ou Senha invalida", Toast.LENGTH_LONG).show();
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
