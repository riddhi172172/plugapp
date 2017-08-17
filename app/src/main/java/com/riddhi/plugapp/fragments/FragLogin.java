package com.riddhi.plugapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.riddhi.plugapp.ActMain;
import com.riddhi.plugapp.R;
import com.riddhi.plugapp.framework.login.LoginRequest;
import com.riddhi.plugapp.framework.login.LoginResponse;
import com.riddhi.plugapp.helper.IRoidAppHelper;
import com.riddhi.plugapp.helper.Loader;
import com.riddhi.plugapp.helper.PrefKeys;
import com.riddhi.plugapp.webapis.WebAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ridz1 on 24/07/2017.
 */

public class FragLogin extends Fragment implements View.OnClickListener {


    private EditText txtEmail;
    private EditText txtPassword;
    private TextView lblForgotPassword;
    private Button btnSignup;
    private Button btnRegister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_login, container, false);
        findViews(view);
        return view;
    }


    private void findViews(View view) {
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        lblForgotPassword = (TextView) view.findViewById(R.id.lblForgotPassword);
        btnSignup = (Button) view.findViewById(R.id.btnSignup);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
        lblForgotPassword.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnSignup) {
            validateData();
        } else if (v == btnRegister) {
            gotoFragRegister();
        } else if (v == lblForgotPassword) {
            gotoFragForgotPassword();
        }
    }

    private void gotoFragForgotPassword() {
        FragForgotPassword fragment = new FragForgotPassword();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragContainer, fragment, FragForgotPassword.class.getName());
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(FragForgotPassword.class.getName());
    //    ft.commit();
        ft.commit();

    }

    private void gotoFragRegister() {

        FragSignup fragment = new FragSignup();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragContainer, fragment, FragSignup.class.getName());
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_out_left);
        ft.addToBackStack(FragSignup.class.getName());
        ft.commit();
    }

    private void gotoFragAddHome() {

        FragAddHome fragment = new FragAddHome();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(FragAddHome.class.getName());
        ft.add(R.id.fragContainer, fragment, FragAddHome.class.getName());
        ft.commit();
    }

    private void validateData() {

        String email = txtEmail.getText().toString().trim();
        String pass = txtPassword.getText().toString().trim();

        boolean bemail = IRoidAppHelper.validateEdt(txtEmail);
        boolean bpass = IRoidAppHelper.validateEdt(txtPassword);

        if (bemail && bpass) {

            LoginRequest request = new LoginRequest();
            request.setMail(email);
            request.setPswd(pass);


            callLogin(request);

        }


    }

    private void callLogin(LoginRequest request) {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.isIndeterminate();


        if (!IRoidAppHelper.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), "No internet connected", Toast.LENGTH_SHORT).show();
            return;

        }

        final Loader loader = new Loader(getActivity());
        loader.show();

        WebAPIClient.getInstance(getActivity()).login(request, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loader.dismiss();
                LoginResponse signupResponse = response.body();
                pd.dismiss();
                if (signupResponse!=null) {
                    if (signupResponse.getFlag().equals("true")) {

                        Prefs.putString(PrefKeys.USER_ID, signupResponse.getData().getUserId());
                        startActivity(new Intent(getActivity(), ActMain.class));
                        getActivity().finish();
                    }

                    Toast.makeText(getActivity(), signupResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loader.dismiss();
                t.printStackTrace();
            }
        });
    }

}
