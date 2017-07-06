package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.PesquisaUsuarios;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.RedefinirSenha;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RedefinirSenhaActivity extends AppCompatActivity  {

    private EditText editTextEmail;
    private Button buttonEnviar;
    private String email, emailCodificado, emailDatabase;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private boolean flag = false;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    private RedefinirSenha redefinirSenha = new RedefinirSenha(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);


        //Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId) ;
        toolbar.setTitle("Redefinição de senha");
        setSupportActionBar(toolbar);
        //fim toolbar

        progressDialog = new ProgressDialog(this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmailRedefinirSenha);
        buttonEnviar = (Button) findViewById(R.id.buttonRedefinirSenha);
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false) {
                    enviaEmail();
                }
            }
        });
    }

    private void enviaEmail(){

        email = editTextEmail.getText().toString().toLowerCase().trim();
        if(!TextUtils.isEmpty(email)) {
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Verificando Email...");
            progressDialog.show();
            emailCodificado = Base64Custom.codificarBase64(email);
            Query query = databaseReference;
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        emailDatabase = postSnapshot.child("uid").getValue(String.class);
                        try {
                            if (emailDatabase.equals(emailCodificado)) {
                                redefinirSenha.redefineSenha(email);
                                flag = true;
                                progressDialog.dismiss();
                                break;
                            }
                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }
                    }
                    if (flag == false) {
                        Toast.makeText(getApplicationContext(), "Email inválido", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        return;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Campo vazio!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.menu_anterior: voltar();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_redefinicao,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


    public void voltar(){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
