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
import android.widget.Toast;

import com.milky.R;
import com.milky.service.databaseutils.CustomersTableMagagement;
import com.milky.service.databaseutils.DatabaseHelper;
import com.milky.service.databaseutils.TableNames;
import com.milky.service.serverapi.HttpAsycTask;
import com.milky.service.serverapi.OnTaskCompleteListner;
import com.milky.service.serverapi.ServerApis;
import com.milky.ui.adapters.CustomersFragmentListAdapter;
import com.milky.ui.customers.CustomerAddActivity;
import com.milky.utils.AppUtil;
import com.milky.utils.Constants;
import com.milky.viewmodel.VCustomersList;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

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

            ((MainActivity) getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
                @Override
                public void onRefresh() {

                    if (MainActivity.selectedAreaId.equals("")) {
                        _mCustomersList = CustomersTableMagagement.getAllCustomers(_dbHelper.getReadableDatabase());
                        mTotalCustomers.setText(String.valueOf(_mCustomersList.size()) + " " + "Customers ");
                        _mAdapter = new CustomersFragmentListAdapter(getActivity(), _mCustomersList);
                        recList.setAdapter(_mAdapter);
                    } else {
                        _mCustomersList = CustomersTableMagagement.getAllCustomersByArea(_dbHelper.getReadableDatabase(), MainActivity.selectedAreaId);
                        mTotalCustomers.setText(String.valueOf(_mCustomersList.size()) + " " + "Customers ");
                        _mAdapter = new CustomersFragmentListAdapter(getActivity(), _mCustomersList);
                        recList.setAdapter(_mAdapter);
                    }

                }
            });

                _mCustomersList = CustomersTableMagagement.getAllCustomers(_dbHelper.getReadableDatabase());
                mTotalCustomers.setText(String.valueOf(_mCustomersList.size()) + " " + "Customers ");
                _mAdapter = new CustomersFragmentListAdapter(getActivity(), _mCustomersList);
                recList.setAdapter(_mAdapter);



        } else
            mTotalCustomers.setText(String.valueOf("No customer added yet !"));


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
                //check if global setting has been set
                if (!AppUtil.getInstance().getDatabaseHandler().isTableNotEmpty(TableNames.TABLE_GLOBAL_SETTINGS)) {
                    MainActivity.mDrawerLayout.openDrawer(MainActivity.mNavigationView);
                    Toast.makeText(getActivity(), getResources().getString(R.string.set_global_rate), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), CustomerAddActivity.class).putExtra("istoAddCustomer", true);
                    startActivity(intent);
                }
            }
        });
        mTotalCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity) getActivity()).SyncNow();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.customers_menu, menu);
    }


}
