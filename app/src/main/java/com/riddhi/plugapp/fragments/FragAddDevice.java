package com.riddhi.plugapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.riddhi.plugapp.R;
import com.riddhi.plugapp.framework.getdeviceconfig.DeviceConfigRequest;
import com.riddhi.plugapp.framework.getdeviceconfig.DeviceConfigResponse;
import com.riddhi.plugapp.helper.IRoidAppHelper;
import com.riddhi.plugapp.helper.PrefKeys;
import com.riddhi.plugapp.webapis.WebAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ridz1 on 30/07/2017.
 */

public class FragAddDevice extends Fragment implements View.OnClickListener {

    private ImageView imgAdd;

    private String placeId = "";

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_device, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        imgAdd = (ImageView) view.findViewById(R.id.imgAdd);

        imgAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgAdd) {

            gotoFragGetWifi();
            //callConfigDevice();
        }
    }

    private void gotoFragGetWifi() {
        FragWifiSetting fragment = new FragWifiSetting();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.containerMain, fragment, FragWifiSetting.class.getName());
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(FragWifiSetting.class.getName());
        ft.commit();


    }


}
