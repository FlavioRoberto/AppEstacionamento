package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.EditaVagasActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.IActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.PreferenciasOcupaVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.invocaActivitys;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.sairAplicacao;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class listaVagaOcupacaoActivity extends AppCompatActivity  implements IActivity{

    private Toolbar toolbar;
    private CheckBox trocaStatus;
    private TextView txNumeroVaga,texSetorVaga,txStatus,txPlaca,txNecessidade;
    private Button btnAtualiza;
    private modelVaga vaga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vaga_ocupacao);

        //invocando componentes
        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        txNumeroVaga = (TextView)findViewById(R.id.numerovaga_preencher);
        texSetorVaga = (TextView)findViewById(R.id.setorpreencherid);
        txStatus = (TextView)findViewById(R.id.statuspreencher);
        txPlaca = (TextView)findViewById(R.id.statusPlaca);
        txNecessidade = (TextView)findViewById(R.id.statusNecessidade);
        trocaStatus = (CheckBox)findViewById(R.id.checkBox);
        btnAtualiza = (Button)findViewById(R.id.buttonatualizarvaga);
        vaga = new modelVaga();

        //preparando Toolbar
        toolbar.setTitle("Confirmar Veiculo");
        setSupportActionBar(toolbar);

        //preenche a view com os dados da vaga selecionado
        preencherViews();

       btnAtualiza.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               atualizaDados();
               Intent intent = new Intent(getApplicationContext(),ListaVagas_confirmarActivity.class);
               startActivity(intent);
               finish();

           }
       });

    }


    public void atualizaDados(){
        String novoStatus = "";
        PreferenciasOcupaVaga ocupaVaga = new PreferenciasOcupaVaga(this);


        vaga.setChave(ocupaVaga.recuperaChave(this));
        vaga.setVagaEspecial(ocupaVaga.recuperaVagaEsp(this));
        vaga.setNumero(ocupaVaga.recuperaNumero(this));
        vaga.setStatus(ocupaVaga.recuperaStatus(this));
        vaga.setEmailDono(ocupaVaga.recuperaEmail(this));
        vaga.setSetor(ocupaVaga.recuperaSetor(this));
        vaga.setPlacaVeiculo(ocupaVaga.recuperaPlaca(this));



        //verifica se o carro ta saindo ou entrando e seleciona acao
        if(trocaStatus.isChecked() && txStatus.getText().equals("OCUPANDO")){
            novoStatus = "OCUPADO";
            txStatus.setText(novoStatus);
            vaga.setStatus(novoStatus);

        } if(trocaStatus.isChecked() && txStatus.getText().equals("SAINDO")){
            novoStatus = "LIVRE";
            txStatus.setText(novoStatus);
            vaga.setPlacaVeiculo("");
            vaga.setEmailDono("");
            vaga.setStatus(novoStatus);

        }


        String chave  =ocupaVaga.recuperaChave(this) ;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("vaga").child(chave);
        databaseReference.setValue(vaga).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Atualização realizada!", Toast.LENGTH_LONG).show();
                }else{
                    try{
                        throw task.getException();
                    }catch (FirebaseNetworkException e){
                        Toast.makeText(getApplicationContext(), "Sem conexão de dados", Toast.LENGTH_SHORT).show();
                    }catch (FirebaseException e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Não foi possível atualizar ",Toast.LENGTH_LONG);
                    }
                }
            }

        });

    }

    public void preencherViews() {
        PreferenciasOcupaVaga ocupaVaga = new PreferenciasOcupaVaga(listaVagaOcupacaoActivity.this);
        if (ocupaVaga != null) {
            txNumeroVaga.setText(ocupaVaga.recuperaNumero(this));
            texSetorVaga.setText(ocupaVaga.recuperaSetor(this));
            txStatus.setText(ocupaVaga.recuperaStatus(this));
            txNumeroVaga.setText(ocupaVaga.recuperaPlaca(this));
            if (ocupaVaga.recuperaVagaEsp(this)) {
                txNecessidade.setText("SIM");
            } else {
                txNecessidade.setText("NÃO");
            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_anterior: voltar();break;
            case R.id.menu_meusdados:invocaActivitys.TelaMeusDados(this);break;
            case R.id.menu_sair: sair();break;
            case R.id.menu_sobre:sobre();break;
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

    //desloga usuario e vai pra tela de login
    public void sair(){
        sairAplicacao.logout(getApplicationContext(),this);
    }

    @Override
    public void sobre() {
        invocaActivitys.invocaSobre(this,this);

    }

    @Override
    public void adicionaMascara() {

    }

    //retorna para a página inicial
    public  void voltar(){

        Preferencias preferencias = new Preferencias(listaVagaOcupacaoActivity.this);
        invocaActivitys.invocaPrincipal(this,this,preferencias.recuperaTipo(this));
    }


}
