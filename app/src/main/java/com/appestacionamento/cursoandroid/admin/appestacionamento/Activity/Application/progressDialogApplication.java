package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Admin on 06/07/2017.
 */

public class progressDialogApplication {

    private Context context;
    private ProgressDialog progressDialog;

    public progressDialogApplication(Context contextActivty) {
        context = contextActivty;
    }


    public  void  invocaDialog(String mensagem){
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
