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

    private String emailDatabase,codificaEmail;
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
        statuscheck = (CheckBox) findViewById(R.id.checkBoxInativar);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnPesquisar = (ImageView) findViewById(R.id.inativaBtnbuscar);
        usuario = new Usuario();

        //INICIO botao pesquisar
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesquisaUsuario();
                //  Usuario teste = new Usuario();

            }
        });
        //FIM botao pesquisar
    }


    //INICIO PESQUISA USUARIO
        public void pesquisaUsuario(){
        if(!editTextBuscarEmailUsuario.getText().toString().isEmpty()) {

            {
                final String emailUsuario = editTextBuscarEmailUsuario.getText().toString().toLowerCase().trim();
                codificaEmail = Base64Custom.codificarBase64(emailUsuario);
                Query query = databaseReference;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            emailDatabase = postSnapshot.child("uid").getValue(String.class);
                            try{
                                if(emailDatabase.equals(codificaEmail)){
                                    nomeText.setText(postSnapshot.child("nome").getValue(String.class));
                                    cpfText.setText(postSnapshot.child("cpf").getValue(String.class));
                                    tipoText.setText(postSnapshot.child("tipo").getValue(String.class));
                                    String status = postSnapshot.child("status").getValue(String.class);

                                    if(status == "INATIVADO"){
                                        statuscheck.isChecked();
                                    }

                                    flag = true;
                                    emailEncontrado = true;
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


        }

    }
    //FIM PESQUISA USUARIO
    }






