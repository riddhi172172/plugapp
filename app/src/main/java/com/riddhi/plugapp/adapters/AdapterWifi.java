package com.riddhi.plugapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.riddhi.plugapp.R;
import com.riddhi.plugapp.framework.getwifi.Network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ridz1 on 03/08/2017.
 */

public class AdapterWifi extends ArrayAdapter<Network> {

    private Context context;
    private ArrayList<Network> arrNetwork;

    public AdapterWifi(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Network> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrNetwork = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.cell_wifi, null);
            holder.unitView = (TextView) convertView.findViewById(R.id.lblWifi);





            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.unitView.setText(arrNetwork.get(position).getSsid());


        return convertView;

    }

    static class ViewHolder {
        TextView unitView;
        TextView quantityView;
    }
}
