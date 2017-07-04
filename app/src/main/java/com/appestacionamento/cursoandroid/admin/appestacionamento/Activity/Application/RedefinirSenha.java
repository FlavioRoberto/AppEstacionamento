package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.LoginActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.RedefinirSenhaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RedefinirSenha {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Context context;


    public RedefinirSenha(Context context) {
        this.context = context;
    }

    public void redefineSenha(String email){

        if(email.isEmpty()) {
            Snackbar snackbar = Snackbar.make(((Activity)context).findViewById(R.id.coordinator_Id),"Campos vazios",Snackbar.LENGTH_LONG);
        }else {

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    Snackbar snackbar = Snackbar
                                            .make(((Activity) context).findViewById(R.id.coordinator_Id), "Email enviado", Snackbar.LENGTH_LONG);

                                    snackbar.show();

                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(((Activity) context).findViewById(R.id.coordinator_Id), "Email não enviado", Snackbar.LENGTH_LONG);

                                    snackbar.show();
                                }
                        }
                    });
        }
    }

}
