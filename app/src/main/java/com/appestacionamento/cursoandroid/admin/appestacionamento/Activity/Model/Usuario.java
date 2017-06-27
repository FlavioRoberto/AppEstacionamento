package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;


import android.content.Intent;
import android.text.TextUtils;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.LoginActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;



public class Usuario {
    private String nome;
    private String uid;//chave do usuario
    private String telefone;
    private String email;
    private String tipo;//(USER, ADM, GARAGISTA, SECRETARIA)
    private String senha ;//Possui valor padrao (200200)
    private String status;//(ATIVO, INATIVO) -> Começa ativo como padrao
    private String cpf;//campo unico
    private String possuiNecessidadeEsp;
    private DatabaseReference firebaseReferences;
    private FirebaseAuth autenticacao;

    public Usuario() {
        firebaseReferences = configuracaoFirebase.getFirebase();
        autenticacao = configuracaoFirebase.getFirebaseAutenticacao();
    }

    public Usuario(String nome, String uid, String telefone, String email,
                   String tipo, String senha, String status, String cpf, String possuiNecessidadeEsp) {
        this.nome = nome;
        this.uid = uid;
        this.telefone = telefone;
        this.email = email;
        this.tipo = tipo;
        this.senha = senha;
        this.status = status;
        this.cpf = cpf;
        this.possuiNecessidadeEsp = possuiNecessidadeEsp;
    }



    public void Create() {






        firebaseReferences.child("users").child(getUid()).setValue(this);
    }

    @Exclude
    public void desloga(){
        autenticacao.signOut();
    }

    public String getNome() {
        return nome;
    }

    public String getStatus() {
        return status;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPossuiNecessidadeEsp() {
        return possuiNecessidadeEsp;
    }

    public void setPossuiNecessidadeEsp(String possuiNecessidadeEsp) {
        this.possuiNecessidadeEsp = possuiNecessidadeEsp;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTelefone() {
        return telefone;
    }

    @Exclude
    public String getCurrentUid(){
        return getAutenticacao().getCurrentUser().getUid();
    }

    @Exclude
    public  String getEmailCurrentUser(){
        return getAutenticacao().getCurrentUser().getEmail();
    }

    @Exclude
    public FirebaseAuth getAutenticacao() {
        return autenticacao;
    }

    @Exclude
    public DatabaseReference getFirebaseReferences() {
        return firebaseReferences;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;

    }

    public String status() {
        return status;
    }


    public String getCpf() {
        return cpf;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



}

