package com.ufrpe.hmenon.user.gui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ufrpe.hmenon.R;


public class MainEdit extends ActionBarActivity {

    private Button btnConfirmEdit;
    private EditText edtNameEdit;
    private EditText edtConfirmPasswordEdit;
    private EditText edtPasswordEdit;


    public boolean isReady(EditText editText, int i){
        return editText.getText().toString().trim().length() > i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);

        btnConfirmEdit = (Button) findViewById(R.id.btnConfirmEdit);
        edtConfirmPasswordEdit = (EditText) findViewById(R.id.edtConfirmEdit);
        edtNameEdit = (EditText) findViewById(R.id.edtNameEdit);
        edtPasswordEdit = (EditText) findViewById(R.id.edtPasswordEdit);

        edtConfirmPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnConfirmEdit.setEnabled(isReady(edtNameEdit, 3) && isReady(edtPasswordEdit, 3) && isReady(edtConfirmPasswordEdit, 3));
            }

        });

        btnConfirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String newName = edtNameEdit.getText().toString();
                String newPassword = edtPasswordEdit.getText().toString();
                String confirmedNewPassword = edtConfirmPasswordEdit.getText().toString();


            }


        });
}
}

