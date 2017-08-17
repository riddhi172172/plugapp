package com.riddhi.plugapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pixplicity.easyprefs.library.Prefs;
import com.riddhi.plugapp.R;
import com.riddhi.plugapp.adapters.AdapterWifi;
import com.riddhi.plugapp.framework.getdeviceconfig.DeviceConfigRequest;
import com.riddhi.plugapp.framework.getdeviceconfig.DeviceConfigResponse;
import com.riddhi.plugapp.framework.getwifi.GetWifiResponse;
import com.riddhi.plugapp.framework.getwifi.Network;
import com.riddhi.plugapp.framework.savewifi.SaveWifiRequest;
import com.riddhi.plugapp.framework.savewifi.SaveWifiResponse;
import com.riddhi.plugapp.helper.IRoidAppHelper;
import com.riddhi.plugapp.helper.PrefKeys;
import com.riddhi.plugapp.webapis.WebAPIClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ridz1 on 02/08/2017.
 */

public class FragAddWifi extends FragBase implements View.OnClickListener {

    private LinearLayout lytWifi;
    private EditText txtWifiName;
    private EditText txtPassword;
    private Button btnRequest;

    private String placeId = "";
    private String server = "";
    private String port = "";

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    private ArrayList<Network> arrWifi = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_wifi, container, false);

        findViews(view);

        callConfigDevice();

        callGetWifi();

        return view;
    }


    private void findViews(View view) {
        lytWifi = (LinearLayout) view.findViewById(R.id.lytWifi);
        txtWifiName = (EditText) view.findViewById(R.id.txtWifiName);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        btnRequest = (Button) view.findViewById(R.id.btnRequest);

        btnRequest.setOnClickListener(this);
        txtWifiName.setOnClickListener(this);
        lytWifi.setOnClickListener(this);
    }

    private void callConfigDevice() {

        if (!IRoidAppHelper.isNetworkAvailable(getActivity())) {
            IRoidAppHelper.showNetworkError(getActivity());
            return;
        }

        DeviceConfigRequest request = new DeviceConfigRequest();
        request.setUser_id(Prefs.getString(PrefKeys.USER_ID, ""));
        request.setPlace_id(placeId);

        WebAPIClient.getInstance(getActivity()).get_device_config(request, new Callback<DeviceConfigResponse>() {
            @Override
            public void onResponse(Call<DeviceConfigResponse> call, Response<DeviceConfigResponse> response) {
                DeviceConfigResponse deviceConfigResponse = response.body();
                if (deviceConfigResponse != null) {

                    if (deviceConfigResponse.getFlag().equals("true")) {
                        server = deviceConfigResponse.getData().getHost() + "";
                        port = deviceConfigResponse.getData().getPort() + "";
                    }

                } else IRoidAppHelper.showFlagFalseError(getActivity());
            }

            @Override
            public void onFailure(Call<DeviceConfigResponse> call, Throwable t) {

            }
        });

    }

    private void callGetWifi() {

        if (!IRoidAppHelper.isNetworkAvailable(getActivity())) {
            IRoidAppHelper.showNetworkError(getActivity());
            return;
        }

        showLoader();

        WebAPIClient.getInstance(getActivity()).getWifiNames(new Callback<GetWifiResponse>() {
            @Override
            public void onResponse(Call<GetWifiResponse> call, Response<GetWifiResponse> response) {
                closeLoder();
                GetWifiResponse getWifiResponse = response.body();
                if (getWifiResponse != null) {

                    if (getWifiResponse.getFlag().equals("true")) {
                        if (getWifiResponse.getNetworks() != null && getWifiResponse.getNetworks().size() > 0) {
                            arrWifi.addAll(getWifiResponse.getNetworks());
                        }
                    }

                } else IRoidAppHelper.showFlagFalseError(getActivity());
            }

            @Override
            public void onFailure(Call<GetWifiResponse> call, Throwable t) {
                closeLoder();
                t.printStackTrace();
            }
        });

    }

    String selectedWifi = "";

    private void showDialog(final ArrayList<Network> arrNetwork) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.lyt_dialog);
        dialog.setTitle("Select Wifi");
        ListView lv = (ListView) dialog.findViewById(R.id.lstWifi);

        // Change MyActivity.this and myListOfItems to your own values
        AdapterWifi adapterWifi = new AdapterWifi(getActivity(), 0, arrNetwork);

        lv.setAdapter(adapterWifi);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedWifi = arrNetwork.get(i).getSsid() + "";
                txtWifiName.setText(selectedWifi);
                dialog.dismiss();

            }
        });


        dialog.show();

    }

    @Override
    public void onClick(View v) {
        if (v == btnRequest) {

            validateData();

        } else if (v == lytWifi || v == txtWifiName) {
            showDialog(arrWifi);
        }
    }

    private void validateData() {

        String pass = txtPassword.getText().toString().trim() + "";

        if (selectedWifi.isEmpty()) {
            txtWifiName.setError("*");
            return;
        }
        if (pass.isEmpty()) {
            txtPassword.setError("*");
            return;
        }

        SaveWifiRequest request = new SaveWifiRequest();
        request.setS(selectedWifi);
        request.setP(pass);
        request.setPort(port);
        request.setServer(server);
        request.setUser(Prefs.getString(PrefKeys.USER_ID, ""));
        request.setPswd("");

        WebAPIClient.getInstance(getActivity()).saveWifi(request, new Callback<SaveWifiResponse>() {
            @Override
            public void onResponse(Call<SaveWifiResponse> call, Response<SaveWifiResponse> response) {

            }

            @Override
            public void onFailure(Call<SaveWifiResponse> call, Throwable t) {

            }
        });
    }
}