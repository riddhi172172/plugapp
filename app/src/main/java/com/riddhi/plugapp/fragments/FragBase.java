package com.riddhi.plugapp.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.riddhi.plugapp.ActMain;
import com.riddhi.plugapp.helper.Loader;

/**
 * Created by ridz1 on 03/08/2017.
 */

public class FragBase extends Fragment {

    private ActMain root;
    private Loader loader;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        root = (ActMain) activity;
    }

    public void showLoader() {
        if (loader == null)
            loader = new Loader(root);

        loader.show();
    }

    public void closeLoder() {
        if (loader != null)
            loader.dismiss();
    }


}
