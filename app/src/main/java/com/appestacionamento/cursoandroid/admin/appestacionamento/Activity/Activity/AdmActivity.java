package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.TabAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.SlidingTabLayout;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.ConsultaUsuarioActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdmActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private Button buttonCadastrar;
    private Button buttonBuscar;
    private Button buttonEditar;
    private String senha, emailBusca, emailDatabase, emailCodificado;
    private String editNome , editTelefone, editTipo, editCpf, editUid, editEmail, editSenha, editNesc, editStatus;
    public static final String EDITNOME = "nome", EDITTELEFONE = "telefone", EDITTIPO = "tipo", EDITCPF = "cpf",
    EDITEMAIL = "email", EDITUID = "uid", EDITSENHA = "senha", EDITNESC = "nesc", EDITSTATUS = "status";
    private Boolean ativaEdicao = false;
    private TextView textViewNome, textViewTelefone, textViewEmail, textViewTipo, textViewSituacao;
    private EditText editTextBusca;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    public  static final String SENHA_ADM = "senhaadm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.stlayout);
        viewPager = (ViewPager)findViewById(R.id.vpLayout);

        //distribui as tabas proporcionalmente na tela
        slidingTabLayout.setDistributeEvenly(true);
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("ADM");

        setSupportActionBar(toolbar);

        //configurando Adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);

        Intent intent = getIntent();
        senha = intent.getStringExtra(LoginActivity.SENHA_ADM);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

   public void onFragmentViewCreated(View view){

    }

    /**
    public void onFragmentViewCreated(View view) {
        // Iniciar os campos buscando no layout do Fragment
        buttonCadastrar = (Button) view.findViewById(R.id.btnCadastrar);
        buttonBuscar = (Button) view.findViewById(R.id.btnconsulta);
        buttonEditar = (Button) view.findViewById(R.id.btnEditar);
        textViewNome = (TextView) view.findViewById(R.id.nomeId);
        textViewTelefone = (TextView) view.findViewById(R.id.telefoneId);
        textViewEmail = (TextView) view.findViewById(R.id.emailId);
        textViewTipo = (TextView) view.findViewById(R.id.tipoId);
        textViewSituacao = (TextView) view.findViewById(R.id.statusId);
        editTextBusca = (EditText) view.findViewById(R.id.consultaId);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroUsuarioActivity.class);
                intent.putExtra(SENHA_ADM, senha);
                startActivity(intent);
                finish();
            }
        });

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "Usuario encontrado", Toast.LENGTH_LONG).show();

                emailBusca = editTextBusca.getText().toString();
                emailCodificado = Base64Custom.codificarBase64(emailBusca);
                Query usersQuery = databaseReference;//orderByKey();//.startAt(input).endAt(input + "\uf8ff");
                usersQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            emailDatabase = postSnapshot.child("uid").getValue(String.class);
                            //Toast.makeText(getApplicationContext(), "Usuario encontrado", Toast.LENGTH_LONG).show();
                            //idDatabse.toString();
                            try{
                                if(emailDatabase.equals(emailCodificado)){

                                    textViewNome.setText(postSnapshot.child("nome").getValue(String.class));
                                    textViewTelefone.setText(postSnapshot.child("telefone").getValue(String.class));
                                    textViewEmail.setText(postSnapshot.child("email").getValue(String.class));
                                    textViewTipo.setText(postSnapshot.child("tipo").getValue(String.class));
                                    textViewSituacao.setText(postSnapshot.child("status").getValue(String.class));


                                    editNome = postSnapshot.child("nome").getValue(String.class);
                                    editCpf = postSnapshot.child("cpf").getValue(String.class);
                                    editTelefone = postSnapshot.child("telefone").getValue(String.class);
                                    editTipo = postSnapshot.child("tipo").getValue(String.class);
                                    editUid = postSnapshot.child("uid").getValue(String.class);
                                    editEmail = postSnapshot.child("email").getValue(String.class);
                                    editSenha = postSnapshot.child("senha").getValue(String.class);
                                    editNesc = postSnapshot.child("possuiNecessidadeEsp").getValue(String.class);
                                    editStatus = postSnapshot.child("status").getValue(String.class);

                                    Toast.makeText(getApplicationContext(), "Nome: "+postSnapshot.child("nome").getValue(String.class)+"\n" +
                                            "Email: "+postSnapshot.child("email").getValue(String.class), Toast.LENGTH_LONG).show();
                                    //Toast.makeText(getApplicationContext(), "Usuario encontrado", Toast.LENGTH_LONG).show();
                                    //return;

                                    emailCodificado = null;
                                    emailBusca = "";
                                    ativaEdicao = true;
                                }
                            }catch(Exception e){

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //buttonBuscar.setEnabled(false);
            }
        });

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ativaEdicao == true){

                    textViewNome.setText(null);
                    textViewTelefone.setText(null);
                    textViewEmail.setText(null);
                    textViewTipo.setText(null);
                    textViewSituacao.setText(null);

                    editaUsuario();

                    ativaEdicao = false;
                }else if(ativaEdicao == false){
                    Toast.makeText(getApplicationContext(), "Pesquise um usuário para relizar sua edição!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
     }     */



    private void editaUsuario(){
        Intent intent = new Intent(getApplicationContext(), EditaDadosActivity.class);
        intent.putExtra(EDITNOME, editNome);
        intent.putExtra(EDITTELEFONE, editTelefone);
        intent.putExtra(EDITTIPO, editTipo);
        intent.putExtra(EDITCPF, editCpf);
        intent.putExtra(EDITUID, editUid);
        intent.putExtra(EDITEMAIL, editEmail);
        intent.putExtra(EDITSENHA, editSenha);
        intent.putExtra(EDITNESC, editNesc);
        intent.putExtra(EDITSTATUS, editStatus);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair :
                sair();
                //finish();
                break;
            case R.id.menu_meusdados:
                chamaConsulta();
                break;
        }
        return true;
    }

    private void sair(){
        Usuario usuario = new Usuario();
        usuario.desloga();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void chamaConsulta(){
        Intent intent = new Intent(getApplication(),ConsultaUsuarioActivity.class);
        startActivity(intent);
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
