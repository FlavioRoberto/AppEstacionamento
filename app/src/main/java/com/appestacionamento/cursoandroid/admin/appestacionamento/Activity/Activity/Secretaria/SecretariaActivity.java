package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Secretaria;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.ConsultaUsuarioActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.LoginActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.TabAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.SlidingTabLayout;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class SecretariaActivity extends AppCompatActivity implements IActivity {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String[] abas = new String[]{"USUÁRIOS","VEÍCULOS"};
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretaria);

        //TOOLBAR
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Secretaria");
        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.stl_secretaria);
        viewPager = (ViewPager)findViewById(R.id.vp_secretaria);


             //configurando adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.setAbas(abas);
        tabAdapter.setUsuario("SECRETARIA");
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair :
                sair();
                //finish();
                break;
            case R.id.menu_meusdados:
                chamaConsulta();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sair() {
        modelUsuario usuario = new modelUsuario();
        usuario.desloga();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void voltar() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void chamaConsulta(){
        Intent intent = new Intent(getApplication(),ConsultaUsuarioActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void adicionaMascara() {

    }
}
