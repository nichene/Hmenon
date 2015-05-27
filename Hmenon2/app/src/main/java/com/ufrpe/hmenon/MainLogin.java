package com.ufrpe.hmenon;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainLogin extends ActionBarActivity {
    private Button btnCadastrar;
    private Button btnLogin;
    private EditText edtNome;
    private EditText edtSenha;
    private UsuarioService service;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new UsuarioService(this);

        context = this;

        setContentView(R.layout.activity_login);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtSenha = (EditText)findViewById(R.id.edtSenha);

        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        btnLogin = (Button)findViewById(R.id.btnLogin);



        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainLogin.this, edtNome.getText().toString() +" você foi cadastrado com sucesso!", Toast.LENGTH_LONG).show();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean confirmado = service.validarLogin(edtNome.getText().toString(), edtSenha.getText().toString());
                if (confirmado){
                    Toast.makeText(MainLogin.this, edtNome.getText().toString() +" logado com sucesso!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainLogin.this, "error, username or password not valid", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public static Context getContext(){
        return context;
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
