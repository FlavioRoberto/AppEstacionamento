package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model;

import java.util.Date;

/**
 * Created by Admin on 08/07/2017.
 */

public class modelVagaVeiculo {

    private Date data;
    private modelVeiculo uidVeiculo;
    private modelVaga uidVaga;
    private String tipo; //entrada ou saida

    public modelVagaVeiculo() {
    }


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public modelVeiculo getUidVeiculo() {
        return uidVeiculo;
    }

    public void setUidVeiculo(modelVeiculo uidVeiculo) {
        this.uidVeiculo = uidVeiculo;
    }

    public modelVaga getUidVaga() {
        return uidVaga;
    }

    public void setUidVaga(modelVaga uidVaga) {
        this.uidVaga = uidVaga;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
