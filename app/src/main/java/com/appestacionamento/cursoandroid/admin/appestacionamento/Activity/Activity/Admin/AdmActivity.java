package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin;


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

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.ConsultaUsuarioActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.EditaDadosUsuarioActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.LoginActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.TabAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.SlidingTabLayout;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private String[] abas = new String[]{
      "USUÁRIOS", "VAGAS","VEÍCULO"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.stlayout);
        viewPager = (ViewPager)findViewById(R.id.vpLayout);
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("Administrador");
        setSupportActionBar(toolbar);

        //distribui as tabas proporcionalmente na tela
        slidingTabLayout.setDistributeEvenly(true);

        //configurando Adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.setUsuario("ADM");
        tabAdapter.setAbas(abas);
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);

        //Intent intent = getIntent();
        //senha = intent.getStringExtra(LoginActivity.SENHA_ADM);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }



   public void onFragmentViewCreated(View view){

    }


    private void editaUsuario(){
        Intent intent = new Intent(getApplicationContext(), EditaDadosUsuarioActivity.class);
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
        inflater.inflate(R.menu.menu_toolbar,menu);
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
        modelUsuario usuario = new modelUsuario();
        usuario.desloga();
        finish();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);

    }

    public void chamaConsulta(){
        Intent intent = new Intent(getApplication(),ConsultaUsuarioActivity.class);
        startActivity(intent);
        finish();
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


