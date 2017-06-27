package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Veiculo;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CadastroVeicuoActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;
    private String uid, tipo, itemSelect, placa, emailDono, marca, modelo, cor, descodificaEmail;
    private EditText editTextPlaca, editTextEmailDono, editTextMarca, editTextModelo, editTextCor;
    private Spinner spinner;
    private Button buttonCadastrarVeiculo;
    private Veiculo veiculo = new Veiculo();
    private DatabaseReference databaseReference;

    private String emailCodificado, emailBusca;
    private Boolean emailaValido = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veicuo);

        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Cadastro de veículo");
        setSupportActionBar(toolbar);

        editTextPlaca = (EditText) findViewById(R.id.placaVeiculoId);
        editTextEmailDono = (EditText) findViewById(R.id.emailDonoId);
        editTextMarca = (EditText) findViewById(R.id.marcaVeiculoId);
        editTextModelo = (EditText) findViewById(R.id.modeloVeiculoId);
        editTextCor = (EditText) findViewById(R.id.corVeiculoId);
        spinner = (Spinner)findViewById(R.id.spinnerTipoVeiculo);
        buttonCadastrarVeiculo = (Button) findViewById(R.id.button_cadastroVeiculo);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        //Spiner Adapter
        SpinnerAdapter adapter = spinner.getAdapter();
        //inicializa o spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemSelect = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonCadastrarVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirVeiculo();
            }
        });

    }


    public void inserirVeiculo(){
        emailDono = editTextEmailDono.getText().toString().toLowerCase().trim();
        //emailCodificado = Base64Custom.codificarBase64(emailBusca);

        placa = editTextPlaca.getText().toString().toUpperCase().trim();
        marca = editTextMarca.getText().toString().toUpperCase().trim();
        modelo = editTextModelo.getText().toString().toUpperCase().trim();
        cor = editTextCor.getText().toString().toUpperCase().trim();



                uid = Base64Custom.codificarBase64(emailDono);
                veiculo.setUid(uid);
                veiculo.setCor(cor);
                veiculo.setMarca(marca);
                veiculo.setModelo(modelo);
                veiculo.setPlaca(placa);
                veiculo.setTipo(tipo);
                veiculo.create();
                Toast.makeText(getApplicationContext(), "Inserido", Toast.LENGTH_LONG).show();



    }

    /*public void verificaEmail(){
        emailBusca = editTextEmailDono.getText().toString().toLowerCase().trim();
        emailCodificado = Base64Custom.codificarBase64(emailBusca);
        //Toast.makeText(getApplicationContext(), emailBusca , Toast.LENGTH_LONG).show();

        Query usersQuery = databaseReference;
        usersQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String emailDatabase = postSnapshot.child("uid").getValue(String.class);
                    try{
                        if(emailDatabase.equals(emailCodificado)){
                            emailDono = postSnapshot.child("email").getValue(String.class);
                            emailaValido = true;
                            break;
                        }
                    }catch(Exception e){

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/


    //sobrescreve metodo da interface IActivity para ativar os icones no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior: voltar();break;
            case R.id.menu_meusdados: break;
            case R.id.menu_sair: sair();break;
            default:break;
        }
        return true;
    }


    //invoca os itens no menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }



    //desloga usuario e vai pra tela de login
    public void sair(){
        Usuario usuario = new Usuario();
        usuario.desloga();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //retorna para a página inicial
    public  void voltar(){
        Intent intent = new Intent(getApplicationContext(),AdmActivity.class);
        startActivity(intent);
        finish();
    }
}
