package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Admin.AdmActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.CadastroVeicuoActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.ConsultarVeiculoActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Secretaria.SecretariaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.Preferencias;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class veiculoFragment extends Fragment {

    private ImageView btnCadastraVeiculo;
    private ImageView exluirVeiculo;


    public veiculoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       final View view = inflater.inflate(R.layout.fragment_veiculo, container, false);

        btnCadastraVeiculo = (ImageView)view.findViewById(R.id.btncadastroVeiculoId);
        exluirVeiculo = (ImageView)view.findViewById(R.id.btnExcluirVeiculoId);

        //ao clicar chama tela cria veiculo
        btnCadastraVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CadastroVeicuoActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //ao clicar chama tela excluir veiculo
        exluirVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConsultarVeiculoActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Preferencias preferencias = new Preferencias(getContext());
        if(preferencias.recuperaTipo(getContext()).equals("ADM")) {
            ((AdmActivity) getActivity()).onFragmentViewCreated(view);
        }else{
            ((SecretariaActivity) getActivity()).onFragmentViewCreated(view);
        }

    }

    @Override
    public void onDestroy() {
        getActivity().finish();
        super.onDestroy();
    }
}
