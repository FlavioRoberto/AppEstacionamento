package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.TabAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.SlidingTabLayout;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class GaragistaActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private static final String[] ABAS = new String[]{"VAGAS","VEICULO"};
    private static final String USUARIO = "GARAGISTA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garagista);

        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.stl_garagista);
        viewPager = (ViewPager)findViewById(R.id.vp_garagista);

        //preparando toolbar
        toolbar.setTitle("Garagista");
        setSupportActionBar(toolbar);

        //distribuidndo sliding tab proprcionalmente
        slidingTabLayout.setDistributeEvenly(true);

        //preparando adapter
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.setUsuario(USUARIO);
        adapter.setAbas(ABAS);

        //preparando view page
        viewPager.setAdapter(adapter);

        //preparando sliding tab
        slidingTabLayout.setViewPager(viewPager);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior: voltar();break;
            case R.id.menu_sair:sair();break;
            case R.id.menu_sobre:sobre();break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onFragmentViewCreated(View view) {
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
