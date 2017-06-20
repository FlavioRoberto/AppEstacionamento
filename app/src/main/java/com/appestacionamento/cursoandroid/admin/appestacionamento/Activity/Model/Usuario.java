package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;


import android.content.Intent;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;


public class Usuario {
    private String nome;
    private String uid;
    private String telefone;
    private String email;


    private String tipo;
    private  String senha ;
    private boolean status;
    private int cpf;
    private DatabaseReference firebaseReferences;
    private FirebaseAuth autenticacao;


    public Usuario() {
        firebaseReferences = configuracaoFirebase.getFirebase();
        autenticacao = configuracaoFirebase.getFirebaseAutenticacao();
    }

    public void Create() {
        firebaseReferences.child("users").child(getUid()).setValue(this);
    }

    public void desloga(){
        autenticacao.signOut();
    }

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    //@Exclude nao upa pro banco o campo
    @Exclude
    public String getUid() {
        return uid;
    }



    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getTelefone() {
        return telefone;
    }

    public String getCurrentUid(){
        return getAutenticacao().getCurrentUser().getUid();
    }

    public FirebaseAuth getAutenticacao() {
        return autenticacao;
    }

    public DatabaseReference getFirebaseReferences() {
        return firebaseReferences;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Exclude
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;

    }

    public boolean isStatus() {
        return status;
    }


    public int getCpf() {
        return cpf;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

