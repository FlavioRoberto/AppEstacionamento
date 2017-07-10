package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by renna on 09/07/2017.
 */

public class BuscarVagaActivity extends AppCompatActivity implements IActivity {

    private ListView ListadeVagas;
    private SeekBar setores;
    private Button botaoBuscarSetor;
    private TextView setor;
    private String vagaBanco;
    private boolean flag;
    private Toolbar toolbar;
    private DatabaseReference databaseReferenceVaga = FirebaseDatabase.getInstance().getReference("vaga");
    private ArrayList Vagas = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscarvaga);

        toolbar = (Toolbar) findViewById(R.id.toolbarId);
        toolbar.setTitle("Buscar Vaga");
        setSupportActionBar(toolbar);

        ListadeVagas = (ListView) findViewById(R.id.ListviewId);
        setores = (SeekBar) findViewById(R.id.seekBarSetores);
        botaoBuscarSetor = (Button) findViewById(R.id.BuscarSetor);
        setor = (TextView) findViewById(R.id.Setor);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                Vagas
        );
        ListadeVagas.setAdapter(adapter);
        setores.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    setor.setText("Setor 1");
                } else
                    setor.setText("Setor 2");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        pesquisaVagas();
    }

    public void pesquisaVagas() {
        Query query = databaseReferenceVaga;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    vagaBanco = postSnapshot.child("setor").getValue(String.class);
                    if (vagaBanco.equals("Setor 1")) {
                        flag = true;
                        Vagas.add(postSnapshot.child("numero").getValue(String.class));
                        setor.setText(postSnapshot.child("numero").getValue(String.class));
                        break;
                    }
                }
                if (flag == false) {
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
    public void adicionaMascara() {

    }

    @Override
    public void sair() {

    }

    @Override
    public void sobre() {

    }

    @Override
    public void voltar() {

    }
}
