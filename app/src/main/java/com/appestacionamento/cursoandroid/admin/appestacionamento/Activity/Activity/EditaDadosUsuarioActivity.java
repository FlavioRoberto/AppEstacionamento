package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.AdmActivity;

public class EditaDadosUsuarioActivity extends AppCompatActivity implements IActivity {

    private EditText editTextNome, editTextTelefone, editTextCpf;
    private Spinner  spinner;
    private Button buttonAtualizar;
    private String nome, telefone, cpf, tipo, uid, email, status;
    private String novoNome, novoTelefone, novoCpf, novoTipo;
    private DatabaseReference databaseReference;
    private modelUsuario usuario = new modelUsuario();
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
        buttonAtualizar = (Button) findViewById(R.id.buttonAtualizarUsuario);

        //pega os valores passados da view consultar
        pegaExtra();

        //aplica a mascara nos campos
        adicionaMascara();

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
                novoTipo = tipo;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //FINALIZA SPINNER

        //Mostra os dados do usuário que foi pesquisado
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

        novoNome = editTextNome.getText().toString().toUpperCase().trim();
        novoTelefone = editTextTelefone.getText().toString().trim();
        novoCpf = editTextCpf.getText().toString().trim();
      //  if(!novoNome.equals(nome) || !novoTelefone.equals(telefone) || !novoTipo.equals(tipo) || !novoCpf.equals(cpf)){

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


            usuario.setEmail(email);
            usuario.setUid(uid);
            usuario.setStatus(status);

            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
            databaseReference.setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Atualização realizada!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), AdmActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        try{
                            throw task.getException();
                        }catch (FirebaseNetworkException e){
                            Toast.makeText(getApplicationContext(), "Sem conexão de dados", Toast.LENGTH_SHORT).show();
                        }catch (FirebaseException e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                        catch (Exception e) {
                           Toast.makeText(getApplicationContext(),"Não foi possível atualizar usuário",Toast.LENGTH_LONG);
                        }
                    }
                }

            });

    /*
        }else{
            Toast.makeText(getApplicationContext(),"Verifique campos vazios",Toast.LENGTH_SHORT).show();
        }
*/
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
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void adicionaMascara() {
        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTextTelefone, smf);
        editTextTelefone.addTextChangedListener(mtw);

        smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        mtw = new MaskTextWatcher(editTextCpf, smf);
        editTextCpf.addTextChangedListener(mtw);
    }

    public void pegaExtra(){
        Intent intent = getIntent();
        nome = intent.getStringExtra(ConsultaUsuarioActivity.EDITNOME);
        telefone = intent.getStringExtra(ConsultaUsuarioActivity.EDITCELULAR);
        cpf = intent.getStringExtra(ConsultaUsuarioActivity.EDITCPF);
        tipo = intent.getStringExtra(ConsultaUsuarioActivity.EDITTIPO);
        uid = intent.getStringExtra(ConsultaUsuarioActivity.EDITUID);
        email = intent.getStringExtra(ConsultaUsuarioActivity.EDITEMAIL);
        status = intent.getStringExtra(ConsultaUsuarioActivity.EDITSTATUS);
    }

    //desloga usuario e vai pra tela de login
    public void sair(){
        sairAplicacao.logout(getApplicationContext(),this);
    }

    @Override
    public void sobre() {

    }

    //retorna para a página inicial
    public  void voltar(){
        Preferencias preferencias = new Preferencias(EditaDadosUsuarioActivity.this);
        invocaActivitys.invocaPrincipal(EditaDadosUsuarioActivity.this,this,preferencias.recuperaTipo(EditaDadosUsuarioActivity.this));
        finish();
    }


}
