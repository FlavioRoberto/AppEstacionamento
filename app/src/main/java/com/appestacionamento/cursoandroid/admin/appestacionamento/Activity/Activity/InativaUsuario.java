package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.AdmActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Secretaria.SecretariaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.verificaUsuarioLogado;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class InativaUsuario extends AppCompatActivity implements IActivity {

    private String emailDatabase,codificaEmail, emailUsuario;
    private String mudaStatus, cpf, email, nome, senha, telefone, tipo, uid;
    private modelUsuario usuario ;
    private EditText editTextBuscarEmailUsuario;
    private DatabaseReference databaseReference = configuracaoFirebase.getFirebase();
    private boolean flag,emailEncontrado;
    private TextView nomeText, cpfText , tipoText;
    private CheckBox statuscheck;
    private Button btnSalvar;
    private ImageView btnPesquisar;
    private Toolbar toolbar;

    //INICIO OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inativa_usuario);
        flag = false;
       // emailEncontrado = false;
        editTextBuscarEmailUsuario = (EditText) findViewById(R.id.inativaEditConsultaId);
        nomeText = (TextView) findViewById(R.id.InativaValorNomeId);
        cpfText = (TextView) findViewById(R.id.InativaValorCpfid);
        tipoText = (TextView) findViewById(R.id.InativaValorTipoid);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnPesquisar = (ImageView) findViewById(R.id.inativaBtnbuscar);
        usuario = new modelUsuario();
        statuscheck = (CheckBox) findViewById(R.id.InativaCheckBoxStatus);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        //TOOLBAR
        toolbar =(Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Inativar usuário");
        setSupportActionBar(toolbar);

        //FIM TOOLBAR


        //INICIO botao pesquisar
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeText.setText(null);
                cpfText.setText(null);
                tipoText.setText(null);
                statuscheck.setChecked(false);
                pesquisaUsuario();
                //  Usuario teste = new Usuario();

            }
        });
        //FIM botao pesquisar

        //INICIO botao salvar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true){
                    salvarStatus();
                }else if(flag == false){
                    Toast.makeText(getApplicationContext(), "Nenhum usuário pesquisado", Toast.LENGTH_LONG).show();
                    return;
                }
                flag = false;
            }
        });
    }
    //FIM onCreate


    //INICIO PESQUISA USUARIO
        public void pesquisaUsuario(){
        if(!editTextBuscarEmailUsuario.getText().toString().isEmpty()) {
            {
                emailUsuario = editTextBuscarEmailUsuario.getText().toString().toLowerCase().trim();
                codificaEmail = Base64Custom.codificarBase64(emailUsuario);
                Query query = databaseReference;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            emailDatabase = postSnapshot.child("uid").getValue(String.class);
                            try{


                                    if (emailDatabase.equals(codificaEmail)) {
                                        cpf = postSnapshot.child("cpf").getValue(String.class);
                                        email = postSnapshot.child("email").getValue(String.class);
                                        nome = postSnapshot.child("nome").getValue(String.class);
                                        senha = postSnapshot.child("senha").getValue(String.class);
                                        mudaStatus = postSnapshot.child("status").getValue(String.class);
                                        telefone = postSnapshot.child("telefone").getValue(String.class);
                                        tipo = postSnapshot.child("tipo").getValue(String.class);
                                        uid = postSnapshot.child("uid").getValue(String.class);
                                        nomeText.setText(nome);
                                        cpfText.setText(cpf);
                                        tipoText.setText(tipo);
                                        flag = true;
                                        emailEncontrado = true;
                                        if (mudaStatus.equals("INATIVADO")) {
                                            statuscheck.setChecked(true);
                                        } else if (mudaStatus.equals("ATIVADO")) {
                                            statuscheck.setChecked(false);
                                        }
                                        break;
                                    }else {
                                        emailEncontrado = false;
                                    }
                            }catch (Exception e){
                              //  Toast.makeText(getApplicationContext(), "Email nao encontrado", Toast.LENGTH_LONG).show();
                            }//catch
                        }//for

                        if(emailEncontrado == false){
                                Toast.makeText(getApplicationContext(), "Email nao encontrado", Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(InativaUsuario.this,"Erro de conexão",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            Toast.makeText(getApplicationContext(), "Campo de email vazio!", Toast.LENGTH_LONG).show();

        }
    }
    //FIM PESQUISA USUARIO


    public void salvarStatus(){
        if(statuscheck.isChecked()){
            mudaStatus = "INATIVADO";
        }
        if(!statuscheck.isChecked()){
            mudaStatus = "ATIVADO";
        }
        usuario.setStatus(mudaStatus);
        usuario.setEmail(email);
        usuario.setUid(uid);
        usuario.setSenha(senha);
        usuario.setCpf(cpf);
        usuario.setNome(nome);
        usuario.setTipo(tipo);
        usuario.setTelefone(telefone);
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
        databaseReference.setValue(usuario);
        Toast.makeText(getApplicationContext(), "Atualização realizada!", Toast.LENGTH_LONG).show();
    }

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

    //invoca os itens no menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //desloga usuario e vai pra tela de login
    public void sair(){
        sairAplicacao.logout(getApplicationContext(),this);
    }

    @Override
    public void sobre() {
        invocaActivitys.invocaSobre(getApplicationContext(),this);

    }

    @Override
    public void adicionaMascara() {

    }

    //retorna para a página inicial
    public  void voltar(){

        if(verificaUsuarioLogado().equals("ADM")) {
            Intent intent = new Intent(getApplicationContext(), AdmActivity.class);
            startActivity(intent);
            finish();
        }
        if(verificaUsuarioLogado().equals("SECRETARIA")){
            Intent intent = new Intent(getApplicationContext(), SecretariaActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public String verificaUsuarioLogado(){
       return verificaUsuarioLogado.verificaUsuarioLogado(getApplicationContext());
    }

}






