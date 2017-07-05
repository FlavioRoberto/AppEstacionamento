package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Garagista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GaragistaVeiculo extends Fragment {


    public GaragistaVeiculo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_garagista_veiculo, container, false);
    }

}
