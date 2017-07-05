package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.AdmActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Secretaria.SecretariaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.SobreActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario.ActivityUsuario;


public class invocaActivitys {

    private  final static String USER = "USER", ADM ="ADM",SECRETARIA ="SECRETARIA",GARAGISTA="GARAGISTA";

    public invocaActivitys() {
    }

    public static void invocaSobre(Context context, Activity activity) {
        Intent intent = new Intent(context.getApplicationContext(), SobreActivity.class);
        context.startActivity(intent);
    }

    public static void invocaPrincipal(Context context, Activity activity, String usuario){
         Intent intent;
        switch (usuario){
            case USER: intent = new Intent(context, ActivityUsuario.class);
                context.startActivity(intent);activity.finish();break;
            case ADM: intent = new Intent(context, AdmActivity.class);
                context.startActivity(intent);activity.finish();break;
            case SECRETARIA: intent = new Intent(context, SecretariaActivity.class);
                context.startActivity(intent);activity.finish();break;
            default:break;

        }
    }
}
