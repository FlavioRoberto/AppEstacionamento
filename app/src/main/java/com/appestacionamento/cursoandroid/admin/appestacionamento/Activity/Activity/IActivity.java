package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


/**
 * Created by Admin on 27/06/2017.
 */

public interface IActivity  {


    public boolean onCreateOptionsMenu(Menu menu);
    public void adicionaMascara();
    public boolean onOptionsItemSelected(MenuItem item) ;
    //desloga usuario e vai pra tela de login
    public void sair();
    //retorna para a página inicial
    public  void voltar();

    }


