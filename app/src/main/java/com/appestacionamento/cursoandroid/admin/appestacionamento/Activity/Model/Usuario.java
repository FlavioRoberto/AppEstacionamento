package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.LoginActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;


public class Usuario {
    private String nome;
    private String uid,codificaEmail;//chave do usuario
    private String telefone;
    private String email,emailDatabase;
    private String tipo;//(USER, ADM, GARAGISTA, SECRETARIA)
    private String senha ;//Possui valor padrao (200200)
    private String status;//(ATIVO, INATIVO) -> Começa ativo como padrao
    private String cpf;//campo unico
  //  private String possuiNecessidadeEsp;
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
      //  this.possuiNecessidadeEsp = possuiNecessidadeEsp;
    }

    public boolean excluirUsuario(FirebaseUser user){

        user = configuracaoFirebase.getFirebaseAutenticacao().getCurrentUser();
        return user.delete().isSuccessful();
    }

    public boolean redefinirSenha(String email){

       return autenticacao.sendPasswordResetEmail(email).isComplete();

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

