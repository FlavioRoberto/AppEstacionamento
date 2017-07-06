package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.content.Context;

/**
 * Created by Admin on 06/07/2017.
 */

public class ProgressDialog {

    private android.app.ProgressDialog progressDialog;

    public ProgressDialog() {
    }


    public void invocaDialog(Context context, String mensagem){
        progressDialog = new android.app.ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(mensagem);
        progressDialog.setTitle("Aguarde");
        progressDialog.show();
    }

    public void disableDialog(){
        progressDialog.dismiss();
    }

}
