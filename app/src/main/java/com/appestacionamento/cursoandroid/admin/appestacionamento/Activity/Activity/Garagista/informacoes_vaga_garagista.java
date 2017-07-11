package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.PreferenciasOcupaVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class informacoes_vaga_garagista extends AppCompatActivity implements IActivity {
    private Toolbar toolbar;
    private TextView txNumero, txSetor,txStatus,txPlaca,txVagaEspecial,txEmailProprietario;
    private PreferenciasOcupaVaga preferenciasOcupaVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_vaga_garagista);

        //invocando componentes
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        txNumero = (TextView)findViewById(R.id.tv_numero);
        txSetor = (TextView)findViewById(R.id.tv_setor);
        txStatus = (TextView)findViewById(R.id.tv_status);
        txPlaca = (TextView)findViewById(R.id.tv_Placa);
        txVagaEspecial = (TextView)findViewById(R.id.tv_vagaEspecial);
        txEmailProprietario = (TextView)findViewById(R.id.tv_email);
        preferenciasOcupaVaga = new PreferenciasOcupaVaga(informacoes_vaga_garagista.this);

        //PREPARANDO TOOLBAR
        toolbar.setTitle("Informações da vaga");
        setSupportActionBar(toolbar);

        setValorVaga();


    }

    public void setValorVaga(){
       if(preferenciasOcupaVaga!= null){
        txNumero.setText(preferenciasOcupaVaga.recuperaNumero(informacoes_vaga_garagista.this));
        txSetor.setText(preferenciasOcupaVaga.recuperaSetor(informacoes_vaga_garagista.this));
        txStatus.setText(preferenciasOcupaVaga.recuperaStatus(informacoes_vaga_garagista.this));
        txPlaca.setText(preferenciasOcupaVaga.recuperaPlaca(informacoes_vaga_garagista.this));txEmailProprietario.setText(preferenciasOcupaVaga.recuperaEmail(informacoes_vaga_garagista.this));
       if(preferenciasOcupaVaga.recuperaVagaEsp(informacoes_vaga_garagista.this) == true) {
           txVagaEspecial.setText("SIM");
       }else {
           txVagaEspecial.setText("NÃO");
       }

    }}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior: voltar();break;
            case R.id.menu_meusdados: break;
            case R.id.menu_sair: sair();break;
            case R.id.menu_sobre:sobre();break;
            default:break;
        }
        return true;
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
    public void sair() {
        sairAplicacao.logout(getApplicationContext(),this);
    }

    @Override
    public void sobre() {

    }

    @Override
    public void voltar() {
        finish();
        Intent intent = new Intent(informacoes_vaga_garagista.this, BuscarVagaListaActivity.class);
        startActivity(intent);
    }
}
