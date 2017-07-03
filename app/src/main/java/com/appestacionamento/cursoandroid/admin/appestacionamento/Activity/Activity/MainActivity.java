package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Adapter.TabAdapter;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application.configuracaoFirebase;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Helper.SlidingTabLayout;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Model.modelUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
