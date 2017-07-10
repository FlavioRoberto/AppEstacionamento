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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.AdmActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Secretaria.SecretariaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.progressDialogApplication;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.verificaUsuarioLogado;
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
    private Switch tipoPesquisa;
    private Preferencias preferencias;
    private String childDatabse, campoInformado, placaDatabse="default",placaDigitada="",codificaEmail, cor, email, marca, modelo,
            placa, tipo, uid;
    private EditText editTextEmailDonoVeiculo;
    private ImageView imageViewBuscarVeiculo;
    private TextView textViewPlaca, textViewModeloVeiculo, textViewMarcaVeiculo, textViewCorVeiculo;
    private Button buttonEditar, buttonExcluir;
    private DatabaseReference databaseReferenceVeiculo;
    private Boolean flag = false, placaEncontrada =false, pesquisaPlaca =  false;
    private AlertDialog.Builder builder;
    private progressDialogApplication progressDialog;
    private  SimpleMaskFormatter smf;
    private MaskTextWatcher mtw;
    public static final String EDITCOR = "cor", EDITEMAIL = "email", EDITMODELO = "modelo", EDITPLACA = "placa",
                        EDITTIPO = "tipo", EDITUID = "uid", EDITMARCA = "marca";

    //FIM declaração de variáveis

    //INiCIO METODO 'principal'
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarveiculo);


        //Variavel para receber valor do sharedPReferences
        preferencias = new Preferencias(getApplicationContext());

        //Iniciando Progress Dialog
        progressDialog = new progressDialogApplication(ConsultarVeiculoActivity.this);

        //Instacia do Banco de Dados
        databaseReferenceVeiculo = FirebaseDatabase.getInstance().getReference("veiculo");

        //Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Consultar veículo");
        setSupportActionBar(toolbar);


        //Inicializando componentes da Activity
        tipoPesquisa = (Switch)findViewById(R.id.tipoPesquisa);
        imageViewBuscarVeiculo = (ImageView) findViewById(R.id.btnbuscar);
        textViewPlaca = (TextView) findViewById(R.id.valorplacaId);
        editTextEmailDonoVeiculo = (EditText) findViewById(R.id.editConsultaId);
        textViewModeloVeiculo = (TextView) findViewById(R.id.valormodeloid);
        textViewMarcaVeiculo = (TextView) findViewById(R.id.valormarcaid);
        textViewCorVeiculo = (TextView) findViewById(R.id.valorcorid);
        buttonEditar = (Button) findViewById(R.id.btnEditar);
        buttonExcluir = (Button) findViewById(R.id.btnExcluir);


        //verificar usuario logado se nao for adm ou secretaria desabilita botoes de excluir e editar
        if(!(verificaUsuarioLogado.verificaUsuarioLogado(ConsultarVeiculoActivity.this).equals("ADM")
                || verificaUsuarioLogado.verificaUsuarioLogado(ConsultarVeiculoActivity.this).equals("SECRETARIA"))){
            buttonEditar.setVisibility(View.INVISIBLE);
            buttonExcluir.setVisibility(View.INVISIBLE);
        }


        //verifica stado do switch e set condiçao de pesquisa
       tipoPesquisa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked == true){
                   limpaTextViews();
                   editTextEmailDonoVeiculo.setText("");
                   pesquisaPlaca = true;
                   editTextEmailDonoVeiculo.setHint("Digite a placa do veìculo..");
                   adicionaMascara();
               }else{
                   limpaTextViews();
                   pesquisaPlaca = false;
                   editTextEmailDonoVeiculo.setText("");
                   editTextEmailDonoVeiculo.setHint("Email do dono do veìculo..");
                   editTextEmailDonoVeiculo.removeTextChangedListener(mtw);

               }
           }
       });


        //Botao para Buscar veiculo
        imageViewBuscarVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.invocaDialog("Pesquisando veículo...");

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
                            excluirVeiculo();
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


    public void buscaVeiculo() {

        campoInformado = editTextEmailDonoVeiculo.getText().toString().toLowerCase().trim();
        codificaEmail = campoInformado;

        if(pesquisaPlaca){
            childDatabse = "placa";
            campoInformado = editTextEmailDonoVeiculo.getText().toString().toUpperCase().trim();
            codificaEmail = campoInformado;
        }else{
            childDatabse = "uid";
            codificaEmail = Base64Custom.codificarBase64(campoInformado);
        }

        placaDigitada = editTextEmailDonoVeiculo.getText().toString().toUpperCase();
        Query query = databaseReferenceVeiculo;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                   placaDatabse = postSnapshot.child(childDatabse).getValue(String.class);

                    if(placaDatabse.equals(codificaEmail)){
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
                        placaEncontrada = true;
                        flag = true;
                        progressDialog.disableDialog();
                        break;
                    }

                }
                if(placaEncontrada == false){
                    progressDialog.disableDialog();
                    if(pesquisaPlaca == true) {
                        Toast.makeText(getApplicationContext(), "Placa não encontrada", Toast.LENGTH_LONG).show();
                        //finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Email não encontrado",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.disableDialog();
                Toast.makeText(getApplicationContext(),"Erro de conexão",Toast.LENGTH_SHORT).show();
            }
        });
    }

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
        finish();
        invocaActivitys.invocaPrincipal(ConsultarVeiculoActivity.this,this,preferencias.recuperaTipo(this));

    }

    @Override
    public void adicionaMascara() {

        if(pesquisaPlaca == true) {
             smf = new SimpleMaskFormatter("LLL-NNNN");
             mtw = new MaskTextWatcher(editTextEmailDonoVeiculo,smf);
             editTextEmailDonoVeiculo.addTextChangedListener(mtw);

        }
    }

    public void limpaTextViews(){
        textViewCorVeiculo.setText("");
        textViewMarcaVeiculo.setText("");
        textViewModeloVeiculo.setText("");
        textViewPlaca.setText("");
    }

    public void excluirVeiculo(){

            databaseReferenceVeiculo = FirebaseDatabase.getInstance().getReference("veiculo").child(uid);
            databaseReferenceVeiculo.removeValue();
            Toast.makeText(getApplicationContext(), "Veículo Excluído!", Toast.LENGTH_SHORT).show();
            limpaTextViews();

    }

}
