package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "preferenciawhatsapp";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String CHAVE_EMAIL="email";
    private final String CHAVE_TIPO="tipo";
    private final String CHAVE_SENHA="senha";

    public  Preferencias(Context contextoParametro){
        context = contextoParametro;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }


    public void salvarusuarioPreferences(String email, String senha, String tipo){
        editor.clear();
        editor.putString(CHAVE_EMAIL, email);
        editor.putString(CHAVE_SENHA, senha);
        editor.putString(CHAVE_TIPO,tipo);
        editor.commit();
    }

    public String recuperaEmail(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(CHAVE_EMAIL, "");
    }

    public String recuperaSenha(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(CHAVE_SENHA, "");
    }

    public  String recuperaTipo(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        return  preferences.getString(CHAVE_TIPO,"");
    }

}