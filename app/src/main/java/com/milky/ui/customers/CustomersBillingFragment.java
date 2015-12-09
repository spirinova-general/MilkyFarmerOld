package com.milky.ui.customers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.milky.service.databaseutils.CustomersTableMagagement;
import com.milky.service.databaseutils.DatabaseHelper;
import com.milky.service.databaseutils.TableNames;
import com.milky.ui.adapters.CustomersListAdapter;
import com.milky.ui.main.BillingEdit;
import com.milky.utils.AppUtil;
import com.milky.viewmodel.VCustomersList;

import java.util.ArrayList;
import java.util.List;

import com.milky.R;

/**
 * Created by Neha on 11/20/2015.
 */
public class CustomersBillingFragment extends Fragment {
    private CustomersListAdapter _mAdapter;
    private List<VCustomersList> _mCustomersList ;
    public static ListView _mListView;
    private FloatingActionButton _mAddBillFab;
    private DatabaseHelper _dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_billing_list, container, false);
        initResources(view);
        return view;
    }

    private void initResources(View v) {
        _mListView = (ListView) v.findViewById(R.id.customersListView);
        _mAddBillFab = (FloatingActionButton) v.findViewById(R.id.addBillFab);
        _dbHelper = AppUtil.getInstance().getDatabaseHandler();
        _mAddBillFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BillingEdit.class)
                        .putExtra("fname",getActivity().getIntent().getStringExtra("fname"))
                        .putExtra("lname",getActivity().getIntent().getStringExtra("lname"));
                startActivity(i);
            }
        });
        if(_dbHelper.isTableNotEmpty(TableNames.TABLE_CUSTOMER))
        {


        }

//        _mCustomersList = new ArrayList<>();
//        for (int i = 0; i < _list.size(); i++) {
//            VCustomersList holder = new VCustomersList();
//            holder.setFirstName(getActivity().getIntent().getStringExtra("fname"));
//            holder.setStart_date(_list.get(i).getStart_date());
//            holder.setEnd_date(_list.get(i).getEnd_date());
//            holder.set_mQuantity(_list.get(i).get_mQuantity());
//            holder.setBalance_amount(_list.get(i).getBalance_amount());
//            holder.setArea(_list.get(i).getArea());
//            holder.setCity(_list.get(i).getCity());
//            holder.setAddress1(_list.get(i).getAddress1());
//            holder.setQuantity(_list.get(i).getQuantity());
//            holder.setAddress2(_list.get(i).getAddress2());
//            _mCustomersList.add(holder);
//
//        }
//
//        _mAdapter = new CustomersListAdapter(_mCustomersList, getActivity(), true);
//        _mListView.setAdapter(_mAdapter);

    }
}