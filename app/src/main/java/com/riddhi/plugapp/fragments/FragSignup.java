package com.riddhi.plugapp.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.riddhi.plugapp.ActMain;
import com.riddhi.plugapp.R;
import com.riddhi.plugapp.framework.signup.SignupRequest;
import com.riddhi.plugapp.framework.signup.SignupResponse;
import com.riddhi.plugapp.helper.IRoidAppHelper;
import com.riddhi.plugapp.helper.Loader;
import com.riddhi.plugapp.helper.PrefKeys;
import com.riddhi.plugapp.webapis.WebAPIClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ridz1 on 23/07/2017.
 */

public class FragSignup extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_signup, container, false);
        findViews(view);
        return view;
    }

    Calendar myCalendar = Calendar.getInstance();
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtRePassword;
    private EditText txtDob;
    private Button btnSignup;


    private void findViews(View view) {
        txtName = (EditText) view.findViewById(R.id.txtName);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        txtRePassword = (EditText) view.findViewById(R.id.txtRePassword);
        txtDob = (EditText) view.findViewById(R.id.txtDob);
        btnSignup = (Button) view.findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(this);
        txtDob.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnSignup) {
            validateData();
        } else if (v == txtDob) {
            showDatePicker();
        }
    }

    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void validateData() {

        String fullname = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String pass = txtPassword.getText().toString().trim();
        String confirmPass = txtRePassword.getText().toString().trim();
        String dob = txtDob.getText().toString().trim();

        boolean bfname = IRoidAppHelper.validateEdt(txtName);
        boolean bemail = IRoidAppHelper.validateEdt(txtEmail);
        boolean bpass = IRoidAppHelper.validateEdt(txtPassword);
        boolean bconfirmpass = IRoidAppHelper.validateEdt(txtRePassword);
        boolean bdob = IRoidAppHelper.validateEdt(txtDob);

        if (bfname && bemail && bpass && bconfirmpass && bdob) {
            if (pass.equals(confirmPass)) {

                SignupRequest request = new SignupRequest();
                request.setFull_name(fullname);
                request.setMail(email);
                request.setPswd(pass);
                request.setBd(dob);


                callSignUp(request);
            } else
                Toast.makeText(getActivity(), "Confirm password wrong!", Toast.LENGTH_SHORT).show();
        }


    }

    private void callSignUp(SignupRequest request) {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.isIndeterminate();


        if (!IRoidAppHelper.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), "No internet connected", Toast.LENGTH_SHORT).show();
            return;

        }

        final Loader loader = new Loader(getActivity());
        loader.show();

        WebAPIClient.getInstance(getActivity()).signup(request, new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                loader.dismiss();
                SignupResponse signupResponse = response.body();
                if (signupResponse.getFlag().equals("true")) {
                    startActivity(new Intent(getActivity(), ActMain.class));
                    Prefs.putString(PrefKeys.USER_ID, signupResponse.getData().getUserId());
                    Prefs.putString(PrefKeys.NAME, signupResponse.getData().getFullName());
                    getActivity().finish();
                }

                Toast.makeText(getActivity(), signupResponse.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                loader.dismiss();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        myCalendar.set(Calendar.YEAR, i);
        myCalendar.set(Calendar.MONTH, i1);
        myCalendar.set(Calendar.DAY_OF_MONTH, i2);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtDob.setText(sdf.format(myCalendar.getTime()));
    }
}
