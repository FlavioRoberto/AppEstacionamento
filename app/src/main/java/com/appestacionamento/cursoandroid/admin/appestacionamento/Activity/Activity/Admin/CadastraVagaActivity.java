package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.*;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class CadastraVagaActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;
    private EditText editTextInsereNumeroVaga;
    private Spinner spinnerSetor;
    private Button buttonCadastraVaga;
    private CheckBox checkBoxVagaEspecial;
    private String setor, numero;
    private Boolean vagaEspecial;
    private CadastraVaga cadastraVaga = new CadastraVaga(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vaga);

        toolbar = (Toolbar) findViewById(R.id.toolbarId);
        toolbar.setTitle("Cadastrar vagas");
        setSupportActionBar(toolbar);

        //Iniciando componentes da Activity
        editTextInsereNumeroVaga = (EditText) findViewById(R.id.numero_da_vaga);
        spinnerSetor = (Spinner) findViewById(R.id.editTipo_setor);
        buttonCadastraVaga = (Button) findViewById(R.id.buttonAtualizarCadastroVaga);
        checkBoxVagaEspecial = (CheckBox) findViewById(R.id.VagaEspecialCheckBox);

        //Iniciando Spinner
        SpinnerAdapter adapter = spinnerSetor.getAdapter();
        spinnerSetor.setAdapter(adapter);
        spinnerSetor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setor = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        buttonCadastraVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaVaga();
            }
        });


    }

    public void adicionaVaga(){
        numero = editTextInsereNumeroVaga.getText().toString().trim();
        if(checkBoxVagaEspecial.isChecked()){
            vagaEspecial = true;
        }else if(!checkBoxVagaEspecial.isChecked()){
            vagaEspecial = false;
        }
        if(!TextUtils.isEmpty(numero) && !TextUtils.isEmpty(setor)){
            cadastraVaga.insereVaga(numero, setor, vagaEspecial);
            editTextInsereNumeroVaga.setText(null);
        }
        else{
            Toast.makeText(getApplicationContext(), "Há Campos Vazios!", Toast.LENGTH_LONG).show();
            return;
        }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
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
        Preferencias preferencias = new Preferencias(CadastraVagaActivity.this);
        invocaActivitys.invocaPrincipal(this,this,preferencias.recuperaTipo(this));
    }

    @Override
    public void adicionaMascara() {

    }
}
