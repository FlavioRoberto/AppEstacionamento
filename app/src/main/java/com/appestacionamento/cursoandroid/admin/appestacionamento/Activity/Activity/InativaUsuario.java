package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class InativaUsuario extends AppCompatActivity {

    private String emailDatabase,codificaEmail, emailUsuario;
    private String mudaStatus, cpf, email, nome, senha, telefone, tipo, uid;
    private Usuario usuario ;
    private EditText editTextBuscarEmailUsuario;
    private DatabaseReference databaseReference = configuracaoFirebase.getFirebase();
    private boolean flag,emailEncontrado;
    private TextView nomeText, cpfText , tipoText;
    private CheckBox statuscheck;
    private Button btnSalvar;
    private ImageView btnPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inativa_usuario);

        editTextBuscarEmailUsuario = (EditText) findViewById(R.id.inativaEditConsultaId);
        nomeText = (TextView) findViewById(R.id.InativaValorNomeId);
        cpfText = (TextView) findViewById(R.id.InativaValorCpfid);
        tipoText = (TextView) findViewById(R.id.InativaValorTipoid);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnPesquisar = (ImageView) findViewById(R.id.inativaBtnbuscar);
        usuario = new Usuario();
        statuscheck = (CheckBox) findViewById(R.id.InativaCheckBoxStatus);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        //INICIO botao pesquisar
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeText.setText(null);
                cpfText.setText(null);
                tipoText.setText(null);
                pesquisaUsuario();
                //  Usuario teste = new Usuario();

            }
        });
        //FIM botao pesquisar

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
                                if(emailDatabase.equals(codificaEmail)){
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

                                    emailEncontrado = true;
                                    if(mudaStatus.equals("INATIVADO")){
                                        statuscheck.setChecked(true);
                                    }else if(mudaStatus.equals("ATIVADO")){
                                        statuscheck.setChecked(false);
                                    }
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
        }else{
            Toast.makeText(getApplicationContext(), "Campo de email vazio!", Toast.LENGTH_LONG).show();
            return;
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
}






