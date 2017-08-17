package com.riddhi.plugapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.riddhi.plugapp.R;
import com.riddhi.plugapp.framework.mikecheck.MikeCheckResponse;
import com.riddhi.plugapp.helper.Loader;
import com.riddhi.plugapp.webapis.WebAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ridz1 on 02/08/2017.
 */

public class FragWifiSetting extends Fragment implements View.OnClickListener {

    private Button btnGoToSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_wifi_setting, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        mikeCheck();
    }

    private void mikeCheck() {

        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mWifi.isConnected()) {
            return;
        }

        final Loader loader = new Loader(getActivity());
        loader.show();

        WebAPIClient.getInstance(getActivity()).mikeCheck(new Callback<MikeCheckResponse>() {
            @Override
            public void onResponse(Call<MikeCheckResponse> call, Response<MikeCheckResponse> response) {
                loader.dismiss();
                MikeCheckResponse mikeCheckResponse = response.body();

                if (mikeCheckResponse != null) {
                    if (mikeCheckResponse.getFlag().equals("true")) {
                        gotoFragAddWifi();
                    }
                }
            }

            @Override
            public void onFailure(Call<MikeCheckResponse> call, Throwable t) {
                loader.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void gotoFragAddWifi() {

        FragAddWifi fragment = new FragAddWifi();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.containerMain, fragment, FragAddWifi.class.getName());
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(FragAddWifi.class.getName());
        ft.commit();
    }

    private void findViews(View view) {
        btnGoToSetting = (Button) view.findViewById(R.id.btnGoToSetting);

        btnGoToSetting.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnGoToSetting) {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }
    }

}
