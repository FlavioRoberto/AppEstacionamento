package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.vagaFragment;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Fragments.consultaUsuarioFragment;

/**
 * Created by admin on 20/06/2017.
 */

public class TabAdapter extends FragmentPagerAdapter {


    private String[] abas  = {"USUARIO","VAGAS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new consultaUsuarioFragment();return fragment;
            case 1:fragment = new vagaFragment();return fragment;
        }

        return fragment;
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