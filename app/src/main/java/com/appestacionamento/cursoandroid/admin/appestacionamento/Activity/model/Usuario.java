package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.model;


import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Usuario {
    private String nome, uid, telefone, email, tipo;
    private final String senha = "200200";
    private boolean status;
    private int cpf;
    private DatabaseReference firebaseReferences;

    public Usuario(String nome, String uid, String telefone, String email, boolean status, int cpf, String tipo) {
        this.nome = nome;
        this.uid = uid;
        this.telefone = telefone;
        this.email = email;
        this.status = status;
        this.cpf = cpf;
        this.tipo = tipo;
        firebaseReferences = configuracaoFirebase.getFirebase();
    }

    public void create(){
        firebaseReferences.child("users").child(getUid()).setValue(this);
    }

    public String getNome() {
        return nome;
    }

    public String getUid() {
        return uid;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isStatus() {
        return status;
    }

    public int getCpf() {
        return cpf;
    }
}
