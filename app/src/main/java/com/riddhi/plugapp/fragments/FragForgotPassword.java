package com.riddhi.plugapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riddhi.plugapp.R;

/**
 * Created by ridz1 on 25/07/2017.
 */

public class FragForgotPassword extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_forgot_password, container, false);
        return view;
    }
}
