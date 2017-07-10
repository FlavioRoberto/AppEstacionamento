package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * Created by renna on 09/07/2017.
 */

public class GaragistaActivityAlteraStatus extends AppCompatActivity implements IActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_garagista_altera_status);
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
