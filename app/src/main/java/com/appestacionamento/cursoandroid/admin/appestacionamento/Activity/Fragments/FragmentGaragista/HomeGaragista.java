package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.FragmentGaragista;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista.GaragistaActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario.ActivityUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeGaragista extends Fragment {

    private ListView ListadeVagas;
    private String[] Vagas = {"Vaga 01","Vaga 02","Vaga 03","Vaga 04","Vaga 05"};
    public HomeGaragista() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_garagista, container, false);
        ListadeVagas = (ListView) view.findViewById(R.id.ListviewId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                Vagas
        );
        ListadeVagas.setAdapter(adapter);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((GaragistaActivity)getActivity()).onFragmentViewCreated(view);

    }
}
