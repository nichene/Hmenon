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
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.gui.MainInitial;
import com.ufrpe.hmenon.user.domain.User;
import com.ufrpe.hmenon.user.service.UserBusiness;

/**
 * Activity responsável por efetuar o login do usuário no aplicativo.
 */
public class MainLogin extends ActionBarActivity {
    private Button btnCadastreSe;
    private Button btnLogin;
    private EditText edtEmail;
    private EditText edtPassword;
    private UserBusiness service;

    /**
     * Verifica se o campo de texto fornecido tem comprimento maior que o argumento
     * <code>minimumLength</code>.
     *
     * @param editText Campo de texto a ser verificado se possui um número mínimo de caracteres.
     * @param minimumLength Valor mínimo de caracteres permitidos.
     * @return Booleano referente a se o campo possui ou não um número de caracteres maior que o
     * limite mínimo.
     */
    public boolean isReady(EditText editText, int minimumLength){
        return editText.getText().toString().trim().length() > minimumLength;
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = new UserBusiness(MainInitial.getContext());

        edtEmail = (EditText)findViewById(R.id.edtNome);
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
                btnLogin.setEnabled(isReady(edtEmail, 3) && isReady(edtPassword, 3));
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

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);

                try {
                    service.checkLogin(user);
                    Toast.makeText(MainLogin.this, StaticUser.getUser().getName()
                            + getString(R.string.login_success), Toast.LENGTH_LONG).show();

                    finish();
                    Intent intentGoMain = new Intent(MainLogin.this, MainActivity.class);
                    startActivity(intentGoMain);
                } catch (Exception e){
                    Toast.makeText(MainLogin.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
