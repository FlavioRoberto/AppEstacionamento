package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.FragmentGaragista;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista.BuscarVagaListaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista.GaragistaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista.ListaVagas_confirmarActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeGaragista extends Fragment {

    private ImageView botaopesquisa,confirmaEstacionamento;
    public HomeGaragista() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home_garagista, container, false);
        botaopesquisa = (ImageView) view.findViewById(R.id.pesquisaId);
        confirmaEstacionamento = (ImageView)view.findViewById(R.id.confirmaId) ;


        botaopesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BuscarVagaListaActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        confirmaEstacionamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListaVagas_confirmarActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((GaragistaActivity)getActivity()).onFragmentViewCreated(view);

    }



}
