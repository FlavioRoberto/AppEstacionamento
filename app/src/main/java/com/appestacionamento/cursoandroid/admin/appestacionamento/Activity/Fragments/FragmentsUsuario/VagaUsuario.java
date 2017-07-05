package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.FragmentsUsuario;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario.ActivityUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VagaUsuario extends Fragment {


    public VagaUsuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaga_usuario, container, false);
    }


}
