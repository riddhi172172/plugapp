package com.riddhi.plugapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.riddhi.plugapp.R;
import com.riddhi.plugapp.framework.insertplace.InsertPlaceRequest;
import com.riddhi.plugapp.framework.insertplace.InsertPlaceResponse;
import com.riddhi.plugapp.framework.signup.SignupResponse;
import com.riddhi.plugapp.helper.IRoidAppHelper;
import com.riddhi.plugapp.helper.PrefKeys;
import com.riddhi.plugapp.webapis.WebAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ridz1 on 30/07/2017.
 */

public class FragAddHome extends FragBase implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_home, container, false);
        findViews(view);
        return view;
    }

    private EditText txtHome;
    private Button btnRequest;

    private void findViews(View view) {
        txtHome = (EditText) view.findViewById(R.id.txtHome);
        btnRequest = (Button) view.findViewById(R.id.btnRequest);

        btnRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnRequest) {
            callInsertPlace();
        }
    }

    private void callInsertPlace() {

        String placeName = txtHome.getText().toString().trim();

        if (placeName == null || placeName.isEmpty()) {
            txtHome.setError("*");
            return;
        }

        if (!IRoidAppHelper.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), R.string.err_no_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        InsertPlaceRequest request = new InsertPlaceRequest();
        request.setUser_id(Prefs.getString(PrefKeys.USER_ID, ""));
        request.setPlace_name(txtHome.getText().toString().trim());

        showLoader();

        WebAPIClient.getInstance(getActivity()).insert_place(request, new Callback<InsertPlaceResponse>() {
            @Override
            public void onResponse(Call<InsertPlaceResponse> call, Response<InsertPlaceResponse> response) {
                closeLoder();
                InsertPlaceResponse insertPlaceResponse = response.body();

                if (insertPlaceResponse != null) {
                    if (insertPlaceResponse.getFlag().equals("true")) {
                        gotoFragAddDevice(insertPlaceResponse.getData().getPlaceId());
                    }
                }
            }

            @Override
            public void onFailure(Call<InsertPlaceResponse> call, Throwable t) {
                closeLoder();
                t.printStackTrace();
            }
        });
    }

    private void gotoFragAddDevice(String placeId) {
        FragAddDevice fragment = new FragAddDevice();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.containerMain, fragment, FragAddDevice.class.getName());
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(FragAddDevice.class.getName());
        ft.commit();

    }


}
