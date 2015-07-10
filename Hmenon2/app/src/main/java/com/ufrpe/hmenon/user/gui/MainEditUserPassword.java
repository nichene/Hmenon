package com.ufrpe.hmenon.user.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.infrastructure.gui.MainInitial;
import com.ufrpe.hmenon.user.service.UserBusiness;

/**
 * Activity responsável por alterar a senha do usuário.
 */
public class MainEditUserPassword extends ActionBarActivity {

    private EditText edtEditPassword;
    private EditText edtPassword;
    private EditText edtConfirmEditPassword;
    private Button btnConfirmEditPassword;
    private UserBusiness service;
    private Context currentContext = MainEditUserPassword.this;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoMain = new Intent(MainEditUserPassword.this, MainActivity.class);
        startActivity(intentGoMain);
    }

    /**
     * Verifica se o Campo de texto fornecido tem comprimento maior que o argumento minimumLength.
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
        service = new UserBusiness(MainInitial.getContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituserpassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);

        edtPassword = (EditText) findViewById(R.id.edtPasswordEditPassword);
        edtEditPassword = (EditText) findViewById(R.id.edtPasswordEdit);
        edtConfirmEditPassword = (EditText) findViewById(R.id.edtConfirmPasswordEdit);
        btnConfirmEditPassword = (Button) findViewById(R.id.btnConfirmPasswordEdit);

        edtConfirmEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                btnConfirmEditPassword.setEnabled(isReady(edtPassword, 3)
                        && isReady(edtEditPassword, 3) && isReady(edtConfirmEditPassword, 3));
            }
        });

        btnConfirmEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edtPassword.getText().toString();
                String password = edtEditPassword.getText().toString();
                String confirmedPassword = edtConfirmEditPassword.getText().toString();

                try {
                    service.checkPasswordUpdate(password, confirmedPassword, oldPassword);
                    StaticUser.getUser().setPassword(password);

                    Toast.makeText(MainEditUserPassword.this, getString(R.string.user_update_success),
                            Toast.LENGTH_LONG).show();

                    onBackPressed();
                } catch (Exception e){
                    Toast.makeText(MainEditUserPassword.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

        });
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
