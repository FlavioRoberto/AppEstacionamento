package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

/**
 * Created by admin on 15/06/2017.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Flavio on 08/06/2017.
 */

public class Permissoes {

    public static boolean permissao(int requestCode, Activity activity, String[] permissoes){

        //verifica se a versao do android  e maior ou igual a 23
        if(Build.VERSION.SDK_INT >= 23){

            //lista com as permissoes a serem dadas
            List<String> permissoesAvalidar = new ArrayList<String>();

            //percorre as permissoes solicitadas pra verificar se ja foi dada
            for(String permissao : permissoes){

                boolean temPermissao = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_GRANTED;
                if(!temPermissao) permissoesAvalidar.add(permissao);
            }

            if(!permissoesAvalidar.isEmpty()){

                String validaPermissao[] = new String[permissoesAvalidar.size()];
                permissoesAvalidar.toArray(validaPermissao);

                //solicita as permissoes necessarias
                activity.requestPermissions(validaPermissao,requestCode);
                return  true;
            }


        }

        return true;

    }




}