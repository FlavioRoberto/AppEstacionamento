package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.PreferenciasOcupaVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * Created by renna on 09/07/2017.
 */

public class GaragistaActivityAlteraStatus extends AppCompatActivity implements IActivity {


    private TextView numeroVaga, setor,status,placaVeiculo;
    private CheckBox trocaStatus;
    private Button btnGuardar;
    private Toolbar toolbar;
    private PreferenciasOcupaVaga preferenciasOcupaVaga;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_garagista_altera_status);
/*
        numeroVaga = (TextView)findViewById(R.id.numerovaga_preencher);
        setor = (TextView)findViewById(R.id.setorvagaid);
        status = (TextView)findViewById(R.id.statuspreencher);
        placaVeiculo=(TextView)findViewById(R.id.statusPlaca);
        trocaStatus =(CheckBox)findViewById(R.id.checkBox);
        btnGuardar=(Button)findViewById(R.id.buttonatualizarvaga);
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        preferenciasOcupaVaga = new PreferenciasOcupaVaga(getApplicationContext());

        atualizaViews();
*/
    }

    public void atualizaViews(){
        if(preferenciasOcupaVaga != null){
            numeroVaga.setText(preferenciasOcupaVaga.recuperaNumero(this));
            setor.setText(preferenciasOcupaVaga.recuperaSetor(this));
            status.setText(preferenciasOcupaVaga.recuperaStatus(this));
            placaVeiculo.setText(preferenciasOcupaVaga.recuperaPlaca(this));
        }
    }


    @Override
    public void adicionaMascara() {

    }

    @Override
    public void sair() {

    }

    @Override
    public void sobre() {

    }

    @Override
    public void voltar() {

    }
}
