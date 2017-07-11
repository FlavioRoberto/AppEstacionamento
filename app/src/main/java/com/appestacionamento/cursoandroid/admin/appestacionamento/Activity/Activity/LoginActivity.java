package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.AdmActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista.GaragistaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Secretaria.SecretariaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario.ActivityUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario.ConsultaVagaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextSenha;
    private Button buttonLogin;
    private TextView textViewRedefinirSenha;
    private modelUsuario user = new modelUsuario();
    private FirebaseAuth autenticacao = configuracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference databaseReference = user.getFirebaseReferences();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Preferencias preferencias;
    private ProgressDialog progressDialog;
    private String codificaEmail, emailLogin, senha;
    private Boolean authFlag = false, loginFlag=false,emailFlag = false;
    private boolean isConnected;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferencias = new Preferencias(getApplicationContext());
        editTextEmail = (EditText) findViewById(R.id.emailId);
        editTextSenha = (EditText) findViewById(R.id.senhaId);
        buttonLogin = (Button) findViewById(R.id.logarId);
        progressDialog = new ProgressDialog(LoginActivity.this);
        editTextEmail = (EditText) findViewById(R.id.emailId);
        editTextSenha = (EditText) findViewById(R.id.senhaId);
        buttonLogin = (Button) findViewById(R.id.logarId);
        textViewRedefinirSenha = (TextView) findViewById(R.id.eSenhaId);

        cm = (ConnectivityManager)getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        isConnected =  activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected == true){
          redirecionarUsuario();
        }


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(isConnected == true && loginFlag == false){
                        loginFlag = true;
                        logar();

                    }else if(isConnected == false){
                        Toast.makeText(getApplicationContext(), "Sem conexão", Toast.LENGTH_LONG).show();
                        return;
                    }
            }
        });

        textViewRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected == true){
                    Intent intent = new Intent(getApplicationContext(), RedefinirSenhaActivity.class);
                    startActivity(intent);
                    finish();
                }else if(isConnected == false){
                    Toast.makeText(getApplicationContext(), "Sem conexão", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }


    //Verifica conexao
   /*
    private void verificarUsuarioLogado() {
        if (autenticacao.getCurrentUser() != null) {
            redirecionarUsuario();
        }else {
            return;
        }
    }
    */


    @Override
    protected void onStart() {
        super.onStart();
        //autenticacao.signOut();
        if(isConnected == true)
            autenticacao.addAuthStateListener(mAuthListener);
    }

    private void logar() {

        emailLogin = editTextEmail.getText().toString().trim();
        senha = editTextSenha.getText().toString().trim();

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Acessando Perfil...");
        progressDialog.show();

        if (TextUtils.isEmpty(emailLogin) || TextUtils.isEmpty(senha)) {
            Toast.makeText(LoginActivity.this, "Campos vazios", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            loginFlag = false;
        } else {
            autenticacao.signInWithEmailAndPassword(emailLogin, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                   if(task.isSuccessful()){
                    //   redirecionarUsuario();
                       return;
                   }
                    if (!task.isSuccessful()) {

                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            loginFlag = false;
                            Toast.makeText(LoginActivity.this, "Usuário inválido", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } catch (FirebaseNetworkException e) {
                            loginFlag = false;
                            Toast.makeText(LoginActivity.this, "Sem conexão", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            loginFlag = false;
                            Toast.makeText(LoginActivity.this, "Senha Inválida", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            loginFlag = false;
                            progressDialog.dismiss();
                            //Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        loginFlag = false;
                        return;
                    }

                }
            });
        }
    }


    private void redirecionarUsuario() {


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser userEmail = firebaseAuth.getCurrentUser();


                if (firebaseAuth.getCurrentUser() != null && userEmail.isEmailVerified() && authFlag == false) {


                    if (authFlag == false) {

                        if (!(LoginActivity.this).isFinishing()) {
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.setCancelable(true);
                            progressDialog.setMessage("Acessando Perfil...");
                            progressDialog.show();

                        }


                        String email = user.getEmailCurrentUser();
                        codificaEmail = Base64Custom.codificarBase64(email);
                        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(codificaEmail);
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                    //Primeiro valor: child, Segundo valor: key
                                    GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                                    Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);

                                    String status = map.get("status");
                                    String tipo = map.get("tipo");
                                    preferencias.salvarusuarioPreferences(emailLogin,senha,tipo);

                                    //Verifica se o usuario está ativado para poder acessar o sistema
                                    if(status.equals("ATIVADO")) {

                                        if (tipo.equals("ADM")) {
                                            //Toast.makeText(getApplicationContext(), "Logado como ADM", Toast.LENGTH_LONG).show();
                                            finish();
                                            Intent intent = new Intent(LoginActivity.this, AdmActivity.class);
                                            startActivity(intent);
                                            progressDialog.dismiss();
                                            authFlag = false;

                                        }  else if (tipo.equals("SECRETARIA")) {
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(), SecretariaActivity.class);
                                            // intent.putExtra(SENHA_ADM,senha);
                                            startActivity(intent);
                                            progressDialog.dismiss();
                                            authFlag = false;

                                        } else if(tipo.equals("USER")){
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(), ConsultaVagaActivity.class);
                                            startActivity(intent);
                                            progressDialog.dismiss();
                                            authFlag = false;

                                        }else if(tipo.equals("GARAGISTA")){
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(),GaragistaActivity.class);
                                            startActivity(intent);
                                            progressDialog.dismiss();
                                            authFlag = false;

                                        }
                                    }else if(status.equals("INATIVADO")) {

                                        authFlag = false;
                                        loginFlag = false;
                                        Toast.makeText(getApplicationContext(), "Este usuário está Inativado no sistema", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        return;
                                    }

                                }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                               Toast.makeText(getApplicationContext(),"Problema ao conectar com banco\n"+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                        authFlag = false;
                        return;
                    }else {
                        return;
                    }
                }else{
                    //Toast.makeText(LoginActivity.this,"Usuário nao verificado, confira no seu email o link para validação",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    authFlag = false;
                    return;
                }
            }


        };




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed()
    {
        finishAndRemoveTask();
    }
}
