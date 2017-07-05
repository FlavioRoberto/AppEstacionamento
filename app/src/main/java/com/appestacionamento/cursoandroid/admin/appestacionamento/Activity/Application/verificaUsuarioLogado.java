package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.content.Context;

import static java.security.AccessController.getContext;

/**
 * Created by Admin on 04/07/2017.
 */

public  class verificaUsuarioLogado {

    public verificaUsuarioLogado() {
    }

    public static String verificaUsuarioLogado( Context context){
       Preferencias preferencias = new Preferencias(context);
        String usuario = preferencias.recuperaTipo(context) ;
        return usuario;
    }

}
