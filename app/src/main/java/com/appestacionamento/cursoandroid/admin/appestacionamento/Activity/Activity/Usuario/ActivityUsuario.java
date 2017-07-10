package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.SobreActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.TabAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.SlidingTabLayout;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class ActivityUsuario extends AppCompatActivity implements IActivity {

    private SlidingTabLayout slidingTabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private final static String[] ABAS = new String[]{"PRINCIPAL","VAGA"};
    private final static String USUARIO = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);


        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.slt_usuario);
        viewPager = (ViewPager)findViewById(R.id.vp_usuario);

        //preparar a toolbar
        toolbar.setTitle("Tela Principal");
        setSupportActionBar(toolbar);

        //preparar o adapter
        adapter.setAbas(ABAS);
        adapter.setUsuario(USUARIO);

        //distribuir o sliding tab layout proporcionalmente na tela
        slidingTabLayout.setDistributeEvenly(true);

        //preparar o sliding tabLayout
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);


    }

    public void onFragmentViewCreated(View view) {
        
    }


    //ao selecionar o item da toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_anterior:voltar();break;
            case R.id.menu_sair:sair();break;
            case  R.id.menu_sobre:sobre();break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }

    //dar funções aos icones da toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }



    //metodos para sair logout
    @Override
    public void sair() {
        sairAplicacao.logout(ActivityUsuario.this,this);

    }

    @Override
    public void sobre(){
        invocaActivitys.invocaSobre(ActivityUsuario.this,this);
    }

    //metodo para voltar
    @Override
    public void voltar() {

    }

    //metodo para adicionar mascara
    @Override
    public void adicionaMascara() {

    }

}
