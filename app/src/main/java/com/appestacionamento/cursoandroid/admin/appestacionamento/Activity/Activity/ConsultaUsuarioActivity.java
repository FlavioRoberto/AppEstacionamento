package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.Usuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ConsultaUsuarioActivity extends AppCompatActivity implements IActivity{

        private Button btnExcluir, btnEditar;
        private Toolbar toolbar;
        private ImageView imageViewBuscarUsuario;
        private EditText editTextBuscarEmailUsuario;
        private String codificaEmail, emailDatabase;
        private DatabaseReference databaseReferenceUsers, databaseReferenceVeiculo;
        private TextView textViewNomeUsuario, textViewCelularUsuario, textViewCpfUsuario, textViewTipoUsuario;
        private Boolean flag = false, emailEncontrado = false;
        private AlertDialog.Builder builder;

        //Valores que serão enviados para a activity de Edição de dados
        public static final String EDITNOME = "nome", EDITTIPO = "tipo", EDITCELULAR = "celular", EDITCPF = "cpf",
                            EDITEMAIL = "email", EDITSENHA = "senha", EDITSTATUS = "status", EDITUID = "uid";
        private String nome, tipo, celular, cpf, email, senha, status, uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_consulta_usuario);

        //invocacao do toolbar

        toolbar = (Toolbar) findViewById(R.id.toolbarId);
        toolbar.setTitle("Consulta de Usuário");
        setSupportActionBar(toolbar);


        btnExcluir = (Button) findViewById(R.id.btnExcluir);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        imageViewBuscarUsuario = (ImageView) findViewById(R.id.btnbuscar);
        editTextBuscarEmailUsuario = (EditText) findViewById(R.id.editConsultaId);
        textViewNomeUsuario = (TextView) findViewById(R.id.valorNomeId);
        textViewCelularUsuario = (TextView) findViewById(R.id.valorCelularid);
        textViewCpfUsuario = (TextView) findViewById(R.id.valorCpfid);
        textViewTipoUsuario = (TextView) findViewById(R.id.valorTipoid);
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users");


        //ao clicar chama tela editar daddos de usuario
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true){
                    flag = false;
                    putEtraEditar();

                }else if(flag == false){
                    Toast.makeText(getApplicationContext(), "Nenhum usuário pesquisado", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        //ao clicar pesquisa o email do usuario no banco
        imageViewBuscarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesquisaUsuario();
            }
        });

        //Botao Excluir (Ativa quando a busca é realizada)
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true){
                    //metodo que chama a dialog e exclui o usuário
                        abreConfirmacaoExclusao();
                   }
                else if(flag == false){
                    Toast.makeText(getApplicationContext(), "Nenhum Usuário pesquisado", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    //Método Busca Usuario
    public void pesquisaUsuario(){
        final String emailUsuario = editTextBuscarEmailUsuario.getText().toString().toLowerCase().trim();
        codificaEmail = Base64Custom.codificarBase64(emailUsuario);
        Query query = databaseReferenceUsers;
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
                            status = postSnapshot.child("status").getValue(String.class);
                            celular = postSnapshot.child("telefone").getValue(String.class);
                            tipo = postSnapshot.child("tipo").getValue(String.class);
                            uid = postSnapshot.child("uid").getValue(String.class);

                            textViewNomeUsuario.setText(nome);
                            textViewCelularUsuario.setText(celular);
                            textViewCpfUsuario.setText(cpf);
                            textViewTipoUsuario.setText(tipo);
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

    //seleciona o metodo de acordo com o click nas opções do menu

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

    @Override
    public void adicionaMascara() {

    }

    //retorna para a página inicial
    public  void voltar(){
        Intent intent = new Intent(getApplicationContext(),AdmActivity.class);
        startActivity(intent);
        finish();
    }

    public void abreConfirmacaoExclusao(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir Usuario");
        builder.setMessage("Tem certeza que deseja excluir o Usuario?");
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                excluiUsuario();
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void putEtraEditar(){
        Intent intent = new Intent(getApplicationContext(), EditaDadosUsuarioActivity.class);
        intent.putExtra(EDITNOME, nome);
        intent.putExtra(EDITTIPO, tipo);
        intent.putExtra(EDITCELULAR, celular);
        intent.putExtra(EDITCPF, cpf);
        intent.putExtra(EDITEMAIL, email);
        intent.putExtra(EDITSENHA, senha);
        intent.putExtra(EDITSTATUS, status);
        intent.putExtra(EDITUID, uid);
        startActivity(intent);
        finish();
    }

    public void excluiUsuario(){
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users").child(uid);
        databaseReferenceUsers.removeValue();
        databaseReferenceVeiculo = FirebaseDatabase.getInstance().getReference("veiculo").child(uid);
        databaseReferenceVeiculo.removeValue();
        Toast.makeText(getApplicationContext(), "Usuario Excluído!", Toast.LENGTH_SHORT).show();

    }
}
