package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
//import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Presenter.UsuarioCrud;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class CadastroUsuarioActivity extends AppCompatActivity implements IActivity {

    private EditText editTextNomeUsuario, editTextTelefoneUsuario, editTextEmailUsuario, editTextCpfUsuario;
    private CheckBox checkBoxPossuiNecessidade;
    private Button buttonInserirUsuario;
    private Usuario usuario = new Usuario();
    private DatabaseReference databaseReference = usuario.getFirebaseReferences();
    private FirebaseAuth autenticacao = usuario.getAutenticacao();
    private String nome, telefone, email, tipo, cpf, senha = "200200", emailCurrentUser, senhaCurrentUser, itemSelect, status = "ATIVO", codificarEmail, cpfDatabase;
    private String possuiNecessidade;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private Spinner spinner;
    private ViewPager viewPager;
    private Boolean validaCpf = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario_cadastro);

        //toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Cadastro de usuário");
        setSupportActionBar(toolbar);



        editTextNomeUsuario = (EditText) findViewById(R.id.editnomeid_cadastro);
        editTextTelefoneUsuario = (EditText) findViewById(R.id.edittelefoneid_cadastro);
        editTextEmailUsuario = (EditText) findViewById(R.id.editemailid_cadastro);
        spinner = (Spinner)findViewById(R.id.spinnerTipo);
        editTextCpfUsuario = (EditText) findViewById(R.id.editTextCpfEditaUsuario);
        //checkBoxPossuiNecessidade = (CheckBox) findViewById(R.id.checkBox_cadastro);
        buttonInserirUsuario = (Button) findViewById(R.id.button_cadastro);
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        //Spiner Adapter
        SpinnerAdapter adapter = spinner.getAdapter();
         //inicializa o spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemSelect = parent.getItemAtPosition(position).toString();
                if(itemSelect.equals("Administrador")){
                    tipo = "ADM";
                }else if(itemSelect.equals("Usuário")){
                    tipo = "USER";
                }else if(itemSelect.equals("Secretaria")){
                    tipo = "SECRETARIA";
                }else if(itemSelect.equals("Garagista")){
                    tipo = "GARAGISTA";
                }
                //Toast.makeText(getApplicationContext(),itemSelect,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();

        senhaCurrentUser = intent.getStringExtra(AdmActivity.SENHA_ADM);

        // metodo para adicionar mascara aos campos
        adicionaMascara();

        buttonInserirUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirUsuario();
        }
        });


        }

    public void inserirUsuario(){

        nome = editTextNomeUsuario.getText().toString().trim().toUpperCase();
        telefone = editTextTelefoneUsuario.getText().toString().trim();
        email = editTextEmailUsuario.getText().toString().trim().toLowerCase();
        cpf = editTextCpfUsuario.getText().toString().trim();

        /*if(checkBoxPossuiNecessidade.isChecked()){
            possuiNecessidade = "SIM";
        }else{
            possuiNecessidade = "NAO";
        }*/

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha) && !TextUtils.isEmpty(nome) && !TextUtils.isEmpty(telefone) &&
        !TextUtils.isEmpty(tipo) && !TextUtils.isEmpty(cpf)){
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Inserindo...");
            progressDialog.show();
            emailCurrentUser = usuario.getEmailCurrentUser();
            final Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
           // preferencias.salvarusuarioPreferences(emailCurrentUser, senhaCurrentUser);
                    autenticacao.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    try{
                                        if(task.isSuccessful()){
                                            cadastraUsuario(preferencias);
                                        }
                                    }catch(Exception e){

                                        Toast.makeText(CadastroUsuarioActivity.this,"Nao foi possível cadastrar: "+e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
        }else{
            Toast.makeText(getApplicationContext(), "Há campos vazios", Toast.LENGTH_LONG).show();
        }
    }

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

    //retorna para a página inicial
    public  void voltar(){
        Intent intent = new Intent(getApplicationContext(),AdmActivity.class);
        startActivity(intent);
        finish();
    }

    public void adicionaMascara(){
        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTextTelefoneUsuario,smf);
        editTextTelefoneUsuario.addTextChangedListener(mtw);

        smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        mtw = new MaskTextWatcher(editTextCpfUsuario,smf);
        editTextCpfUsuario.addTextChangedListener(mtw);

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), AdmActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();

    }

    public void cadastraUsuario(Preferencias preferencias){


        codificarEmail = Base64Custom.codificarBase64(email);
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setTelefone(telefone);
        usuario.setTipo(tipo);
        usuario.setCpf(cpf);
        //usuario.setPossuiNecessidadeEsp(possuiNecessidade);
        usuario.setSenha(senha);
        usuario.setStatus("ATIVO");
        usuario.setUid(codificarEmail);


        usuario.Create();

        editTextNomeUsuario.setText(null);
        editTextTelefoneUsuario.setText(null);
        editTextEmailUsuario.setText(null);
        editTextCpfUsuario.setText(null);

        Toast.makeText(getApplicationContext(), "Novo usuário Registrado com sucesso!", Toast.LENGTH_LONG).show();

        String emailAdm = preferencias.recuperaEmail(getApplicationContext()); //userDetails.getString("email", "");
        String senhaAdm = preferencias.recuperaSenha(getApplicationContext()); //userDetails.getString("senha", "");
        autenticacao.signInWithEmailAndPassword(emailAdm, senhaAdm)
                .addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("RELOGIN", "FAILED");
                        } else {
                            Log.e("RELOGIN", "SUCCESS");
                        }
                    }
                });
    }
}
