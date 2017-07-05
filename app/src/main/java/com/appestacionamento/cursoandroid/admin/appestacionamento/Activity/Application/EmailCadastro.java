package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by renna on 05/07/2017.
 */


public class EmailCadastro {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Context context;
    private FirebaseUser user;

    public  EmailCadastro(Context context){
        this.context = context;
    }

   /* public  void VerificaEmail(String email){
        if(email.isEmpty()){
            Snackbar snackbar = Snackbar.make(((Activity)context).findViewById(R.id.coordinator_Id),"Campos vazios",Snackbar.LENGTH_LONG);
        }else {

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(R.id.coordinator_Id), "Email enviado", Snackbar.LENGTH_LONG);
                                snackbar.show();

                            } else {
                                Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(R.id.coordinator_Id), "Email não enviado", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                    });
        }
   */

    public void Verifica_Email(String email){
        final FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context,"Email Enviado",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,"Email não enviado",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

}
