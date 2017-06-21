package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.TabAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.SlidingTabLayout;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.auth.FirebaseAuth;

public class TelaAdm extends AppCompatActivity {
    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.stlayout);
        viewPager = (ViewPager)findViewById(R.id.vpLayout);

        //distribui as tabas proporcionalmente na tela
        slidingTabLayout.setDistributeEvenly(true);
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("BM");

        setSupportActionBar(toolbar);

        //configurando Adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);


        slidingTabLayout.setViewPager(viewPager);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair : sair();break;
            case R.id.menu_meusdados:chamaConsulta();break;


        }

        return true;
    }

    private void sair(){
        FirebaseAuth auth = configuracaoFirebase.getFirebaseAutenticacao();
        auth.signOut();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void chamaConsulta(){
        Intent intent = new Intent(getApplication(),consultaUsuarioActivity.class);
        startActivity(intent);
        //finish();
    }
}
