package com.riddhi.plugapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pixplicity.easyprefs.library.Prefs;
import com.riddhi.plugapp.helper.IRoidAppHelper;
import com.riddhi.plugapp.helper.PrefKeys;

public class ActSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        initSplash();
    }


    private void initSplash() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (IRoidAppHelper.isStringValid(Prefs.getString(PrefKeys.USER_ID, "")))
                    startActivity(new Intent(ActSplash.this, ActMain.class));
                else
                    startActivity(new Intent(ActSplash.this, ActLogin.class));

                finish();
            }
        }, 3000);
    }
}
