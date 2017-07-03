package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;


public class modelVeiculo {

    private String placa, marca, modelo, cor, tipo, uid, email;
    //private Usuario usuario;
    //private int idVeiculo;
    private static DatabaseReference firebaseReferencia;

    public modelVeiculo(){
        firebaseReferencia = configuracaoFirebase.getFirebase();
    }

    @Exclude
    public void create(){
        firebaseReferencia.child("veiculo").child(uid).setValue(this);
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Exclude
    public DatabaseReference getFirebaseReferencia() {
        return firebaseReferencia;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //public Usuario getUsuario() {
        //return usuario;
    //}

    //public void setUsuario(Usuario usuario) {
        //this.usuario = usuario;
    //}

    //@Exclude
    //public int getIdVeiculo() {
        //return idVeiculo;
    //}

    //public void setIdVeiculo(int idUser) {
        //this.idVeiculo = idUser;
    //}
}
