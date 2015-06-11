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
import com.ufrpe.hmenon.infrastructure.gui.MainInitial;
import com.ufrpe.hmenon.user.service.UserBusiness;


public class MainEditUserName extends ActionBarActivity{

    private EditText edtEditName;
    private EditText edtPassword;
    private EditText edtConfirmEditName;
    private Button btnConfirmEditName;
    private UserBusiness service;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoMain = new Intent(MainEditUserName.this, MainActivity.class);
        startActivity(intentGoMain);
    }

    public boolean isReady(EditText editText, int i){
        return editText.getText().toString().trim().length() > i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editusername);

        service = new UserBusiness(MainInitial.getContext());
        edtPassword = (EditText) findViewById(R.id.edtNameEditPassword);
        edtEditName = (EditText) findViewById(R.id.edtNameEdit);
        edtConfirmEditName = (EditText) findViewById(R.id.edtConfirmNameEdit);
        btnConfirmEditName = (Button) findViewById(R.id.btnConfirmEdit);
        edtConfirmEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnConfirmEditName.setEnabled(isReady(edtEditName, 3) && isReady(edtConfirmEditName, 3));

            }
        });
        btnConfirmEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edtPassword.getText().toString();
                String name = edtEditName.getText().toString();
                String confirmedName = edtConfirmEditName.getText().toString();
                try {
                    service.checkNameUpdate(name, confirmedName, oldPassword);
                    StaticUser.getUser().setPassword(name);
                    Toast.makeText(MainEditUserName.this, "Alterações feitas com sucesso", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } catch (Exception e) {
                    Toast.makeText(MainEditUserName.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
