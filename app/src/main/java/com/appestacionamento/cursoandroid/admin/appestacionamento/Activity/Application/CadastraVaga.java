package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;


import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.*;

public class CadastraVaga {
    private Context context;
    private DatabaseReference databaseReference;
    private Boolean flag = true; //verifica se o numero que sera inserido pra vaga esta disponivel
    private String setorDatabase, numeroVagaDatabase, setorVaga, numeroVaga, chave;
    private Boolean vagaEspecial;
    private modelVaga modelVaga = new modelVaga();

    public CadastraVaga(Context context) {
        this.context = context;
    }

    public void insereVaga(String numero, String setor, Boolean vagaEsp){
        setorVaga = setor;
        numeroVaga = numero;
        vagaEspecial = vagaEsp;
        databaseReference = FirebaseDatabase.getInstance().getReference("vaga");
        Query query = databaseReference;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSanpshot : dataSnapshot.getChildren()){
                    setorDatabase = postSanpshot.child("setor").getValue(String.class);
                    numeroVagaDatabase = postSanpshot.child("numero").getValue(String.class);
                    if(setorDatabase.equals(setorVaga) && numeroVagaDatabase.equals(numeroVaga)){
                        flag = false;
                        break;
                    }
                }

                if(flag == true){
                    chave = databaseReference.push().getKey();
                    modelVaga.setVagaEspecial(vagaEspecial);
                    modelVaga.setNumero(numeroVaga);
                    modelVaga.setSetor(setorVaga);
                    modelVaga.setChave(chave);
                    modelVaga.setStatus("LIVRE");
                    modelVaga.create();
                    Toast.makeText(context, "Vaga cadastrada com sucesso!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, "Numero de vaga ja esta em uso", Toast.LENGTH_LONG).show();
                    flag=true;
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
