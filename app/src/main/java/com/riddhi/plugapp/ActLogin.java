package com.riddhi.plugapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.riddhi.plugapp.fragments.FragLogin;
import com.riddhi.plugapp.fragments.FragSignup;

public class ActLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        initHomeFragment();
    }

    private void initHomeFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragContainer, new FragLogin()).commit();
    }
}
