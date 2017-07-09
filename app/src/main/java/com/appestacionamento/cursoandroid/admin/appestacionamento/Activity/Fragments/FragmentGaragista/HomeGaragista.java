package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.FragmentGaragista;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista.GaragistaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario.ActivityUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeGaragista extends Fragment {

    private ListView ListadeVagas;
    private SeekBar setores;
    private Button botaoBuscarSetor;
    private TextView setor;
    private String[] Vagas = {"Vaga 01","Vaga 02","Vaga 03","Vaga 04","Vaga 05","Vaga 06","Vaga07",
            "Vaga 08","Vaga 09","Vaga 10"};
    public HomeGaragista() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_garagista, container, false);
        ListadeVagas = (ListView) view.findViewById(R.id.ListviewId);
        setores = (SeekBar) view.findViewById(R.id.seekBarSetores);
        botaoBuscarSetor = (Button) view.findViewById(R.id.BuscarSetor);
        setor = (TextView) view.findViewById(R.id.Setor);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                Vagas
        );
        ListadeVagas.setAdapter(adapter);
        setores.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0){
                    setor.setText("Setor 1");
                }else
                    setor.setText("Setor 2");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((GaragistaActivity)getActivity()).onFragmentViewCreated(view);

    }
}
