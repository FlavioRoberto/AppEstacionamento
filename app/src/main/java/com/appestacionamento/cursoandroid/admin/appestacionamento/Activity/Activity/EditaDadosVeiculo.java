package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVeiculo;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditaDadosVeiculo extends AppCompatActivity implements IActivity{

    private EditText editTextPlacaVeiculo, editTextModeloVeiculo;
    private Spinner spinnerMarcaVeiculo, spinnerCorVeiculo, spinnerTipoVeiculo;
    private String cor, email, marca, modelo, placa, tipo, uid;
    private String novaPlaca, novaMarca, novoModelo, novaCor, novoTipo;
    private Button buttonEditarVeiculo;
    private modelVeiculo veiculo = new modelVeiculo();
    private DatabaseReference databaseReference;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_veiculo);


        //toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Editar Veículo");
        setSupportActionBar(toolbar);


        //metodo para pegar valor da activity consulta veiculos
        getStringExtra();

        editTextPlacaVeiculo = (EditText) findViewById(R.id.EditrplacaVeiculoId);
        editTextModeloVeiculo = (EditText) findViewById(R.id.EditarModeloVeiculoId);
        spinnerMarcaVeiculo = (Spinner) findViewById(R.id.EditarSpinnerMarcaVeiculo);
        spinnerCorVeiculo = (Spinner) findViewById(R.id.EditarSpinnerCorVeiculoId);
        spinnerTipoVeiculo = (Spinner) findViewById(R.id.EditarSspinnerTipoVeiculo);
        buttonEditarVeiculo = (Button) findViewById(R.id.button_editarVeiculo);



        //Spinner Marca do veiculo
        //Spiner Adapter
        SpinnerAdapter adapter = spinnerMarcaVeiculo.getAdapter();
        //inicializa o spinner
        spinnerMarcaVeiculo.setAdapter(adapter);
        spinnerMarcaVeiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marca = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner Cor do veiculo
        adapter = spinnerCorVeiculo.getAdapter();
        spinnerCorVeiculo.setAdapter(adapter);
        spinnerCorVeiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cor = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spiner Adapter
        adapter = spinnerTipoVeiculo.getAdapter();
        //inicializa o spinner
        spinnerTipoVeiculo.setAdapter(adapter);
        spinnerTipoVeiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonEditarVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarVeiculo();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior:voltar();break;
            case R.id.menu_meusdados: break;
            case  R.id.menu_sair: sair();break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void editarVeiculo(){
        novaPlaca = editTextPlacaVeiculo.getText().toString().toUpperCase().trim();
        novaMarca = marca;
        novoModelo = editTextModeloVeiculo.getText().toString().toUpperCase().trim();
        novaCor = cor;
        novoTipo = tipo;

        if(!TextUtils.isEmpty(novaPlaca)){
            veiculo.setPlaca(novaPlaca);
        }
        if(!TextUtils.isEmpty(novoModelo)){
            veiculo.setModelo(novoModelo);
        }
        veiculo.setMarca(marca);
        veiculo.setCor(cor);
        veiculo.setTipo(tipo);
        veiculo.setUid(uid);
        veiculo.setEmail(email);

        databaseReference = FirebaseDatabase.getInstance().getReference("veiculo").child(uid);
        databaseReference.setValue(veiculo);
        Toast.makeText(getApplicationContext(), "Atualização realizada!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), AdmActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void adicionaMascara() {

    }

    @Override
    public void sair() {
        FirebaseAuth auth = configuracaoFirebase.getFirebaseAutenticacao();
        auth.signOut();
        finish();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void voltar() {
        Intent intent = new Intent(getApplicationContext(),ConsultarVeiculoActivity.class);
        startActivity(intent);
        finish();
    }

    public void getStringExtra(){
        Intent intent = getIntent();
        cor = intent.getStringExtra(ConsultarVeiculoActivity.EDITCOR);
        email = intent.getStringExtra(ConsultarVeiculoActivity.EDITEMAIL);
        marca = intent.getStringExtra(ConsultarVeiculoActivity.EDITMARCA);
        modelo = intent.getStringExtra(ConsultarVeiculoActivity.EDITMODELO);
        placa = intent.getStringExtra(ConsultarVeiculoActivity.EDITPLACA);
        tipo = intent.getStringExtra(ConsultarVeiculoActivity.EDITTIPO);
        uid = intent.getStringExtra(ConsultarVeiculoActivity.EDITUID);

    }
}
