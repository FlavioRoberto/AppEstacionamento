package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class SobreActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        toolbar = (Toolbar)findViewById(R.id.toolbarId);

        //configurar toolbar
        toolbar.setTitle("Sobre");
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void adicionaMascara() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior:voltar();break;
            case R.id.menu_sair:sair();break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sair() {
        sairAplicacao.logout(getApplicationContext(),this);
    }

    @Override
    public void sobre() {

    }

    @Override
    public void voltar() {
        preferencias = new Preferencias(SobreActivity.this);
        invocaActivitys.invocaPrincipal(SobreActivity.this,this,preferencias.recuperaTipo(SobreActivity.this));
    }


}
