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
import android.widget.Button;
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
    private String setorSeek="Setor 1";
    private com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.ProgressDialog progressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_vaga_admin);

        //preparando componentes
        progressDialog = new com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.ProgressDialog();
        necessidadeEspecial = (TextView)findViewById(R.id.valorNecessidadeEspecial);
        setorText = (TextView)findViewById(R.id.valorSetorId);
        numeroVagaText = (TextView)findViewById(R.id.valorNumeroId);
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        seekBar = (SeekBar)findViewById(R.id.seekBarSetor);
        btnBuscarVaga = (ImageView)findViewById(R.id.btnbuscarVaga);
        editconsultaVaga = (EditText)findViewById(R.id.editConsultaVaga);
        vaga = new modelVaga();

        //Preparando Toolbar
        toolbar.setTitle("Consulta vaga");
        setSupportActionBar(toolbar);

        /*
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0){
                    setorSeek = "Setor 1";
                }else {
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

*/

        btnBuscarVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editconsultaVaga.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Campo de busca vazio!",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.invocaDialog(getApplicationContext(),"Consultando vagas...");
                    vaga = consultaUnicaVaga(editconsultaVaga.getText().toString(),setorSeek);
                    if(vaga != null){

                        if(vaga.getVagaEspecial() == true) {
                            necessidadeEspecial.setText("Sim");
                        }else{
                            necessidadeEspecial.setText("Não");
                        }

                        setorText.setText(vaga.getSetor());
                        numeroVagaText.setText(vaga.getNumero());
                        progressDialog.disableDialog();
                        return;
                    }else{
                        progressDialog.disableDialog();
                        Toast.makeText(getApplicationContext(),"Não foi encontrada nenhuma vaga",Toast.LENGTH_SHORT).show();
                        return;
                    }
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

    public modelVaga consultaUnicaVaga(final String numero, final String setor){

        DatabaseReference database = configuracaoFirebase.getFirebase();
        database.child("vagas").addListenerForSingleValueEvent(new ValueEventListener() {
            String setorSnapshot, numeroSnapshot;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setorSnapshot = dataSnapshot.getValue(modelVaga.class).getSetor();
                numeroSnapshot = dataSnapshot.getValue(modelVaga.class).getNumero();

                if(setorSnapshot.equals(setor) && numeroSnapshot.equals(numero)){
                    vaga = dataSnapshot.getValue(modelVaga.class);
                }else {
                    Toast.makeText(getApplicationContext(),"Vaga não encontrada",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return vaga;

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
