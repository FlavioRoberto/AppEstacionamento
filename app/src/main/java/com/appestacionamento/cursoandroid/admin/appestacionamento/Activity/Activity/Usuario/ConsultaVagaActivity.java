package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.PreferenciasOcupaVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVeiculo;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class ConsultaVagaActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;
    private TextView textViewNumeroVaga, textViewSetorVaga;
    private DatabaseReference databaseReferenceVaga = FirebaseDatabase.getInstance().getReference("vaga");
    private DatabaseReference databaseReferenceVeiculo = FirebaseDatabase.getInstance().getReference("veiculo");
    private Button buttonConfirmar;
    private String vagaDatabase;
    private String veiculoDatabase;
    private String vagaTipoDatabase;
    private Boolean flag = false, ativaBotao = false, veiculoEncotrado = false;
    private String chaveVeiculo;
    private String recuperaEmail;
    private modelVaga modelVaga = new modelVaga();
    private modelUsuario modelUsuario = new modelUsuario();
    private modelVeiculo modelVeiculo = new modelVeiculo();
    private PreferenciasOcupaVaga preferenciasOcupaVaga ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_vaga_bucar);

        textViewNumeroVaga = (TextView) findViewById(R.id.textnumero_vaga);
        textViewSetorVaga = (TextView) findViewById(R.id.textnumero_setor_vaga);
        buttonConfirmar = (Button) findViewById(R.id.botaoconfirmarid);

        //Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Consultar Vaga");
        setSupportActionBar(toolbar);


        //pesquisaVaga();

        recuperaChaveVeiculo();


        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ativaBotao == true){
                    //recuperaChaveVeiculo();
                }
            }
        });

    }

    public void ocupaVaga(){
        preferenciasOcupaVaga = new PreferenciasOcupaVaga(ConsultaVagaActivity.this);
        modelVaga.setEmailDono(modelVeiculo.getEmail());
        modelVaga.setPlacaVeiculo(modelVeiculo.getPlaca());
        modelVaga.setStatus("OCUPANDO");
        preferenciasOcupaVaga.salvarOcupaVagaPreferences(modelVaga.getNumero(), modelVaga.getSetor(), modelVaga.getChave(), modelVaga.getStatus(), modelVaga.getPlacaVeiculo(), modelVaga.getEmailDono(), modelVaga.getVagaEspecial());
        databaseReferenceVaga = FirebaseDatabase.getInstance().getReference("vaga").child(modelVaga.getChave());
        databaseReferenceVaga.setValue(modelVaga);
        Toast.makeText(ConsultaVagaActivity.this, "A vaga com os seus dados foram cadastrados com Sucesso!", Toast.LENGTH_LONG).show();

    }

    public void recuperaChaveVeiculo(){
        recuperaEmail = modelUsuario.getEmailCurrentUser();
        chaveVeiculo = Base64Custom.codificarBase64(recuperaEmail);
        verificaVeiculo(chaveVeiculo);
    }

    public void verificaVeiculo(final String chave){
        Query query = databaseReferenceVeiculo;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    veiculoDatabase = postSnapshot.child("uid").getValue(String.class);
                    if(veiculoDatabase.equals(chave)){
                        veiculoEncotrado = true;
                        modelVeiculo.setEmail(recuperaEmail);
                        modelVeiculo.setPlaca(postSnapshot.child("placa").getValue(String.class));
                        modelVeiculo.setTipo(postSnapshot.child("tipo").getValue(String.class));
                        pesquisaVaga();
                        //ocupaVaga();
                        break;
                    }
                }
                if(veiculoEncotrado == false){
                    Toast.makeText(getApplicationContext(), "Nao Possui veiculo", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void pesquisaVaga() {
        Query query = databaseReferenceVaga;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(modelVeiculo.getTipo().equals("Necessidade Especial")){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        vagaDatabase = postSnapshot.child("status").getValue(String.class);
                        vagaTipoDatabase = postSnapshot.child("tipo").getValue(String.class);
                        if (vagaDatabase.equals("LIVRE") && vagaTipoDatabase.equals("Necessidade Especial")) {
                            ativaBotao = true;
                            flag = true;

                            modelVaga.setChave(postSnapshot.child("chave").getValue(String.class));
                            modelVaga.setNumero(postSnapshot.child("numero").getValue(String.class));
                            modelVaga.setSetor(postSnapshot.child("setor").getValue(String.class));
                            modelVaga.setStatus(postSnapshot.child("status").getValue(String.class));
                            modelVaga.setVagaEspecial(postSnapshot.child("vagaEspecial").getValue(Boolean.class));

                            textViewNumeroVaga.setText(postSnapshot.child("numero").getValue(String.class));
                            textViewSetorVaga.setText(postSnapshot.child("setor").getValue(String.class));
                            ocupaVaga();
                            break;
                        }
                    }
                    if(flag == false){
                        Toast.makeText(getApplicationContext(), "Nenhuma vaga Encontrada!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }else{
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        vagaDatabase = postSnapshot.child("status").getValue(String.class);
                        if (vagaDatabase.equals("LIVRE") && postSnapshot.child("vagaEspecial").getValue(boolean.class).equals(false)) {
                            ativaBotao = true;
                            flag = true;

                            modelVaga.setChave(postSnapshot.child("chave").getValue(String.class));
                            modelVaga.setNumero(postSnapshot.child("numero").getValue(String.class));
                            modelVaga.setSetor(postSnapshot.child("setor").getValue(String.class));
                            modelVaga.setStatus(postSnapshot.child("status").getValue(String.class));
                            modelVaga.setVagaEspecial(postSnapshot.child("vagaEspecial").getValue(Boolean.class));
                            textViewNumeroVaga.setText(postSnapshot.child("numero").getValue(String.class));
                            textViewSetorVaga.setText(postSnapshot.child("setor").getValue(String.class));
                            ocupaVaga();
                            break;
                        }
                    }
                    if(flag == false){
                        Toast.makeText(getApplicationContext(), "Nenhuma vaga Encontrada!", Toast.LENGTH_LONG).show();
                        return;
                    }
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