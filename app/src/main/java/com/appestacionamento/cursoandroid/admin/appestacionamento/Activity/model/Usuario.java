package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.model;


import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Usuario extends Pessoa{

    private DatabaseReference firebaseReferences;

    private String tipo;

    public Usuario(String nome, String uid, String telefone, String email, boolean status, int cpf, String tipo) {
        super(nome, uid, telefone, email, status, cpf);
        this.tipo = tipo;
        firebaseReferences = configuracaoFirebase.getFirebase();
    }

    public void create(){
        firebaseReferences.child("usuarios").child(getUid()).setValue(this);
    }

    public String getTipo() {
        return tipo;
    }


}
