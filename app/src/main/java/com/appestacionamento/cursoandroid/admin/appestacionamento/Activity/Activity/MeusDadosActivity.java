package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by renna on 11/07/2017.
 */


public class MeusDadosActivity extends AppCompatActivity implements IActivity {

    private TextView nome,telefone,email,status,cpf,tipo;
    private DatabaseReference databaseReferenceUsers;
    private Preferencias preferencias;
    private String usuarioDatabase, nomes,telefones,emails,statustv,cpftv,tipotv;
    private Toolbar toolbar;
    private Preferencias preferencias2;




    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meusdados);

        toolbar = (Toolbar)findViewById(R.id.toolbarId);

        toolbar.setTitle("Meus Dados");
        setSupportActionBar(toolbar);

        nome = (TextView) findViewById(R.id.tv_nome);
        telefone = (TextView) findViewById(R.id.tv_telefone);
        email = (TextView) findViewById(R.id.tv_email);
        status = (TextView) findViewById(R.id.tv_Status);
        cpf = (TextView) findViewById(R.id.tv_cpf);
        tipo = (TextView) findViewById(R.id.tv_Tipo);
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users");
        pesquisaUsuario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void adicionaMascara() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior:voltar();break;
            case R.id.menu_sair:sair();break;
            case R.id.menu_sobre: sobre();break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sair() {
        sairAplicacao.logout(this,this);
        finish();
    }

    @Override
    public void sobre() {
        invocaActivitys.invocaSobre(this,this);

    }

    @Override
    public void voltar() {
        preferencias2 = new Preferencias(MeusDadosActivity.this);
        invocaActivitys.invocaPrincipal(MeusDadosActivity.this,this,preferencias2.recuperaTipo(MeusDadosActivity.this));

    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();

    }

    public void pesquisaUsuario(){
        Query query = databaseReferenceUsers;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    usuarioDatabase = postSnapshot.child("email").getValue(String.class);
                    try{
                        if(usuarioDatabase.equals(consultaUsuarioLogado())){
                            cpftv = postSnapshot.child("cpf").getValue(String.class);
                            emails = postSnapshot.child("email").getValue(String.class);
                            nomes = postSnapshot.child("nome").getValue(String.class);
                            statustv = postSnapshot.child("status").getValue(String.class);
                            tipotv = postSnapshot.child("tipo").getValue(String.class);
                            telefones = postSnapshot.child("telefone").getValue(String.class);

                            nome.setText(nomes);
                            cpf.setText(cpftv);
                            tipo.setText(tipotv);
                            status.setText(statustv);
                            telefone.setText(telefones);
                            email.setText(emails);

                            break;
                        }
                    }catch (Exception e){

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String consultaUsuarioLogado(){
        preferencias = new Preferencias(getApplicationContext());
        String usuario = preferencias.recuperaEmail(getApplicationContext());
        return usuario;
    }
}
