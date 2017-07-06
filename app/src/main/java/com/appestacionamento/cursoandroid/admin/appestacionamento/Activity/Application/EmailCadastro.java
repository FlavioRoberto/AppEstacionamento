package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.EmailAuthProvider;
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


    public void Verifica_Email(String email){

        final FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context,"Email Enviado para o Usuário",Toast.LENGTH_LONG).show();
                        }else{
                           try {
                               throw task.getException();
                           }catch (FirebaseNetworkException e){
                               Toast.makeText(context,"Erro de conexão",Toast.LENGTH_SHORT).show();
                           }catch (Exception e){
                               Toast.makeText(context,"Não foi possível enviar o email"+e.getMessage(),Toast.LENGTH_SHORT).show();
                           }
                        }

                    }
                });
    }

}
