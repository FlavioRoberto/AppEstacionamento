package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.appestacionamento.cursoandroid.admin.appestacionamento.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
       // Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
       // startActivity(intent);

        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return true;


    }
}
