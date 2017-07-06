package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.AdmActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.ConsultaVagaADMActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Secretaria.SecretariaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ConsultarVeiculoActivity extends AppCompatActivity implements IActivity {

    //Declaração de variáveis
    private Toolbar toolbar;
    private Preferencias preferencias;
    private EditText editTextEmailDonoVeiculo;
    private ImageView imageViewBuscarVeiculo;
    private TextView textViewPlaca, textViewModeloVeiculo, textViewMarcaVeiculo, textViewCorVeiculo;
    private Button buttonEditar, buttonExcluir;
    private DatabaseReference databaseReferenceVeiculo;
    private String emailDatabase, codificaEmail;
    private Boolean flag = false, emailEncontrado = false;
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;
    private String cor, email, marca, modelo, placa, tipo, uid;
    public static final String EDITCOR = "cor", EDITEMAIL = "email", EDITMODELO = "modelo", EDITPLACA = "placa",
                        EDITTIPO = "tipo", EDITUID = "uid", EDITMARCA = "marca";

    //FIM declaração de variáveis

    //INiCIO METODO 'principal'
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarveiculo);

        preferencias = new Preferencias(getApplicationContext());

        //Iniciando Progress Dialog
        progressDialog = new ProgressDialog(this);

        //Instacia do Banco de Dados
        databaseReferenceVeiculo = FirebaseDatabase.getInstance().getReference("veiculo");

        //Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Consultar veículo");
        setSupportActionBar(toolbar);


        //Inicializando componentes da Activity
        imageViewBuscarVeiculo = (ImageView) findViewById(R.id.btnbuscar);
        textViewPlaca = (TextView) findViewById(R.id.valorplacaId);
        editTextEmailDonoVeiculo = (EditText) findViewById(R.id.editConsultaId);
        textViewModeloVeiculo = (TextView) findViewById(R.id.valormodeloid);
        textViewMarcaVeiculo = (TextView) findViewById(R.id.valormarcaid);
        textViewCorVeiculo = (TextView) findViewById(R.id.valorcorid);
        buttonEditar = (Button) findViewById(R.id.btnEditar);
        buttonExcluir = (Button) findViewById(R.id.btnExcluir);

        builder = new AlertDialog.Builder(this);

        //Botao para Buscar veiculo
        imageViewBuscarVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Pesquisando Veículo...");
                progressDialog.show();
                buscaVeiculo();
            }
        });

        //Botao Editar (Ativa quando a busca é realizada)
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true){

                    putEditaVeiculo();

                }else if(flag == false){
                    Toast.makeText(getApplicationContext(), "Nenhum veículo selecionado", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        //Botao Excluir (Ativa quando a busca é realizada)
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true){
                    builder.setTitle("Excluir Veículo");
                    builder.setMessage("Tem certeza que deseja excluir o Veículo?");
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            databaseReferenceVeiculo = FirebaseDatabase.getInstance().getReference("veiculo").child(uid);
                            databaseReferenceVeiculo.removeValue();
                            Toast.makeText(getApplicationContext(), "Veículo Excluído!", Toast.LENGTH_SHORT).show();
                            finish();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else if(flag == false){
                    Toast.makeText(getApplicationContext(), "Nenhum Veículo pesquisado", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    //FIM metodo 'principal'

    //Metodo para Buscar veiculo
    public void buscaVeiculo(){

        String emailVeiculo = editTextEmailDonoVeiculo.getText().toString().toLowerCase().trim();
        codificaEmail = Base64Custom.codificarBase64(emailVeiculo);
        Query query = databaseReferenceVeiculo;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    emailDatabase = postSnapshot.child("uid").getValue(String.class);
                    try{
                        if(emailDatabase.equals(codificaEmail)){
                            cor = postSnapshot.child("cor").getValue(String.class);
                            email = postSnapshot.child("email").getValue(String.class);
                            marca = postSnapshot.child("marca").getValue(String.class);
                            modelo = postSnapshot.child("modelo").getValue(String.class);
                            placa = postSnapshot.child("placa").getValue(String.class);
                            tipo = postSnapshot.child("tipo").getValue(String.class);
                            uid = postSnapshot.child("uid").getValue(String.class);


                            textViewPlaca.setText(placa);
                            textViewModeloVeiculo.setText(modelo);
                            textViewMarcaVeiculo.setText(marca);
                            textViewCorVeiculo.setText(cor);
                            emailEncontrado = true;
                            flag = true;
                            progressDialog.dismiss();
                            break;
                        }
                    }catch (Exception e){
                        progressDialog.dismiss();
                    }
                }
                if(emailEncontrado == false){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Email nao encontrado", Toast.LENGTH_LONG).show();
                    //finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    //Fim do metodo buscar veiculo

    //Método Edita veiculo
    public void putEditaVeiculo(){
        Intent intent = new Intent(getApplicationContext(), EditaDadosVeiculo.class);
        intent.putExtra(EDITCOR, cor);
        intent.putExtra(EDITEMAIL, email);
        intent.putExtra(EDITMODELO, modelo);
        intent.putExtra(EDITPLACA, placa);
        intent.putExtra(EDITTIPO, tipo);
        intent.putExtra(EDITUID, uid);
        intent.putExtra(EDITMARCA, marca);
        startActivity(intent);
        finish();
    }



    //ao clicar nas opções do menu seleciona uma ação
    //invoca os itens no menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //opcoes do item do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior: voltar();break;
            case R.id.menu_meusdados: break;
            case R.id.menu_sair: sair();break;
            case R.id.menu_sobre:sobre();break;
            default:break;
        }
        return true;
    }

    //desloga usuario e vai pra tela de login
    public void sair(){
        sairAplicacao.logout(getApplicationContext(),this);
    }

    @Override
    public void sobre() {
        invocaActivitys.invocaSobre(ConsultarVeiculoActivity.this,this);
    }

    //metodo para voltar a menu principal
    @Override
    public void voltar() {
        Preferencias preferencias = new Preferencias(ConsultarVeiculoActivity.this);
        invocaActivitys.invocaPrincipal(ConsultarVeiculoActivity.this,this,preferencias.recuperaTipo(this));    }

    @Override
    public void adicionaMascara() {

    }
}
