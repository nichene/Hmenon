package com.ufrpe.hmenon;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainCadastrar extends ActionBarActivity {

    private Button btnCadastrar;
    private EditText edtNome;
    private EditText edtConfirme;
    private EditText edtSenha;
    private UsuarioService service;
    private String senhaString;
    private String senhaConfirmadaString;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        service = new UsuarioService(MainLogin.getContext());
        edtNome = (EditText) findViewById(R.id.edtNomeCadastro);
        edtSenha = (EditText) findViewById(R.id.edtSenhaCadastro);
        edtConfirme = (EditText) findViewById(R.id.edtConfirme);
        btnCadastrar = (Button) findViewById(R.id.btnConfirmarCadastro);


        senhaString = edtSenha.getText().toString();
        senhaConfirmadaString = edtConfirme.getText().toString();


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (senhaString.equals(senhaConfirmadaString)) {

                    Usuario usuario = new Usuario();
                    usuario.setNome(edtNome.getText().toString());
                    usuario.setSenha(edtSenha.getText().toString());

                    service.validarCadastro(usuario);

                    Toast.makeText(MainCadastrar.this, "Cadastro realizados com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(MainCadastrar.this, MainActivity.class);
                    startActivity(intent2);


                } else {
                    Toast.makeText(MainCadastrar.this, "As senhas não estão equivalentes!", Toast.LENGTH_LONG).show();
                }
            }

        });


    }
}
