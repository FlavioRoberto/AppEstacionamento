package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class ConsultaUsuarioActivity extends AppCompatActivity {

        private Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_consulta_usuario);

        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);

        //ao clicar no btnCadastrar chama tela cadastrar
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CadastroUsuarioActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



}
