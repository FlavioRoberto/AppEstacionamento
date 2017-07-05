package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;


import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class modelVaga {
    private String numero, setor, chave;
    private Boolean vagaEspecial;
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

    @Exclude
    public Boolean getVagaEspecial() {
        return vagaEspecial;
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
}
