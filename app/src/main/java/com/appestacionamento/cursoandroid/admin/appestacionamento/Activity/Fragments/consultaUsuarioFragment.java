package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.AdmActivity;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class consultaUsuarioFragment extends Fragment {

    private ImageView buttonBuscar;
    private Button  buttonEditar;
    private TextView textViewNome, textViewTelefone, textViewEmail, textViewTipo, textViewCpf;
    private EditText editTextBusca;


    public consultaUsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consulta_usuario, container, false);


        buttonBuscar = (ImageView) view.findViewById(R.id.btnbuscar);
        buttonEditar = (Button) view.findViewById(R.id.btnEditar);
        textViewNome = (TextView) view.findViewById(R.id.valorNomeId);
        textViewTelefone = (TextView) view.findViewById(R.id.valorCelularid);
        textViewEmail = (TextView) view.findViewById(R.id.emailId);
        textViewTipo = (TextView) view.findViewById(R.id.valorTipoid);
        textViewCpf = (TextView) view.findViewById(R.id.valorCpfid);
        editTextBusca = (EditText) view.findViewById(R.id.editConsultaId);


        return view;

    }
    
}
