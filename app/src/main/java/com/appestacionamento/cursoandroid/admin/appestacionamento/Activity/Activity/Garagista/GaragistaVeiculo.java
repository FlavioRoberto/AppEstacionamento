package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVeiculo;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class GaragistaVeiculo extends Fragment {



    private TextView celularDono,emailDono,modeloVeiculo ,marcaVeiculo,corVeiculo;
    private EditText consultaPlaca;
    private ImageView BuscarVaga;
    private modelVeiculo veiculo;
    private modelUsuario usuario;
    private DatabaseReference referencia = configuracaoFirebase.getFirebase();


    public GaragistaVeiculo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_garagista_veiculo, container, false);

        //invocando componentes
        consultaPlaca = (EditText)view.findViewById(R.id.editConsultaId);
        celularDono = (TextView)view.findViewById(R.id.valorCelularDonoId);
        emailDono =  (TextView)view.findViewById(R.id.valorEmailDonoId);
        modeloVeiculo = (TextView)view.findViewById(R.id.valormodeloid);
        marcaVeiculo = (TextView)view.findViewById(R.id.valormarcaid);
        corVeiculo = (TextView)view.findViewById(R.id.valorcorid);
        veiculo = new modelVeiculo();
        BuscarVaga = (ImageView)view.findViewById(R.id.btnbuscar);
        usuario = new modelUsuario();

        //aplica mascara no campo EditText
        mascara();

        BuscarVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!consultaPlaca.getText().toString().toUpperCase().isEmpty()){
                    Toast.makeText(getContext(),consultaPlaca.getText().toString().toUpperCase().trim(),Toast.LENGTH_SHORT).show();
                    consultaVeiculo();

                }else {
                    Toast.makeText(getContext(),"Campo placa vazio",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }


    public void consultaVeiculo(){
        referencia = FirebaseDatabase.getInstance().getReference("veiculo");
        Query query = referencia;
        query.addValueEventListener(new ValueEventListener() {
        boolean flag;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                modelVeiculo consultaBD = new modelVeiculo();
                for(DataSnapshot postSnapshot :  dataSnapshot.getChildren()){
                   consultaBD = postSnapshot.getValue(modelVeiculo.class);

                    if(consultaBD.getPlaca().toUpperCase().trim().equals(consultaPlaca.getText().toString().toUpperCase().trim())){
                        veiculo = consultaBD;
                        consultaDono();
                       flag = true;
                        break;
                    }

                }
                if(flag != true){
                    Toast.makeText(getContext(),"Veículo não Encontrado",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

      //  consultaDono();

    }

    public void atualizaTexts(){
        if(veiculo != null){

            modeloVeiculo.setText(veiculo.getModelo());
            marcaVeiculo.setText(veiculo.getMarca());
            corVeiculo.setText(veiculo.getCor());
            celularDono.setText(usuario.getTelefone());
            emailDono.setText(usuario.getEmail());
        }
    }

    public void mascara(){

        SimpleMaskFormatter smf = new SimpleMaskFormatter("LLL-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(consultaPlaca,smf);
        consultaPlaca.addTextChangedListener(mtw);

    }

    public void consultaDono(){
        referencia = FirebaseDatabase.getInstance().getReference("users");
        Query query = referencia;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                modelUsuario consultaBd = new modelUsuario();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    consultaBd = postSnapshot.getValue(modelUsuario.class);
                    if(consultaBd.getUid().equals(veiculo.getUid())){
                        usuario = consultaBd;
                        atualizaTexts();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
