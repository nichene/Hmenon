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
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.user.domain.User;
import com.ufrpe.hmenon.user.service.UserBusiness;

/**
 * Activity responsável pelo cadastro de novos usuários.
 */
public class MainSignUp extends ActionBarActivity {
    private Button btnCadastrar;
    private EditText edtEmail;
    private EditText edtName;
    private EditText edtConfirmPassword;
    private EditText edtPassword;
    private UserBusiness service;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoLogin = new Intent(MainSignUp.this, MainLogin.class);
        startActivity(intentGoLogin);
    }

    /**
     * Verifica se o campo de texto fornecido tem comprimento maior que o argumento
     * <code>minimumLength</code>.
     *
     * @param editText Campo de texto a ser verificado se possui um número mínimo de caracteres.
     * @param minimumLength Valor mínimo de caracteres permitidos.
     * @return Booleano referente ao campo possuir ou não um número de caracteres maior que o
     * limite mínimo.
     */
    public boolean isReady(EditText editText, int minimumLength){
        return editText.getText().toString().trim().length() > minimumLength;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        StaticUser.setContext(this);

        service = new UserBusiness(StaticUser.getContext());
        edtEmail  = (EditText) findViewById(R.id.edtEmailSignUp);
        edtName = (EditText) findViewById(R.id.edtNameSignUp);
        edtPassword = (EditText) findViewById(R.id.edtPasswordSignUp);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirm);
        btnCadastrar = (Button) findViewById(R.id.btnConfirmSignUp);


        edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                btnCadastrar.setEnabled(isReady(edtName, 3) && isReady(edtPassword, 3)
                        && isReady(edtConfirmPassword, 3));
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmedPassword = edtConfirmPassword.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setPassword(password);

                try {
                    service.checkSignUp(user, confirmedPassword);
                    finish();
                    startActivity(new Intent(MainSignUp.this, MainLogin.class));
                } catch (Exception e){
                    Toast.makeText(MainSignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
