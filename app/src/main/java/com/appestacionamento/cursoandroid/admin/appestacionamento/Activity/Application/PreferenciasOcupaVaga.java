package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferenciasOcupaVaga {
    private Context context;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "preferenciaocupavaga";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String NUMERO="numero";
    private final String SETOR = "setor";
    private final String CHAVE="chave";
    private final String STATUS="status";
    private final String PLACA="placa";
    private final String EMAIL="email";
    private final Boolean VAGAESP = Boolean.valueOf("vaga");


    public  PreferenciasOcupaVaga(Context contextoParametro){
        context = contextoParametro;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }


    public void salvarOcupaVagaPreferences(String numero, String setor, String chave, String status, String placa, String email, Boolean vagaEsp){
        editor.clear();
        editor.putString(NUMERO, numero);
        editor.putString(SETOR, setor);
        editor.putString(CHAVE, chave);
        editor.putString(STATUS, status);
        editor.putString(PLACA, placa);
        editor.putString(EMAIL, email);
        editor.putBoolean(String.valueOf(VAGAESP), vagaEsp);
        editor.commit();
    }

    public String recuperaNumero(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(NUMERO, "");
    }

    public String recuperaSetor(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        return preferences.getString(SETOR, "");
    }

    public  String recuperaChave(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        return  preferences.getString(CHAVE,"");
    }

    public  String recuperaStatus(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        return  preferences.getString(STATUS,"");
    }

    public  String recuperaPlaca(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        return  preferences.getString(PLACA,"");
    }

    public  String recuperaEmail(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        return  preferences.getString(EMAIL,"");
    }

    public  String recuperaVagaEsp(Context context){
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        return  preferences.getString(String.valueOf(VAGAESP),"");
    }
}
