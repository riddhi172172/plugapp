package com.riddhi.plugapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pixplicity.easyprefs.library.Prefs;
import com.riddhi.plugapp.fragments.FragAddHome;
import com.riddhi.plugapp.fragments.FragLogin;

public class ActMain extends AppCompatActivity {

    private ImageView imgLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);


        initHomeFragment();

        imgLogout = (ImageView) findViewById(R.id.imgLogout);

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.clear();
                startActivity(new Intent(ActMain.this, ActLogin.class));
                finish();
            }
        });
    }

    private void initHomeFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.containerMain, new FragAddHome()).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
