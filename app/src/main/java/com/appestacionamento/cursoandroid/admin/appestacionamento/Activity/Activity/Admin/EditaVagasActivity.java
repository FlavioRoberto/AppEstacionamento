package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.ConsultaUsuarioActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.PreferenciasVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditaVagasActivity extends AppCompatActivity  implements IActivity{

    private String novoNumero,novoSetor,status,chave;
    private boolean novoNecessidadeEspecial;
    private Toolbar toolbar;
    private EditText editNumVaga;
    private Spinner spinnerTipoSetorVaga;
    private CheckBox checkBoxNecessidade;
    private Button buttonSalvar;
    private modelVaga vaga;
    private  PreferenciasVaga vagaPreferencia ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_vagas);

        //inicialização das variáveis
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        editNumVaga = (EditText)findViewById(R.id.EditNumero_da_vaga);
        spinnerTipoSetorVaga = (Spinner)findViewById(R.id.EditarEditTipo_setor);
        checkBoxNecessidade = (CheckBox)findViewById(R.id.EditarVagaEspecialCheckBox);
        buttonSalvar = (Button)findViewById(R.id.EditarbuttonAtualizarCadastroVaga);
        vaga = new modelVaga();
        vagaPreferencia = new PreferenciasVaga(EditaVagasActivity.this);


        //preparando toolbar
        toolbar.setTitle("Editar vagas");
        setSupportActionBar(toolbar);

        //configurando o spinner
        spinnerTipoSetorVaga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    novoSetor = "Setor 1";
                }else {
                    novoSetor = "Setor 2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        atualizaView();

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaDados();
                Intent intent = new Intent(EditaVagasActivity.this,ConsultaVagaADMActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public void atualizaDados(){
        novoNumero = editNumVaga.getText().toString().toUpperCase().trim();

        if(checkBoxNecessidade.isChecked()){
            novoNecessidadeEspecial = true;
        }else {
            novoNecessidadeEspecial = false;
        }

        //  if(!novoNome.equals(nome) || !novoTelefone.equals(telefone) || !novoTipo.equals(tipo) || !novoCpf.equals(cpf)){

        if(!novoNumero.equals("")){
            vaga.setNumero(novoNumero);
        }
        if(!novoSetor.equals("")){
            vaga.setSetor(novoSetor);
        }
       
            vaga.setVagaEspecial(novoNecessidadeEspecial);


        String chave  =vagaPreferencia.recuperaChave(EditaVagasActivity.this) ;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("vaga").child(chave);
        databaseReference.setValue(vaga).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Atualização realizada!", Toast.LENGTH_LONG).show();
                }else{
                    try{
                        throw task.getException();
                    }catch (FirebaseNetworkException e){
                        Toast.makeText(getApplicationContext(), "Sem conexão de dados", Toast.LENGTH_SHORT).show();
                    }catch (FirebaseException e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Não foi possível atualizar ",Toast.LENGTH_LONG);
                    }
                }
            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior:voltar();break;
            case R.id.menu_sair:sair();break;
            case R.id.menu_sobre:sobre();break;
            case R.id.menu_meusdados:invocaActivitys.TelaMeusDados(this);break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void atualizaView(){
         vaga.setChave(vagaPreferencia.recuperaChave(this));
        vaga.setNumero(vagaPreferencia.recuperaNumero(this));
        vaga.setSetor(vagaPreferencia.recuperaSetor(this));
        vaga.setVagaEspecial(vagaPreferencia.recuperaVagaEspecial(this));
        vaga.setChave(vagaPreferencia.recuperaChave(this));
        vaga.setStatus(vagaPreferencia.recuperaStatus(this));

        editNumVaga.setText(vaga.getNumero());
        passarValorProSpinner();
       if(vaga.getVagaEspecial() == true) {
           checkBoxNecessidade.setChecked(true);
       }else {
           checkBoxNecessidade.setChecked(false);
       }

    }

    @Override
    public void adicionaMascara() {

    }

    @Override
    public void sair() {
        sairAplicacao.logout(EditaVagasActivity.this,this);
    }

    @Override
    public void sobre() {
        invocaActivitys.invocaSobre(EditaVagasActivity.this,this);
    }

    @Override
    public void voltar() {
        Preferencias preferencias = new Preferencias(EditaVagasActivity.this);
        invocaActivitys.invocaPrincipal(EditaVagasActivity.this,this,preferencias.recuperaTipo(EditaVagasActivity.this));
    }


    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }


    public void passarValorProSpinner(){
        //Dentro desta variável conteria o valor do banco, abaixo um exemplo
        String compareValue= vaga.getSetor();

//Criando o adapter
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.spinnerSetor, android.R.layout.simple_spinner_item);

//Setando o valor
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoSetorVaga.setAdapter(adapter);

        if (!compareValue.equals(null)) {
            int spinnerPostion = adapter.getPosition(compareValue);
            spinnerTipoSetorVaga.setSelection(spinnerPostion);
            spinnerPostion = 0;
        }
    }
}
