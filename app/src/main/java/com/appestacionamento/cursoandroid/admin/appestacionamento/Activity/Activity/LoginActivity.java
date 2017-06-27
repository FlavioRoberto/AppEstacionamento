package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Permissoes;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.net.ConnectException;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextSenha;
    private Button buttonLogin;
    private Usuario user = new Usuario();
    private FirebaseAuth autenticacao = user.getAutenticacao();
    private DatabaseReference databaseReference = user.getFirebaseReferences();
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;
    private String codificaEmail, email, senha;
    private Boolean authFlag = false;
    public static final String SENHA_ADM = "senhaadm";
    private String uid;
    private Preferencias preferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.emailId);
        editTextSenha = (EditText) findViewById(R.id.senhaId);
        buttonLogin = (Button) findViewById(R.id.logarId);
        progressDialog = new ProgressDialog(LoginActivity.this);
        editTextEmail = (EditText) findViewById(R.id.emailId);
        editTextSenha = (EditText) findViewById(R.id.senhaId);
        buttonLogin = (Button) findViewById(R.id.logarId);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();

            }
        });

        verificarUsuarioLogado();
        redirecionarUsuario();

    }


    //Verifica conexao
    private void verificarUsuarioLogado() {

        if (autenticacao.getCurrentUser() != null) {
            redirecionarUsuario();

        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        autenticacao.addAuthStateListener(mAuthListener);
    }

    private void logar() {
        email = editTextEmail.getText().toString().trim();
        senha = editTextSenha.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(LoginActivity.this, "Campos vazios", Toast.LENGTH_LONG).show();
        } else {
            autenticacao.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            Toast.makeText(LoginActivity.this, "Usuário inválido", Toast.LENGTH_LONG).show();
                        } catch (FirebaseNetworkException e) {
                            Toast.makeText(LoginActivity.this, "Sem conexão", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(LoginActivity.this, "Email ou senha Inválido", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            //Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        email = null;
                        senha = null;
                    }
                }
            });
        }
    }


    private void redirecionarUsuario() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if (firebaseAuth.getCurrentUser() != null) {


                    if (authFlag == false) {

                        if (!(LoginActivity.this).isFinishing()) {
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.setMessage("Acessando Perfil...");
                            progressDialog.show();
                        }

                        String email = user.getEmailCurrentUser();
                        codificaEmail = Base64Custom.codificarBase64(email);
                        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(codificaEmail);
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    //Primeiro valor: child, Segundo valor: key
                                    GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                                    };
                                    Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);
                                    String tipo = map.get("tipo");
                                    String senha = map.get("senha");
                                    if (tipo.equals("ADM")) {
                                        //Toast.makeText(getApplicationContext(), "Logado como ADM", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this, AdmActivity.class);
                                        intent.putExtra(SENHA_ADM, senha);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                        finish();
                                    } else if (tipo.equals("USER")) {
                                        //Intent intent = new Intent(getApplicationContext(), Usuario.class);
                                        //startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "Logado como USER", Toast.LENGTH_LONG).show();
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        progressDialog.dismiss();
                                        finish();
                                    }
                                } catch (Exception e) {
                                    //Toast.makeText(getApplicationContext(), "Erro: " + e, Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        authFlag = true;
                    }
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
        finish();
    }
}
