package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class usuario_fragment extends Fragment {


    public usuario_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.usuario_fragment, container, false);
    }

}
