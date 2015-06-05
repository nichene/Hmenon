package com.ufrpe.hmenon.user.gui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import com.ufrpe.hmenon.user.domain.User;
import android.widget.EditText;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.user.service.UserBusiness;

public class MainEditName extends ActionBarActivity {

    private Button btnConfirmEditName;
    private EditText edtConfirmNameEdit;
    private EditText edtNameEdit;
    private UserBusiness service;


    public boolean isReady(EditText editText, int i){
        return editText.getText().toString().trim().length() > i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editname);
        btnConfirmEditName = (Button) findViewById(R.id.btnConfirmEditName);
        edtConfirmNameEdit = (EditText) findViewById(R.id.edtConfirmEditName);
        edtNameEdit = (EditText) findViewById(R.id.edtNameEdit);

        edtConfirmNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               btnConfirmEditName.setEnabled(isReady(edtNameEdit, 3) && isReady(edtConfirmNameEdit, 3));
            }

        });

        btnConfirmEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = edtNameEdit.getText().toString();
                String confirmedNewName = edtConfirmNameEdit.getText().toString();


            }


        });


    }
}
