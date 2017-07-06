package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.Base64Custom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PesquisaUsuarios {

    private String emailCodificado, emailDatabase;
    private Boolean flag = false;
    private DatabaseReference databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users");


    public PesquisaUsuarios() {
    }

    //Método que verifica se o email passado é valido
    public Boolean retornaUsuario(String email){
        emailCodificado = Base64Custom.codificarBase64(email);
            Query query = databaseReferenceUsers;
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        emailDatabase = postSnapshot.child("uid").getValue(String.class);
                        try {
                            if (emailDatabase.equals(emailCodificado)) {
                                flag = true;
                                break;
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        return flag;
    }
    //Fim do metodo

}
