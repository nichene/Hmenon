package com.ufrpe.hmenon;


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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        service = new UsuarioService(MainLogin.getContext());
        edtNome = (EditText) findViewById(R.id.edtNomeCadastro);
        edtSenha = (EditText) findViewById(R.id.edtSenhaCadastro);
        edtConfirme = (EditText) findViewById(R.id.edtConfirme);
        btnCadastrar = (Button) findViewById(R.id.btnConfirmarCadastro);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setNome(edtNome.getText().toString());
                usuario.setSenha(edtSenha.getText().toString());
                service.validarCadastro(usuario);
                Toast.makeText(MainLogin.getContext(), "Cadastro realizados com sucesso!", Toast.LENGTH_LONG).show();
            }
        });


    }
}
