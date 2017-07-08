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


    private TextView Dados,Placaveiculo,Valorplacaveicuo,ModeloVeicuo,Valormodeloveicuo,Marcadoveiculo,Valormarcaveiculo,Cordoveiculo,Valorcorveiculo;
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
        Valorcorveiculo = (TextView) view.findViewById(R.id.valorcor_id);
        Cordoveiculo = (TextView) view.findViewById(R.id.textcor_id);
        Valormarcaveiculo = (TextView) view.findViewById(R.id.valormarca_id);
        Marcadoveiculo = (TextView) view.findViewById(R.id.textmarca_id);
        Valormodeloveicuo = (TextView) view.findViewById(R.id.valormodelo_id);
        ModeloVeicuo = (TextView) view.findViewById(R.id.textmodelo_id);
        Valorplacaveicuo = (TextView) view.findViewById(R.id.textovalor_placaId);
        Dados = (TextView) view.findViewById(R.id.textdados);
        Placaveiculo = (TextView) view.findViewById(R.id.placaVeiculoId);
        Traco = view.findViewById(R.id.viewbarra);




        return view;
    }

}
