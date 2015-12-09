package com.milky.ui.customers;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.milky.service.databaseutils.ConsumptionCustomersTableManagement;
import com.milky.service.databaseutils.CustomersTableMagagement;
import com.milky.service.databaseutils.DatabaseHelper;
import com.milky.service.databaseutils.TableNames;
import com.milky.ui.adapters.GlobalDeliveryAdapter;
import com.milky.utils.AppUtil;
import com.milky.utils.Constants;
import com.milky.viewmodel.VCustomersList;

import java.text.DateFormatSymbols;
import java.util.List;

import com.milky.R;

public class CustomersList extends AppCompatActivity {
    private ListView _mCustomers;
    private int _mDay;
    private String _mMonth;
    private Intent _mIntent;
    private Toolbar _mToolbar;
    public static List<VCustomersList> _mCustomersList;
    private GlobalDeliveryAdapter _mAdaapter;
    private Button _save, _cancel;
    private DatabaseHelper _dbHelper;
    private LinearLayout _bottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list);
        initResources();

    }

    private void initResources() {
        _mCustomers = (ListView) findViewById(R.id.customersList);
        _save = (Button) findViewById(R.id.save);
        _cancel = (Button) findViewById(R.id.cancel);
        _bottomLayout = (LinearLayout) findViewById(R.id.bottom_Layout);
        _mIntent = this.getIntent();
        String month = new DateFormatSymbols().getMonths()[(Constants.SELECTED_DAY).getMonth()];
        _mDay = Constants.SELECTED_DAY.getDay();
        _mMonth = month;
        setActionBar();
        _dbHelper = AppUtil.getInstance().getDatabaseHandler();
        if (_dbHelper.isTableNotEmpty(TableNames.TABLE_CUSTOMER)) {
            _mCustomersList = CustomersTableMagagement.getAllCustomers(_dbHelper.getReadableDatabase());
            _mAdaapter = new GlobalDeliveryAdapter(this, String.valueOf(Constants.SELECTED_DAY));
            _mCustomers.setItemsCanFocus(true);
            _mCustomers.setAdapter(_mAdaapter);
        }
        if ((_mCustomersList == null || _mCustomersList.size() == 0))
            _bottomLayout.setVisibility(View.GONE);
        _dbHelper.close();

    }

    private void setActionBar() {
        _mToolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(_mToolbar);
        _mToolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(String.valueOf(_mDay) + " " + _mMonth);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.customers_menu, menu);
        menu.findItem(R.id.action_add_bill).setVisible(false);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:

                for (int i = 0; i < _mCustomersList.size(); i++) {
//                    CustomersTableMagagement.updateQuantity(_dbHelper.getWritableDatabase(), _mCustomersList.get(i));
                    if (ConsumptionCustomersTableManagement.isHasData(Constants.QUANTITY_UPDATED_DATE, _dbHelper.getReadableDatabase(),_mCustomersList.get(i).getCustomerId()))
                        ConsumptionCustomersTableManagement.updateCustomerDetail(_dbHelper.getWritableDatabase(), _mCustomersList.get(i));
                    else
                        ConsumptionCustomersTableManagement.insertCustomerDetail(_dbHelper.getWritableDatabase(), _mCustomersList.get(i));
                }
                finish();

                break;

            case R.id.cancel:
                finish();
                break;
        }
    }
}
