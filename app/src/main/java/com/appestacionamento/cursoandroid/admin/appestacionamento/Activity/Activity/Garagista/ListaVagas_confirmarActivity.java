package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.vagaAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.PreferenciasOcupaVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.appestacionamento.cursoandroid.admin.appestacionamento.R.string.vaga;

public class ListaVagas_confirmarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listaVagas;;
    private ArrayList<modelVaga> vagas;
    private DatabaseReference databaseReferenceVaga = FirebaseDatabase.getInstance().getReference("vaga");
    private ArrayAdapter<modelVaga> adapter;
    private ValueEventListener valueEventListenerVagas;
    private Query query = databaseReferenceVaga;
    private String vagaBanco;
    private modelVaga vaga;
    private   boolean flag;
    private PreferenciasOcupaVaga preferenciaOcupaVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vagas_confirmar);

        //inicializacao de variavei
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        listaVagas = (ListView)findViewById(R.id.ListaVagas);
        preferenciaOcupaVaga = new PreferenciasOcupaVaga(getApplicationContext());
        vagas = new ArrayList<>();

        pesquisaVaga();
        adapter = new vagaAdapter(ListaVagas_confirmarActivity.this,vagas);
        listaVagas.setAdapter(adapter);

        listaVagas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vaga = vagas.get(position);
                preferenciaOcupaVaga.salvarOcupaVagaPreferences(vaga.getNumero(),vaga.getSetor(),
                        vaga.getChave(),vaga.getStatus(),vaga.getPlacaVeiculo(),vaga.getEmailDono(),vaga.getVagaEspecial());
                Intent intent = new Intent(ListaVagas_confirmarActivity.this,listaVagaOcupacaoActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    public void pesquisaVaga(){
        valueEventListenerVagas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                vagas.clear();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    vagaBanco = postSnapshot.child("status").getValue(String.class);

                    if (vagaBanco.equals("OCUPANDO")) {
                        flag = true;
                        vaga = postSnapshot.getValue(modelVaga.class);
                        vagas.add(vaga);

                    }
                }
                adapter.notifyDataSetChanged();

                if (flag == false) {
                    Toast.makeText(getApplicationContext(), "Nenhuma vaga Encontrada!", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        query.addValueEventListener(valueEventListenerVagas);
    }

    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener(valueEventListenerVagas);
    }


}
