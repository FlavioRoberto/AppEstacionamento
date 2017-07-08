package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConsultaVagaADMActivity extends AppCompatActivity implements IActivity {

    private  modelVaga vaga;
    private Toolbar toolbar;
    private SeekBar seekBar;
    private ImageView btnBuscarVaga;
    private EditText editconsultaVaga;
    private TextView necessidadeEspecial,setorText,numeroVagaText;
    private String setorSeek, setorVaga, numeroVaga;
    private ProgressDialog progressDialog;
    private Boolean flag = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_vaga_admin);

        //preparando componentes
        progressDialog = new ProgressDialog(this);
        necessidadeEspecial = (TextView)findViewById(R.id.valorNecessidadeEspecial);
        setorText = (TextView)findViewById(R.id.valorSetorId);
        numeroVagaText = (TextView)findViewById(R.id.valorNumeroId);
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        seekBar = (SeekBar)findViewById(R.id.seekBarSetor);
        btnBuscarVaga = (ImageView)findViewById(R.id.btnbuscarVaga);
        editconsultaVaga = (EditText)findViewById(R.id.editConsultaVaga);
        vaga = new modelVaga();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0){
                    setorSeek = "Setor 1";
                }else{
                    setorSeek = "Setor 2";
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Preparando Toolbar
        toolbar.setTitle("Consulta vaga");
        setSupportActionBar(toolbar);

        btnBuscarVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editconsultaVaga.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Campo de busca vazio!",Toast.LENGTH_SHORT).show();
                }else {

                    consultaUnicaVaga(editconsultaVaga.getText().toString().trim(),setorSeek);
                }
            }
        });


    }


    //metodos da toolbar

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
            case R.id.menu_sair: sair();break;
            case R.id.menu_sobre: sobre();break;
        }
        return super.onOptionsItemSelected(item);
    }

    //fim metodos da toolbar

    public void consultaUnicaVaga(String numero, String setor){

        setorVaga = setor;
        numeroVaga = numero;

        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference("vaga");
        database.addValueEventListener(new ValueEventListener() {
            String setorSnapshot, numeroSnapshot;
            Boolean vagaEspcialSnapshot;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    setorSnapshot = postSnapshot.child("setor").getValue(String.class);
                    numeroSnapshot = postSnapshot.child("numero").getValue(String.class);
                    vagaEspcialSnapshot = postSnapshot.child("vagaEspecial").getValue(Boolean.class);

                    if (setorSnapshot.equals(setorVaga) && numeroSnapshot.equals(numeroVaga)) {
                        vaga.setSetor(setorVaga);
                        vaga.setNumero(numeroVaga);
                        vaga.setVagaEspecial(vagaEspcialSnapshot);
                        flag = true;
                        break;
                    }
                }
                if(flag == true){
                    if(vaga.getVagaEspecial() == true) {
                        necessidadeEspecial.setText("Sim");
                    }else{
                        necessidadeEspecial.setText("Não");
                    }
                    setorText.setText(vaga.getSetor());
                    numeroVagaText.setText(vaga.getNumero());

                }
                else if(flag == false){
                    Toast.makeText(getApplicationContext(), "Vaga não encontrada", Toast.LENGTH_SHORT).show();
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
        sairAplicacao.logout(ConsultaVagaADMActivity.this,this);
    }

    @Override
    public void sobre() {
        invocaActivitys.invocaSobre(ConsultaVagaADMActivity.this,this);
    }

    @Override
    public void voltar() {
        Preferencias preferencias = new Preferencias(ConsultaVagaADMActivity.this);
        invocaActivitys.invocaPrincipal(ConsultaVagaADMActivity.this,this,preferencias.recuperaTipo(this));
    }
}
