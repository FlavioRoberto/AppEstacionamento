package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class configuracaoFirebase {
    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacao;

    //static para nao precisar toda vez instanciar o objeto
    public static DatabaseReference getFirebase(){
        if(referenciaFirebase == null) {
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return  referenciaFirebase;
    }

    public static  FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return  autenticacao;
    }
}
