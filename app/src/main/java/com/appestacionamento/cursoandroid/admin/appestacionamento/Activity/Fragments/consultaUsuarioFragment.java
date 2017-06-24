package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.consultaUsuarioActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.TelaAdm;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class consultaUsuarioFragment extends Fragment {

    private Button buttonCadastrarUsuario, buttonBuscar;
    private TextView textViewNome, textViewTelefone, textViewEmail, textViewTipo, textViewSituacao;
    private EditText editTextBusca;


    public consultaUsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment]
        View view =  inflater.inflate(R.layout.fragment_consulta_usuario, container, false);

        buttonCadastrarUsuario = (Button) view.findViewById(R.id.btnCadastrar);
        buttonBuscar = (Button) view.findViewById(R.id.btnconsulta);
        textViewNome = (TextView) view.findViewById(R.id.nomeId);
        textViewTelefone = (TextView) view.findViewById(R.id.telefoneId);
        textViewEmail = (TextView) view.findViewById(R.id.emailId);
        textViewTipo = (TextView) view.findViewById(R.id.tipoId);
        textViewSituacao = (TextView) view.findViewById(R.id.statusId);
        editTextBusca = (EditText) view.findViewById(R.id.consultaId);


        return view;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        ((TelaAdm) getActivity()).onFragmentViewCreated(view);

    }

}
