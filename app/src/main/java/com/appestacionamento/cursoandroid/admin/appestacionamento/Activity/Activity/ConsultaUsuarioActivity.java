package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class ConsultaUsuarioActivity extends AppCompatActivity implements IActivity{

        private Button btnCadastrar;
        private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //invocacao do toolbar

        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Consulta de usuário");
        setSupportActionBar(toolbar);

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menu_anterior: voltar();break;
            case R.id.menu_sair:sair();break;
            default: return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void sair() {
       Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void voltar() {
        Intent intent = new Intent(getApplicationContext(),AdmActivity.class);
        startActivity(intent);
        finish();

    }
}
