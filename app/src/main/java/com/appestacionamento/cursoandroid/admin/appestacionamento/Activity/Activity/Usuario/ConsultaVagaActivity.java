package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ConsultaVagaActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;
    private TextView textViewNumeroVaga;
    private DatabaseReference databaseReferenceVaga = FirebaseDatabase.getInstance().getReference("vaga");
    private String vagaDatabase;
    private Boolean flag = false;
    private Button botaoConfirmar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_vaga_bucar);

        textViewNumeroVaga = (TextView) findViewById(R.id.textnumero_vaga);
        botaoConfirmar = (Button) findViewById(R.id.botaoconfirmarid);

        //Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Consultar Vaga");
        setSupportActionBar(toolbar);


        pesquisaVaga();

    }

    public void pesquisaVaga() {
        Query query = databaseReferenceVaga;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    vagaDatabase = postSnapshot.child("status").getValue(String.class);
                    if (vagaDatabase.equals("LIVRE")) {
                        flag = true;
                        textViewNumeroVaga.setText(postSnapshot.child("numero").getValue(String.class));
                        break;
                    }
                }
                if(flag == false){
                    Toast.makeText(getApplicationContext(), "Nenhuma vaga Encontrada!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
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
    public void adicionaMascara() {

    }

    @Override
    public void sair() {
        sairAplicacao.logout(this,this);
    }

    @Override
    public void sobre() {
        invocaActivitys.invocaSobre(this,this);
    }

    @Override
    public void voltar() {
        Preferencias preferencias = new Preferencias(this);
        invocaActivitys.invocaPrincipal(this,this,preferencias.recuperaTipo(this));
    }
}
