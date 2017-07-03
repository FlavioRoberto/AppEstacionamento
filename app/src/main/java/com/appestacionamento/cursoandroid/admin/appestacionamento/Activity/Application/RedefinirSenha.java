package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.LoginActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.RedefinirSenhaActivity;
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

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Email enviado.", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Email não enviado.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
