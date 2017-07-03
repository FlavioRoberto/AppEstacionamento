package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.RedefinirSenha;

public class RedefinirSenhaActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonEnviar;
    private String email;
    private RedefinirSenha redefinirSenha = new RedefinirSenha(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        editTextEmail = (EditText) findViewById(R.id.editTextEmailRedefinirSenha);
        buttonEnviar = (Button) findViewById(R.id.buttonRedefinirSenha);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviaEmail();
            }
        });

    }

    private void enviaEmail(){
        email = editTextEmail.getText().toString().toLowerCase().trim();
        redefinirSenha.redefineSenha(email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
