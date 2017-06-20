package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;


import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.google.firebase.database.DatabaseReference;
<<<<<<< Updated upstream

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

=======
import com.google.firebase.database.Exclude;

public class Usuario {
    private String nome, uid,senha,telefone,email,tipo;
    private boolean status;
    private int cpf;

    private DatabaseReference firebaseReference;


    public Usuario(){
        firebaseReference = configuracaoFirebase.getFirebase();
    }


    public void Create(){
        firebaseReference.child("Usuarios").child(getUid()).setValue(this);
    }



>>>>>>> Stashed changes
    public String getNome() {
        return nome;
    }

<<<<<<< Updated upstream
=======
    public void setNome(String nome) {
        this.nome = nome;
    }

    //@Exclude nao upa pro banco o campo
    @Exclude
>>>>>>> Stashed changes
    public String getUid() {
        return uid;
    }

<<<<<<< Updated upstream
=======
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

>>>>>>> Stashed changes
    public String getTelefone() {
        return telefone;
    }

<<<<<<< Updated upstream
=======
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Exclude
>>>>>>> Stashed changes
    public String getEmail() {
        return email;
    }

<<<<<<< Updated upstream
    public String getSenha() {
        return senha;
=======
    public void setEmail(String email) {
        this.email = email;
>>>>>>> Stashed changes
    }

    public boolean isStatus() {
        return status;
    }

<<<<<<< Updated upstream
    public int getCpf() {
        return cpf;
    }
=======
    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }
>>>>>>> Stashed changes
}
