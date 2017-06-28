package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ConsultarVeiculoActivity extends AppCompatActivity implements IActivity {

    private Toolbar toolbar;
    private EditText editTextEmailDonoVeiculo;
    private ImageView imageViewBuscarVeiculo;
    private TextView textViewPlaca, textViewModeloVeiculo, textViewMarcaVeiculo, textViewCorVeiculo;
    private Button buttonEditar;
    private DatabaseReference databaseReference;
    private String emailDatabase, codificaEmail;
    private Boolean flag = false, emailEncontrado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarveiculo);

        databaseReference = FirebaseDatabase.getInstance().getReference("veiculo");

        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Excluir veículo");
        setSupportActionBar(toolbar);

        imageViewBuscarVeiculo = (ImageView) findViewById(R.id.btnbuscar);
        textViewPlaca = (TextView) findViewById(R.id.valorplacaId);
        editTextEmailDonoVeiculo = (EditText) findViewById(R.id.editConsultaId);
        textViewModeloVeiculo = (TextView) findViewById(R.id.valormodeloid);
        textViewMarcaVeiculo = (TextView) findViewById(R.id.valormarcaid);
        textViewCorVeiculo = (TextView) findViewById(R.id.valorcorid);
        buttonEditar = (Button) findViewById(R.id.btnEditar);

        //Botao para Buscar veiculo
        imageViewBuscarVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscaVeiculo();
            }
        });

        //Botao Editar (Ativa quando a busca é realizada)
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true){
                    editaVeiculo();

                }else if(flag == false){
                    Toast.makeText(getApplicationContext(), "Nenhum veículo selecionado", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Metodo para Buscar veiculo
    public void buscaVeiculo(){
        String emailVeiculo = editTextEmailDonoVeiculo.getText().toString().toLowerCase().trim();
        codificaEmail = Base64Custom.codificarBase64(emailVeiculo);
        Query query = databaseReference;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    emailDatabase = postSnapshot.child("uid").getValue(String.class);
                    try{
                        if(emailDatabase.equals(codificaEmail)){
                            textViewPlaca.setText(postSnapshot.child("placa").getValue(String.class));
                            textViewModeloVeiculo.setText(postSnapshot.child("modelo").getValue(String.class));
                            textViewMarcaVeiculo.setText(postSnapshot.child("marca").getValue(String.class));
                            textViewCorVeiculo.setText(postSnapshot.child("cor").getValue(String.class));
                            emailEncontrado = true;
                            flag = true;
                            break;
                        }
                    }catch (Exception e){

                    }
                }
                if(emailEncontrado == false){
                    Toast.makeText(getApplicationContext(), "Email nao encontrado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //Fim do metodo buscar veiculo

    //Método Edita veiculo
    public void editaVeiculo(){

    }



    //ao clicar nas opções do menu seleciona uma ação
    //invoca os itens no menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //opcoes do item do menu
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

    //desloga usuario e vai pra tela de login
    public void sair(){
        Usuario usuario = new Usuario();
        usuario.desloga();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //metodo para voltar a menu principal
    @Override
    public void voltar() {
        Intent intent = new Intent(getApplicationContext(),AdmActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void adicionaMascara() {

    }
}
