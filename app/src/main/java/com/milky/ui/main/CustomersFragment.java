package com.milky.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.milky.service.databaseutils.CustomersTableMagagement;
import com.milky.service.databaseutils.DatabaseHelper;
import com.milky.service.databaseutils.TableNames;
import com.milky.ui.adapters.CustomersFragmentListAdapter;
import com.milky.ui.customers.CustomerAddActivity;
import com.milky.utils.AppUtil;
import com.milky.viewmodel.VCustomersList;

import java.util.List;

import com.milky.R;

/**
 * Created by Neha on 11/17/2015.
 */
public class CustomersFragment extends Fragment {

    private List<VCustomersList> _mCustomersList;
    private CustomersFragmentListAdapter _mAdapter;
    private FloatingActionButton mFab;
    private TextView mTotalCustomers;
    private DatabaseHelper _dbHelper;
    private RecyclerView recList;


    @Override
    public void onResume() {
        super.onResume();
        if (_dbHelper.isTableNotEmpty(TableNames.TABLE_CUSTOMER)) {
            _mCustomersList = CustomersTableMagagement.getAllCustomers(_dbHelper.getReadableDatabase());
            mTotalCustomers.setText(String.valueOf(_mCustomersList.size()) + " " + "Customers ");
            _mAdapter = new CustomersFragmentListAdapter(getActivity(), _mCustomersList);
            recList.setAdapter(_mAdapter);
        } else
            mTotalCustomers.setText(String.valueOf("0" + " " + "Customers "));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.customers_fragment_layout, null);
        initResources(view);
        return view;
    }

    private void initResources(View view) {
        recList = (RecyclerView) view.findViewById(R.id.customersListView);
        recList.setHasFixedSize(true);
        _dbHelper = AppUtil.getInstance().getDatabaseHandler();
        mTotalCustomers = (TextView) view.findViewById(R.id.totalCustomers);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
//        _mCustomersList = GetCustomersListFromAssets.GetCustomersList(getActivity());


        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerAddActivity.class).putExtra("istoAddCustomer", true);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.customers_menu, menu);
    }
}
