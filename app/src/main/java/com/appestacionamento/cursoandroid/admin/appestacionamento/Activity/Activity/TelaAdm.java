package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.ColorUtils;
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
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.SlidingTabLayout;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.auth.FirebaseAuth;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.consultaUsuarioFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TelaAdm extends AppCompatActivity {
    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private Button buttonCadastrar;
    private Button buttonBuscar;
    private String senha, emailBusca, emailDatabase, emailCodificado;
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

    public void onFragmentViewCreated(View view) {
        // Iniciar os campos buscando no layout do Fragment
        buttonCadastrar = (Button) view.findViewById(R.id.btnCadastrar);
        buttonBuscar = (Button) view.findViewById(R.id.btnconsulta);
        textViewNome = (TextView) view.findViewById(R.id.nomeId);
        textViewTelefone = (TextView) view.findViewById(R.id.telefoneId);
        textViewEmail = (TextView) view.findViewById(R.id.emailId);
        textViewTipo = (TextView) view.findViewById(R.id.tipoId);
        textViewSituacao = (TextView) view.findViewById(R.id.statusId);
        editTextBusca = (EditText) view.findViewById(R.id.consultaId);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaCadastroUsuario.class);
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
                                    //textViewIdade.setText(postSnapshot.child("idade").getValue(Integer.class).toString());
                                    //textViewSexo.setText(postSnapshot.child("sexo").getValue(String.class));
                                    Toast.makeText(getApplicationContext(), "Nome: "+postSnapshot.child("nome").getValue(String.class)+"\n" +
                                            "Email: "+postSnapshot.child("email").getValue(String.class), Toast.LENGTH_LONG).show();
                                    //Toast.makeText(getApplicationContext(), "Usuario encontrado", Toast.LENGTH_LONG).show();
                                    //return;

                                    emailCodificado = null;
                                    emailBusca = "";

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
            case R.id.menu_meusdados:chamaConsulta();break;
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
        Intent intent = new Intent(getApplication(),consultaUsuarioActivity.class);
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
