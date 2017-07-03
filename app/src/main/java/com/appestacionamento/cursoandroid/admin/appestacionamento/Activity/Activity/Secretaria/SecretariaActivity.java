package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Secretaria;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.LoginActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.TabAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.SlidingTabLayout;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.auth.FirebaseAuth;

public class SecretariaActivity extends AppCompatActivity implements IActivity {

    private TabAdapter tabAdapter;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private String[] abas = new String[]{"Usuário","Veículos"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretaria);
/*

        //sliding tab layout
        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.stl_secretaria);

        //distribui telas proporcionais
        slidingTabLayout.setDistributeEvenly(true);

        //view pager
        viewPager = (ViewPager)findViewById(R.id.vp_secretaria);
  */
        //Tollbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Secretaria");
        setSupportActionBar(toolbar);

 /*
        //configurando adapter
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.setUsuario("SECRETARIA");
        tabAdapter.setAbas(abas);
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
        */

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

           case R.id.menu_anterior: voltar();break;
           case  R.id.menu_meusdados:break;
           case  R.id.menu_sair: sair();break;
       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void adicionaMascara() {

    }

    @Override
    public void sair() {
        FirebaseAuth auth = configuracaoFirebase.getFirebaseAutenticacao();
        auth.signOut();
        finish();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void voltar() {
        return;
    }
}
