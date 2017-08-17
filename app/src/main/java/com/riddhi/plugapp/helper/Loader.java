package com.riddhi.plugapp.helper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.riddhi.plugapp.R;

/**
 * Created by ridz1 on 25/07/2017.
 */

public class Loader extends Dialog {
    public Loader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);

    }
}
