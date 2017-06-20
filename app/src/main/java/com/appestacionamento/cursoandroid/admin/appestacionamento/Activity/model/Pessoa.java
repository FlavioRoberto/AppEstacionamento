package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.model;



public class Pessoa {
    private String nome, uid, telefone, email;
    private final String senha = "200200";
    private boolean status;
    private int cpf;

    public Pessoa(String nome, String uid, String telefone, String email, boolean status, int cpf) {
        this.nome = nome;
        this.uid = uid;
        this.telefone = telefone;
        this.email = email;
        this.status = status;
        this.cpf = cpf;
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
