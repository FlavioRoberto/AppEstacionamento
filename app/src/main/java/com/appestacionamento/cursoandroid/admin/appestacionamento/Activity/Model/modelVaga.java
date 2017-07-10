package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;


import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.FragmentsUsuario.VagaUsuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class modelVaga {
    private String numero, setor, chave, status, placaVeiculo, emailDono;

    private Boolean vagaEspecial;

    private String dataOcupacao;

    private static DatabaseReference firebaseReferencia;

    public modelVaga(){
        firebaseReferencia = configuracaoFirebase.getFirebase();
    }


    @Exclude
    public void create(){
        firebaseReferencia.child("vaga").child(chave).setValue(this);
    }

    public String getNumero() {
        return numero;
    }

    public String getSetor() {
        return setor;
    }

    public String getChave() {
        return chave;
    }

    public Boolean getVagaEspecial() {
        return vagaEspecial;
    }

    public String getStatus() {//Livre ou OCUPADO
        return status;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public String getEmailDono() {
        return emailDono;
    }

    public String getDataOcupacao() {
        return dataOcupacao;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public void setVagaEspecial(Boolean vagaEspecial) {
        this.vagaEspecial = vagaEspecial;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public void setEmailDono(String emailDono) {
        this.emailDono = emailDono;
    }

    public void setDataOcupacao(String dataOcupacao) {
        this.dataOcupacao = dataOcupacao;
    }
}
