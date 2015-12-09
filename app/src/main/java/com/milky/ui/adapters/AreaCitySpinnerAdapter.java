package com.milky.ui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.milky.R;
import com.milky.viewmodel.VAreaMapper;

import java.util.ArrayList;

/**
 * Created by Neha on 12/2/2015.
 */
public class AreaCitySpinnerAdapter extends ArrayAdapter<VAreaMapper> {
    private Activity _context;
    private ArrayList<VAreaMapper> dataList;

    public AreaCitySpinnerAdapter(Activity context, int resource, ArrayList<VAreaMapper> data) {
        super(context, resource, data);
        this._context = context;
        this.dataList = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {   // This view starts when we click the spinner.
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = _context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_layout, parent, false);
        }

        VAreaMapper item = dataList.get(position);

        if (item != null) {
            TextView area = (TextView) row.findViewById(R.id.spinnerText);

            area.setText(item.getCity());

        }

        return row;
    }
}

