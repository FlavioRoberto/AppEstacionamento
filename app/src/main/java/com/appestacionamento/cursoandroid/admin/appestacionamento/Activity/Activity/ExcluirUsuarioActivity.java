package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class ExcluirUsuarioActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluiveiculo);

        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Excluir Veículo");
        setSupportActionBar(toolbar);

    }



    //ao clicar nas opções do menu seleciona uma ação
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_anterior:voltar();break;
            case R.id.menu_meusdados:return true;
            case R.id.menu_sair: sair();break;
            default:return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sair() {

    }

    //metodo para voltar a menu principal
    @Override
    public void voltar() {
        Intent intent = new Intent(getApplicationContext(),AdmActivity.class);
        startActivity(intent);
        finish();
    }

    //cria o menu da toolbar de acordo com o arquivo do menu xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void adicionaMascara() {

    }
}
