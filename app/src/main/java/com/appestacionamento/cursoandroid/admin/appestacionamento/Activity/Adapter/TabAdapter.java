package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.usuarioAdmin;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.vagaFragment;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.consultaUsuarioFragment;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.veiculoFragment;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;

/**
 * Created by admin on 20/06/2017.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private  String usuario ;
    private String[] abas;

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(!getUsuario().isEmpty()) {

        }
        if (getUsuario().equals("SECRETARIA")) {
            switch (position) {
                case 0:
                    fragment = new usuarioAdmin();
                    return fragment;

                case 1:
                    fragment = new veiculoFragment();
                    return fragment;
            }
        }

            if (getUsuario().equals("ADM")) {
                switch (position) {
                    case 0:
                        fragment = new usuarioAdmin();
                        return fragment;
                    case 1:
                        fragment = new vagaFragment();
                        return fragment;
                    case 2:
                        fragment = new veiculoFragment();
                        return fragment;
                }

            }


       // fragment = new usuarioAdmin();
        return fragment ;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String[] getAbas() {
        return abas;
    }

    public void setAbas(String[] abas) {
        this.abas = abas;
    }

    @Override
    public int getCount() {
        return abas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return abas[position];
    }
}
