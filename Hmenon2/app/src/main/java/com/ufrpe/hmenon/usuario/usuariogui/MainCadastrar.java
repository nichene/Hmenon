package com.ufrpe.hmenon.usuario.usuariogui;


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
import com.ufrpe.hmenon.usuario.dominio.Usuario;
import com.ufrpe.hmenon.usuario.usuarioservice.UsuarioService;

public class MainCadastrar extends ActionBarActivity {

    private Button btnCadastrar;
    private EditText edtNome;
    private EditText edtConfirme;
    private EditText edtSenha;
    private UsuarioService service;
    private String senhaString;
    private String senhaConfirmadaString;

    public boolean isReady(EditText editText){
        return editText.getText().toString().trim().length() > 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        service = new UsuarioService(MainLogin.getContext());
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

                    Usuario usuario = new Usuario();
                    usuario.setNome(edtNome.getText().toString());
                    usuario.setSenha(edtSenha.getText().toString());

                    boolean cadastro = service.validarCadastro(usuario);

                    if (cadastro) {

                        Toast.makeText(MainCadastrar.this, "Cadastro realizados com sucesso!", Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(MainCadastrar.this, MainActivity.class);
                        startActivity(intent2);
                    }


                } else {
                    Toast.makeText(MainCadastrar.this, "As senhas não estão equivalentes!", Toast.LENGTH_LONG).show();
                }
            }

        });


    }
}
