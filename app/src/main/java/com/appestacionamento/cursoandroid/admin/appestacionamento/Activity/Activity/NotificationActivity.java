package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.MyFirebaseMessagingService;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class NotificationActivity extends AppCompatActivity{

    private TextView textViewTitulo, textViewCorpoMensagem;
    private String corpoMensagem, titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getExtra();

        textViewTitulo = (TextView) findViewById(R.id.titulo_notificationid);
        textViewCorpoMensagem = (TextView) findViewById(R.id.corpo_notificationId);

        mostraMensagem();
    }

    private void mostraMensagem(){
        textViewTitulo.setText(titulo);
        textViewCorpoMensagem.setText(corpoMensagem);
    }

    public void getExtra(){
        Intent intent = getIntent();
        titulo = intent.getStringExtra(MyFirebaseMessagingService.TITULO);
        corpoMensagem = intent.getStringExtra(MyFirebaseMessagingService.CORPOMENSAGEM);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
