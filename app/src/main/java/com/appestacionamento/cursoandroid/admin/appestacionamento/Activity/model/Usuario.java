package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;


import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;


public class Usuario {
    private String nome, uid, telefone, email, tipo;
    private  String senha ;
    private boolean status;
    private int cpf;
    private DatabaseReference firebaseReferences;


    public Usuario() {
        firebaseReferences = configuracaoFirebase.getFirebase();
    }

    public void Create() {
        firebaseReferences.child("Usuarios").child(getUid()).setValue(this);
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


    public void setUid(String uid) {
        this.uid = uid;
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

}

