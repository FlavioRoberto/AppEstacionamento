package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class TelaCadastroUsuario extends AppCompatActivity {

    private EditText editTextNomeUsuario, editTextTelefoneUsuario, editTextEmailUsuario, editTextTipoUsuario,
                     editTextCpfUsuario;
    private CheckBox checkBoxPossuiNecessidade;
    private Button buttonInserirUsuario;
    private Usuario usuario = new Usuario();
    private DatabaseReference databaseReference = usuario.getFirebaseReferences();
    private FirebaseAuth autenticacao = usuario.getAutenticacao();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String nome, telefone, email, tipo, cpf, senha, emailCurrentUser, senhaCurrentUser, codificarEmail;
    private String possuiNecessidade;
    private ProgressDialog progressDialog;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario_cadastro);

        editTextNomeUsuario = (EditText) findViewById(R.id.editTextNomeUsuario);
        editTextTelefoneUsuario = (EditText) findViewById(R.id.editTextTelefoneUsuario);
        editTextEmailUsuario = (EditText) findViewById(R.id.editTextEmailUsuario);
        editTextTipoUsuario = (EditText) findViewById(R.id.editTextTipoUsuario);
        editTextCpfUsuario = (EditText) findViewById(R.id.editTextCpfUsuario);
        checkBoxPossuiNecessidade = (CheckBox) findViewById(R.id.checkBoxNescUsuario);
        buttonInserirUsuario = (Button) findViewById(R.id.buttonInserirUsuario);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        senhaCurrentUser = intent.getStringExtra(TelaAdm.SENHA_ADM);

        buttonInserirUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirUsuario();
            }
        });

    }

    private void inserirUsuario(){
        nome = editTextNomeUsuario.getText().toString().trim().toUpperCase();
        telefone = editTextTelefoneUsuario.getText().toString().trim();
        email = editTextEmailUsuario.getText().toString().trim().toLowerCase();
        tipo = editTextTipoUsuario.getText().toString().toUpperCase().trim();
        cpf = editTextCpfUsuario.getText().toString().trim();
        if(checkBoxPossuiNecessidade.isChecked()){
            possuiNecessidade = "SIM";
        }else{
            possuiNecessidade = "NAO";
        }
        senha = "200200";

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha) && !TextUtils.isEmpty(nome) && !TextUtils.isEmpty(telefone) &&
        !TextUtils.isEmpty(tipo) && !TextUtils.isEmpty(cpf)){
            progressDialog.setMessage("Inserindo...");
            progressDialog.show();
            emailCurrentUser = usuario.getEmailCurrentUser();
            SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
            SharedPreferences.Editor edit = userDetails.edit();
            edit.clear();
            edit.putString("email", emailCurrentUser);
            edit.putString("senha", senhaCurrentUser);
            edit.commit();
            autenticacao.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            try{
                                if(task.isSuccessful()){


                                    codificarEmail = Base64Custom.codificarBase64(email);
                                    usuario.setEmail(email);
                                    usuario.setNome(nome);
                                    usuario.setTelefone(telefone);
                                    usuario.setTipo(tipo);
                                    usuario.setCpf(cpf);
                                    usuario.setPossuiNecessidadeEsp(possuiNecessidade);
                                    usuario.setSenha(senha);
                                    usuario.setStatus("ATIVO");
                                    usuario.setUid(codificarEmail);


                                    usuario.Create();
                                    SharedPreferences userDetails = getApplicationContext().getSharedPreferences("userdetails", MODE_PRIVATE);
                                    String emailAdm = userDetails.getString("email", "");
                                    String senhaAdm = userDetails.getString("senha", "");
                                    autenticacao.signInWithEmailAndPassword(emailAdm, senhaAdm)
                                            .addOnCompleteListener(TelaCadastroUsuario.this, new OnCompleteListener<AuthResult>() {
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
                            }catch(Exception e){
                                Log.v("E_VALUE", "Erro: "+ e);
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), TelaAdm.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
