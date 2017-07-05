package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Admin on 04/07/2017.
 */

public class sairAplicacao {

    public static void logout(Context context, Activity activity){
        FirebaseAuth auth = configuracaoFirebase.getFirebaseAutenticacao();
        activity.finish();
        auth.signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        activity.startActivity(intent);

    }

}
