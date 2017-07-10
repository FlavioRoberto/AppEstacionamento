package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelVaga;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renna on 10/07/2017.
 */

public class vagaAdapter extends ArrayAdapter<modelVaga>{

    private ArrayList<modelVaga> vagas;
    private Context context;
    public vagaAdapter(@NonNull Context c, @NonNull ArrayList<modelVaga> objects) {
        super(c,0, objects);
        this.vagas = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if(vagas != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_vagas_ocupadas,parent,false);
            TextView vagaNumero = (TextView) view.findViewById(R.id.tv_numero);
            TextView vagaPlaca = (TextView) view.findViewById(R.id.tv_Placa);
            modelVaga vaga = vagas.get(position);

            vagaNumero.setText("Número: "+vaga.getNumero());
            vagaPlaca.setText("Placa: "+vaga.getPlacaVeiculo());
        }

        return view;
    }
}
