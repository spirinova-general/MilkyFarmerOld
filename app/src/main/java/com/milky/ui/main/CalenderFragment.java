package com.milky.ui.main;

import android.content.Intent;
import android.os.Bundle;

import java.text.DateFormatSymbols;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.milky.ui.customers.CustomersList;
import com.milky.utils.Constants;
import com.tyczj.extendedcalendarview.Day;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

import com.milky.R;

/**
 * Created by Neha on 11/17/2015.
 */
public class CalenderFragment extends Fragment {
    private ExtendedCalendarView _mCalenderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.calender_layout, container, false);
        _mCalenderView = (ExtendedCalendarView) view.findViewById(R.id.calendar);
        initResources();
        return view;

    }

    private void initResources() {
        _mCalenderView.setQuantity("10L");
        _mCalenderView.setOnDayClickListener(new ExtendedCalendarView.OnDayClickListener() {
            @Override
            public void onDayClicked(AdapterView<?> adapterView, View view, int i, long l, Day day) {
                Constants.SELECTED_DAY = day;
                Constants.QUANTITY_UPDATED_DATE = String.valueOf(day.getDay()) + String.valueOf(day.getMonth()) + String.valueOf(day.getYear());
                Intent intent = new Intent(getActivity(), CustomersList.class);
                startActivity(intent);
            }
        });
    }
}
