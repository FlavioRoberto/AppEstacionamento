package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class ConsultaVaga extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_vaga);

        //Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Consultar Vaga");
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.menu_anterior:voltar();break;
           case R.id.menu_sair:sair();break;
           case R.id.menu_sobre: sobre();break;
       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void adicionaMascara() {

    }

    @Override
    public void sair() {
        sairAplicacao.logout(this,this);
    }

    @Override
    public void sobre() {
        invocaActivitys.invocaSobre(this,this);
    }

    @Override
    public void voltar() {
        Preferencias preferencias = new Preferencias(this);
        invocaActivitys.invocaPrincipal(this,this,preferencias.recuperaTipo(this));
    }
}
