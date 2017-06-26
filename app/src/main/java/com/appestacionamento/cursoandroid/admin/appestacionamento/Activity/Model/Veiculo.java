package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

/**
 * Created by admin on 20/06/2017.
 */

public class Veiculo {

    private String placa, marca, modelo, cor, tipo;
    private Usuario usuario;
    private int idVeiculo;
    private static DatabaseReference firebaseReferencia;

    public Veiculo(){
        firebaseReferencia = configuracaoFirebase.getFirebase();
    }


    public void create(){
        firebaseReferencia.child("Veiculo").child(usuario.getUid()).setValue(this);
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Exclude
    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idUser) {
        this.idVeiculo = idUser;
    }
}
