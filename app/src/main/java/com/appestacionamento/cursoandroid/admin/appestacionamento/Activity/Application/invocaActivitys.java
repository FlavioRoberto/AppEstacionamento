package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.SobreActivity;

/**
 * Created by Admin on 05/07/2017.
 */

public class invocaActivitys {

    public invocaActivitys() {
    }

    public static void invocaSobre(Context context, Activity activity) {
        Intent intent = new Intent(context, SobreActivity.class);
        context.startActivity(intent);
        activity.finish();
    }
}
