package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Admin on 06/07/2017.
 */

public class progressDialogApplication {

    private ProgressDialog progressDialog;

    public progressDialogApplication() {
    }


    public  void  invocaDialog(Context context, String mensagem){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(mensagem);
        progressDialog.setTitle("Aguarde");
        progressDialog.show();
    }

    public void disableDialog(){
        progressDialog.dismiss();
    }

}
