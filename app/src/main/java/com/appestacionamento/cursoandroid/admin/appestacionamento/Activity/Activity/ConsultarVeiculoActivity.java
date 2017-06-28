package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class ConsultarVeiculoActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarveiculo);

        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Consulta veículo");
        setSupportActionBar(toolbar);

    }



    //ao clicar nas opções do menu seleciona uma ação
    //invoca os itens no menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //opcoes do item do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior: voltar();break;
            case R.id.menu_meusdados: break;
            case R.id.menu_sair: sair();break;
            default:break;
        }
        return true;
    }

    //desloga usuario e vai pra tela de login
    public void sair(){
        Usuario usuario = new Usuario();
        usuario.desloga();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //metodo para voltar a menu principal
    @Override
    public void voltar() {
        Intent intent = new Intent(getApplicationContext(),AdmActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void adicionaMascara() {

    }
}
