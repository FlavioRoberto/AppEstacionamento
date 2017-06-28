package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import  com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.AdmActivity;

public class EditaDadosActivity extends AppCompatActivity {

    private EditText editTextNome, editTextTelefone, editTextCpf;
    private Spinner  spinner;
    private CheckBox checkBoxInativa;
    private Button buttonAtualizar;
    private String nome, telefone, cpf, tipo, uid, email, senha, nesc, status, itemSelectedSpinner;
    private String novoNome, novoTelefone, novoCpf, novoTipo, novoStatus;
    private DatabaseReference databaseReference;
    private Usuario usuario = new Usuario();
    private Toolbar toolbar;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editadados);

        //invocando toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Editar usuário");
        setSupportActionBar(toolbar);

        editTextNome = (EditText) findViewById(R.id.editNomeid_editadado);
        editTextTelefone = (EditText) findViewById(R.id.editTelefoneid_editadado);
        spinner = (Spinner) findViewById(R.id.editTipo_editadados);
        editTextCpf = (EditText) findViewById(R.id.editTextCpfEditaUsuario);
        checkBoxInativa = (CheckBox) findViewById(R.id.checkBoxInativar);
        buttonAtualizar = (Button) findViewById(R.id.buttonAtualizarUsuario);

        Intent intent = getIntent();
        nome = intent.getStringExtra(AdmActivity.EDITNOME);
        telefone = intent.getStringExtra(AdmActivity.EDITTELEFONE);
        cpf = intent.getStringExtra(AdmActivity.EDITCPF);
        tipo = intent.getStringExtra(AdmActivity.EDITTIPO);
        uid = intent.getStringExtra(AdmActivity.EDITUID);
        email = intent.getStringExtra(AdmActivity.EDITEMAIL);
        senha = intent.getStringExtra(AdmActivity.EDITSENHA);
        nesc = intent.getStringExtra(AdmActivity.EDITNESC);
        status = intent.getStringExtra(AdmActivity.EDITSTATUS);


        apresentaDados();

        buttonAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaDados();
            }
        });
    }




    private void apresentaDados(){

        editTextNome.setText(nome);
        editTextTelefone.setText(telefone);
        editTextCpf.setText(cpf);
    }

    private void atualizaDados(){
        //inicializa o spinner
        SpinnerAdapter adapter = spinner.getAdapter();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelect = parent.getItemAtPosition(position).toString();
                if(itemSelect.equals("Administrador")){
                    tipo = "ADM";
                }else if(itemSelect.equals("Usuário")){
                    tipo = "USER";
                }else if(itemSelect.equals("Secretaria")){
                    tipo = "SECRETARIA";
                }else if(itemSelect.equals("Garagista")){
                    tipo = "GARAGISTA";
                }
                novoTipo = itemSelect;
                //Toast.makeText(getApplicationContext(),itemSelect,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //FINALIZA SPINNER

        novoNome = editTextNome.getText().toString().toUpperCase().trim();
        novoTelefone = editTextTelefone.getText().toString().trim();
        novoCpf = editTextCpf.getText().toString().trim();
        if(!novoNome.equals(nome) || !novoTelefone.equals(telefone) || !novoTipo.equals(tipo) || !novoCpf.equals(cpf)){

            if(!novoNome.equals("")){
                usuario.setNome(novoNome);
            }
            if(!novoTelefone.equals("")){
                usuario.setTelefone(novoTelefone);
            }
            if(!novoTipo.equals("")){
                usuario.setTipo(novoTipo);
            }
            if(!novoCpf.equals("")){
                usuario.setCpf(novoCpf);
            }

            if(checkBoxInativa.isChecked()){
                novoStatus = "INATIVO";
                usuario.setStatus(novoStatus);
            }else{
                usuario.setStatus(status);
            }

            usuario.setEmail(email);
            usuario.setSenha(senha);
          //  usuario.setPossuiNecessidadeEsp(nesc);
            usuario.setUid(uid);

            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
            databaseReference.setValue(usuario);

            Toast.makeText(getApplicationContext(), "Atualização realizada!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), AdmActivity.class);
            startActivity(intent);
        }

    }

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


    //invoca os itens no menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
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


}
