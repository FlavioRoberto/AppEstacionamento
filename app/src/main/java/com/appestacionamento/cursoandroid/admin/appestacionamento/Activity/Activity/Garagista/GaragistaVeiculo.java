package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GaragistaVeiculo extends Fragment {


    private TextView Dados,Placaveiculo,NumeroVaga,Valornumerovaga,Setor,Valorsetor,Nessecidade,ValorNessecidade;
    private EditText Consultaid;
    private ImageView BuscarVaga;
    private View Traco;
    public GaragistaVeiculo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_garagista_veiculo, container, false);
        BuscarVaga = (ImageView) view.findViewById(R.id.btnbuscar);
        Consultaid = (EditText) view.findViewById(R.id.editConsultaId);
        Setor = (TextView) view.findViewById(R.id.texto_Setor);
        Nessecidade = (TextView) view.findViewById(R.id.texto_Necessidade);
        ValorNessecidade = (TextView) view.findViewById(R.id.valorNecessidade_Especial);
        Valorsetor = (TextView) view.findViewById(R.id.valorSetor_Id);
        Valornumerovaga = (TextView) view.findViewById(R.id.valorNumero_Id);
        NumeroVaga = (TextView) view.findViewById(R.id.textView_12);
        Dados = (TextView) view.findViewById(R.id.textdados);
        Placaveiculo = (TextView) view.findViewById(R.id.placaVeiculoId);
        Traco = view.findViewById(R.id.viewbarra);




        return view;
    }

}
