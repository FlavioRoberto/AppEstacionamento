package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class CadastroVeicuoActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veicuo);

        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Cadastro de veículo");

        setSupportActionBar(toolbar);

    }

    //sobrescreve metodo da interface IActivity para ativar os icones no menu
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


    //invoca os itens no menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }



    //desloga usuario e vai pra tela de login
    public void sair(){
        Usuario usuario = new Usuario();
        usuario.desloga();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //retorna para a página inicial
    public  void voltar(){
        Intent intent = new Intent(getApplicationContext(),AdmActivity.class);
        startActivity(intent);
        finish();
    }
}
