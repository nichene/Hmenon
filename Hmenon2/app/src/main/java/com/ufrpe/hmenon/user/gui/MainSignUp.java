package com.ufrpe.hmenon.user.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.hmenon.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.domain.User;
import com.ufrpe.hmenon.user.service.userService;

public class MainSignUp extends ActionBarActivity {

    private Button btnCadastrar;
    private EditText edtNome;
    private EditText edtConfirme;
    private EditText edtSenha;
    private userService service;
    private String senhaString;
    private String senhaConfirmadaString;

    public boolean isReady(EditText editText){
        return editText.getText().toString().trim().length() > 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        service = new userService(MainLogin.getContext());
        edtNome = (EditText) findViewById(R.id.edtNomeCadastro);
        edtSenha = (EditText) findViewById(R.id.edtSenhaCadastro);
        edtConfirme = (EditText) findViewById(R.id.edtConfirme);
        btnCadastrar = (Button) findViewById(R.id.btnConfirmarCadastro);


        edtConfirme.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnCadastrar.setEnabled(isReady(edtNome) && isReady(edtSenha) && isReady(edtConfirme));

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senhaString = edtSenha.getText().toString();
                senhaConfirmadaString = edtConfirme.getText().toString();


                if (senhaString.equals(senhaConfirmadaString)) {

                    User user = new User();
                    user.setNome(edtNome.getText().toString());
                    user.setSenha(edtSenha.getText().toString());

                    User u = service.checkSignUp(user);



                    if (u != null) {

                        StaticUser sUser = new StaticUser();
                        StaticUser.setUser(user);


                        Intent intentGoMain = new Intent(MainSignUp.this, MainActivity.class);
                        startActivity(intentGoMain);
                    }


                } else {
                    Toast.makeText(MainSignUp.this, "As senhas não estão equivalentes!", Toast.LENGTH_LONG).show();
                }
            }

        });


    }
}
