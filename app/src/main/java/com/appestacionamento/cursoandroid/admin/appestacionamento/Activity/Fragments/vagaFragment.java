package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.CadastraVagaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.ConsultaVagaADMActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class vagaFragment extends Fragment {

    private ImageView btnCadastraVaga, btnConsultaVaga;

    public vagaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_vaga, container, false);

        btnCadastraVaga = (ImageView)view.findViewById(R.id.adicionarVagaId);
        btnConsultaVaga = (ImageView)view.findViewById(R.id.btnConsultaId);

        btnCadastraVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CadastraVagaActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        btnConsultaVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ConsultaVagaADMActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;


    }

}
