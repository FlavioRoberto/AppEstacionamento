package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenciasVaga {

    private Context context;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "preferenciavaga";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String CHAVE_NUMERO="numero";
    private final String CHAVE_SETOR="setor";
    private final String CHAVE="chave",CHAVE_STATUS = "status",CHAVE_PLACAVEICULO="placa";
    private final Boolean CHAVE_VAGAESPECIAL=false;

    public  PreferenciasVaga(Context contextoParametro){
        context = contextoParametro;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarVagaPreferences(String numero, String setor, String chave, Boolean vagaEspecial,String status,String placa){
        editor.clear();
        editor.putString(CHAVE_NUMERO, numero);
        editor.putString(CHAVE_SETOR, setor);
        editor.putString(CHAVE, chave);
        editor.putBoolean(String.valueOf(CHAVE_VAGAESPECIAL), vagaEspecial);
        editor.putString(String.valueOf(CHAVE_STATUS),status);
        editor.putString(String.valueOf(CHAVE_PLACAVEICULO),status);
        editor.commit();
    }

    public String recuperaNumero(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(CHAVE_NUMERO, "");
    }

    public String recuperaStatus(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(CHAVE_STATUS, "");
    }


    public String recuperaSetor(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(CHAVE_SETOR, "");
    }

    public String recuperaChave(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(CHAVE, "");
    }

    public boolean recuperaVagaEspecial(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getBoolean(String.valueOf(CHAVE_VAGAESPECIAL),false);
    }


}
