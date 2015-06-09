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

import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.user.service.UserBusiness;

public class MainEditUserPassword extends ActionBarActivity {

    private EditText edtEditPassword;
    private EditText edtPassword;
    private EditText edtConfirmEditPassword;
    private Button btnConfirmEditPassword;
    private UserBusiness service;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoMain = new Intent(MainEditUserPassword.this, MainActivity.class);
        startActivity(intentGoMain);
    }

    public boolean isReady(EditText editText, int i){
        return editText.getText().toString().trim().length() > i;
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        service = new UserBusiness(MainLogin.getContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edituserpassword);
        edtPassword = (EditText) findViewById(R.id.edtPasswordEditPassword);
        edtEditPassword = (EditText) findViewById(R.id.edtPasswordEdit);
        edtConfirmEditPassword = (EditText) findViewById(R.id.edtConfirmPasswordEdit);
        btnConfirmEditPassword = (Button) findViewById(R.id.btnConfirmPasswordEdit);
        edtConfirmEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnConfirmEditPassword.setEnabled(isReady(edtPassword, 3) && isReady(edtEditPassword, 3) && isReady(edtConfirmEditPassword, 3));

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
                    Toast.makeText(MainEditUserPassword.this, "Alterações feitas com sucesso", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } catch (Exception e){
                    Toast.makeText(MainEditUserPassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
