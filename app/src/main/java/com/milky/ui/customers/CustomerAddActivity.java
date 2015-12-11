package com.milky.ui.customers;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.milky.R;
import com.milky.service.databaseutils.AreaMapTableManagement;
import com.milky.service.databaseutils.CustomersTableMagagement;
import com.milky.service.databaseutils.DatabaseHelper;
import com.milky.service.databaseutils.GlobalSettingTableManagement;
import com.milky.service.databaseutils.TableNames;
import com.milky.ui.adapters.AreaCityAdapter;
import com.milky.ui.adapters.AutocompleteAreaArrayAdapter;
import com.milky.utils.AppUtil;
import com.milky.utils.Constants;
import com.milky.utils.TextValidationMessage;
import com.milky.viewmodel.VAreaMapper;
import com.milky.viewmodel.VCustomersList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomerAddActivity extends AppCompatActivity {
    private Toolbar _mToolbar;
    private EditText _firstName;

    private EditText _lastName;

    private EditText _mAddress1;

    private EditText _address2;
    private EditText _rate;
    private EditText _mPhone;
    private EditText _mBalance;
    private EditText _mQuantuty;
    private Button _mSave, _mCancel;
    private LinearLayout _mBottomLayout;
    private int dataCount = 0;
    private CoordinatorLayout _mCoordinatorLayout;
    private AutoCompleteTextView _cityAreaAutocomplete;
    private DatabaseHelper _dbHelper;
    private TextInputLayout name_layout, rate_layout, balance_layout, autocomplete_layout, last_name_layout, flat_number_layout, street_layout, milk_quantity_layout, _phone_textinput_layout;
    private String selectedCityId = "", selectedAreaId = "";
    private ArrayList<VAreaMapper> _areaList, _areacityList = new ArrayList<>();
    private String[] autoCompleteData;
    private Calendar c;
    private AreaCityAdapter adapter1;
    private AutocompleteAreaArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_customer_add);

        initResources();
        setActionBar();

      /*
        * Set text field listeners*/
        _mAddress1.addTextChangedListener(new TextValidationMessage(flat_number_layout, this, false));
        _firstName.addTextChangedListener(new TextValidationMessage(name_layout, this, false));
        _mQuantuty.addTextChangedListener(new TextValidationMessage(milk_quantity_layout, this, false));
        _mBalance.addTextChangedListener(new TextValidationMessage(balance_layout, this, false));
        _mPhone.addTextChangedListener(new TextValidationMessage(_phone_textinput_layout, this, true));
        _address2.addTextChangedListener(new TextValidationMessage(street_layout, this, false));
        _lastName.addTextChangedListener(new TextValidationMessage(last_name_layout, this, false));
        _cityAreaAutocomplete.addTextChangedListener(new TextValidationMessage(autocomplete_layout, this, false));
        _rate.addTextChangedListener(new TextValidationMessage(rate_layout, this, false));

    }

    String formattedDate;

    private void setActionBar() {
        _mToolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(_mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        formattedDate = df.format(c.getTime());
        getSupportActionBar().setSubtitle(formattedDate);
         /*
        * Set Custome action bar
        * */
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar_layout, null);
        TextView title = (TextView) mCustomView.findViewById(R.id.title);
        TextView subTitle = (TextView) mCustomView.findViewById(R.id.date);
        title.setText("Add Customer");
        subTitle.setText(formattedDate);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        getMenuInflater().inflate(R.menu.delete_customer_menu, menu);

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

    private void initResources() {
        _firstName = (EditText) findViewById(R.id.first_name);
        _lastName = (EditText) findViewById(R.id.last_name);
        _mAddress1 = (EditText) findViewById(R.id.flat_number);
        _address2 = (EditText) findViewById(R.id.street);
        _mPhone = (EditText) findViewById(R.id.phone);
        _mQuantuty = (EditText) findViewById(R.id.milk_quantity);
        _mBalance = (EditText) findViewById(R.id.balance);
        _mSave = (Button) findViewById(R.id.save);
        _mCancel = (Button) findViewById(R.id.cancel);
        _mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        _mBottomLayout = (LinearLayout) findViewById(R.id.bottomLayout);
        _mBottomLayout.setVisibility(View.VISIBLE);
        _rate = (EditText) findViewById(R.id.rate);
        _mCancel.setVisibility(View.GONE);

        _dbHelper = AppUtil.getInstance().getDatabaseHandler();
        _cityAreaAutocomplete = (AutoCompleteTextView) findViewById(R.id.autocomplete_city_area);
        /*Set defaul rate
        * */
        if (_dbHelper.isTableNotEmpty(TableNames.TABLE_GLOBAL_SETTINGS))
            _rate.setText(GlobalSettingTableManagement.getDefaultRate(_dbHelper.getReadableDatabase()));

        _phone_textinput_layout = (TextInputLayout) findViewById(R.id.phone_textinput_layout);
        rate_layout = (TextInputLayout) findViewById(R.id.rate_layout);
        name_layout = (TextInputLayout) findViewById(R.id.name_layout);
        last_name_layout = (TextInputLayout) findViewById(R.id.last_name_layout);
        street_layout = (TextInputLayout) findViewById(R.id.street_layout);
        balance_layout = (TextInputLayout) findViewById(R.id.balance_layout);
        flat_number_layout = (TextInputLayout) findViewById(R.id.flat_number_layout);
        autocomplete_layout = (TextInputLayout) findViewById(R.id.autocomplete_layout);
        milk_quantity_layout = (TextInputLayout) findViewById(R.id.milk_quantity_layout);
        _areaList = AreaMapTableManagement.getAreaById(_dbHelper.getReadableDatabase(), Constants.ACCOUNT_ID);
        autoCompleteData = new String[_areaList.size()];
        for (int i = 0; i < _areaList.size(); i++) {
            VAreaMapper areacity = new VAreaMapper();
            areacity.setArea(_areaList.get(i).getArea());
            areacity.setAreaId(_areaList.get(i).getAreaId());
            areacity.setCityId(_areaList.get(i).getCityId());
            areacity.setCity(AreaMapTableManagement.getCityNameById(_dbHelper.getReadableDatabase(), _areaList.get(i).getCityId()));
            areacity.setCityArea(areacity.getArea() + areacity.getCity());
            _areacityList.add(areacity);


        }
        //  final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoCompleteData);
        _cityAreaAutocomplete.setThreshold(1);//will start working from first character
       /* _cityAreaAutocomplete.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                adapter.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });*/
        AreaCityAdapter adapter1 = new AreaCityAdapter(this, 0, R.id.te1, _areacityList);
        _cityAreaAutocomplete.setAdapter(adapter1);
        //_cityAreaAutocomplete.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        _cityAreaAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _cityAreaAutocomplete.setText(_areacityList.get(position).getArea() + ", " + _areacityList.get(position).getCity());
                selectedAreaId = _areacityList.get(position).getAreaId();
                selectedCityId = _areacityList.get(position).getCityId();
            }
        });

        _mSave.setText("Add");
        ((FloatingActionButton) findViewById(R.id.editFab)).setVisibility(View.GONE);

        final Snackbar snackbar = Snackbar
                .make(_mCoordinatorLayout, "Please fill required fields!", Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        _mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ((!_firstName.getText().toString().equals("")
                            && !_lastName.getText().toString().equals("") &&
                            !_mBalance.getText().toString().equals("") &&
                            !_mAddress1.getText().toString().equals("")
                            && !_address2.getText().toString().equals("")
                            && !selectedCityId.equals("") && !selectedAreaId.equals("")
                            && !_mPhone.getText().toString().equals("") &&
                            !_mQuantuty.getText().toString().equals("") && !_rate.getText().toString().equals("")
                    )) {
                        VCustomersList holder = new VCustomersList();
                        holder.setFirstName(_firstName.getText().toString());
                        holder.setLastName(_lastName.getText().toString());
                        holder.setBalance_amount(_mBalance.getText().toString());
                        holder.setAddress1(_mAddress1.getText().toString());
                        holder.setAddress2(_address2.getText().toString());
                        holder.setCityId(selectedCityId);
                        holder.setAreaId(selectedAreaId);
                        holder.setMobile(_mPhone.getText().toString());
                        holder.setQuantity(_mQuantuty.getText().toString());
                        holder.setAccountId(Constants.ACCOUNT_ID);
                        holder.setRate(_rate.getText().toString());
                        holder.setDateAdded(formattedDate);
                        holder.setCustomerId(Constants.ACCOUNT_ID + String.valueOf(System.currentTimeMillis()));
                        CustomersTableMagagement.insertCustomerDetail(_dbHelper.getWritableDatabase(), holder);
                        CustomerAddActivity.this.finish();

                    } else {
                        // snackbar.show();
                        Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.fill_require_fields), Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException npe) {
                    snackbar.show();
                }
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();

        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


}
