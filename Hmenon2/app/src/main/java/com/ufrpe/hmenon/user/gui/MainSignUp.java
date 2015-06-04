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
import com.ufrpe.hmenon.user.domain.User;
import com.ufrpe.hmenon.user.service.UserBusiness;

public class MainSignUp extends ActionBarActivity {

    private Button btnCadastrar;
    private EditText edtName;
    private EditText edtConfirmPassword;
    private EditText edtPassword;
    private UserBusiness service;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentGoLogin = new Intent(MainSignUp.this, MainLogin.class);
        startActivity(intentGoLogin);
    }
    public boolean isReady(EditText editText, int i){
        return editText.getText().toString().trim().length() > i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        service = new UserBusiness(MainLogin.getContext());
        edtName = (EditText) findViewById(R.id.edtNameSignUp);
        edtPassword = (EditText) findViewById(R.id.edtPasswordSignUp);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirm);
        btnCadastrar = (Button) findViewById(R.id.btnConfirmSignUp);


        edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnCadastrar.setEnabled(isReady(edtName, 3) && isReady(edtPassword, 3) && isReady(edtConfirmPassword, 3));

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmedPassword = edtConfirmPassword.getText().toString();
                User user = new User();
                user.setName(name);
                user.setPassword(password);
                try {
                    service.checkSignUp (user, confirmedPassword);
                    finish();
                    startActivity(new Intent(MainSignUp.this, MainActivity.class));
                } catch (Exception e){
                    Toast.makeText(MainSignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });


    }
}
