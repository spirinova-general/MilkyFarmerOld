package com.milky.ui.customers;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.milky.R;
import com.milky.service.databaseutils.AreaMapTableManagement;
import com.milky.service.databaseutils.CustomersTableMagagement;
import com.milky.service.databaseutils.DatabaseHelper;
import com.milky.service.databaseutils.GlobalSettingTableManagement;
import com.milky.service.databaseutils.TableNames;
import com.milky.ui.adapters.AreaCityAdapter;
import com.milky.utils.AppUtil;
import com.milky.utils.Constants;
import com.milky.utils.EnableEditableFields;
import com.milky.utils.TextValidationMessage;
import com.milky.viewmodel.VAreaMapper;
import com.milky.viewmodel.VCustomersList;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Neha on 11/19/2015.
 */
public class CustomerSettingFragment extends Fragment {
    private EditText _mFirstName, _mLastName, _mAddress1, _mBalance, _mQuantuty, _mAddress2, _mMobile, _mRate;
    private InputMethodManager inputMethodManager;
    private Button _mSave, _mCancel;
    private FloatingActionButton _mEdit;
    private int dataCount = 0;
    private LinearLayout _mBottomLayout;
    private TextInputLayout _phone_textinput_layout;
    private AutoCompleteTextView _autocomplete_city_area;
    private String selectedCityId = "", selectedAreaId = "";
    private TextInputLayout name_layout, last_name_layout, balance_layout, flat_number_layout, street_layout, milk_quantity_layout, rate_layout;
    private VCustomersList dataHolder;
    private DatabaseHelper _dbHelper;
    private String itemName;
    private String[] autoCompleteData;
    private String[] mData;
    private ArrayList<VAreaMapper> areaList, _areacityList = new ArrayList<>();


    //   private FormEditText et_phone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        View view = inflater.inflate(R.layout.activity_customers_setting, container, false);
        inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        /*
        * initialize all resouces
        * */
        initResources(view);

       /*
        * Set text field listeners*/
        _mAddress1.addTextChangedListener(new TextValidationMessage(flat_number_layout, getActivity(), false));
        _mFirstName.addTextChangedListener(new TextValidationMessage(name_layout, getActivity(), false));
        _mLastName.addTextChangedListener(new TextValidationMessage(last_name_layout, getActivity(), false));

        _mQuantuty.addTextChangedListener(new TextValidationMessage(milk_quantity_layout, getActivity(), false));
        _mBalance.addTextChangedListener(new TextValidationMessage(balance_layout, getActivity(), false));
        _mMobile.addTextChangedListener(new TextValidationMessage(_phone_textinput_layout, getActivity(), true));
        _mAddress2.addTextChangedListener(new TextValidationMessage(street_layout, getActivity(), false));
        _mRate.addTextChangedListener(new TextValidationMessage(rate_layout, getActivity(), false));

        /* ---------------------------------------------------*/

/* Let fields be enabled if edit button has been clicked only.
* */
        _mRate.setOnTouchListener(new EnableEditableFields(_mRate, getActivity(), inputMethodManager));
        _mBalance.setOnTouchListener(new EnableEditableFields(_mBalance, getActivity(), inputMethodManager));
        _mLastName.setOnTouchListener(new EnableEditableFields(_mLastName, getActivity(), inputMethodManager));
        _mFirstName.setOnTouchListener(new EnableEditableFields(_mFirstName, getActivity(), inputMethodManager));
        _mMobile.setOnTouchListener(new EnableEditableFields(_mMobile, getActivity(), inputMethodManager));
        _mAddress1.setOnTouchListener(new EnableEditableFields(_mAddress1, getActivity(), inputMethodManager));
        _mAddress2.setOnTouchListener(new EnableEditableFields(_mAddress2, getActivity(), inputMethodManager));
        _mQuantuty.setOnTouchListener(new EnableEditableFields(_mQuantuty, getActivity(), inputMethodManager));


        return view;

    }

    /*    public void onClickNext(View v) {
            FormEditText[] allFields = {_mPhone};
            boolean allValid = true;
            for (FormEditText field : allFields) {
                allValid = field.testValidity() && allValid;
            }

            if (allValid) {
                // YAY
            } else {
                // EditText are going to appear with an exclamation mark and an explicative message.
            }
        }*/
    private void initResources(View view) {

        _mFirstName = (EditText) view.findViewById(R.id.first_name);
        _mLastName = (EditText) view.findViewById(R.id.last_name);
        _mRate = (EditText) view.findViewById(R.id.rate);
        _mAddress1 = (EditText) view.findViewById(R.id.flat_number);
        _mMobile = (EditText) view.findViewById(R.id.phone);
        // et_phone=(FormEditText)view.findViewById(R.id.et_phone);
        _mQuantuty = (EditText) view.findViewById(R.id.milk_quantity);
        _mAddress2 = (EditText) view.findViewById(R.id.street);
        _mBalance = (EditText) view.findViewById(R.id.balance);
        _autocomplete_city_area = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_city_area);
        _mSave = (Button) view.findViewById(R.id.save);
        _mCancel = (Button) view.findViewById(R.id.cancel);
        _mEdit = (FloatingActionButton) view.findViewById(R.id.editFab);
        _mBottomLayout = (LinearLayout) view.findViewById(R.id.bottomLayout);
        _mEdit.setVisibility(View.VISIBLE);
        _phone_textinput_layout = (TextInputLayout) view.findViewById(R.id.phone_textinput_layout);
        name_layout = (TextInputLayout) view.findViewById(R.id.name_layout);
        last_name_layout = (TextInputLayout) view.findViewById(R.id.last_name_layout);
        street_layout = (TextInputLayout) view.findViewById(R.id.street_layout);
        balance_layout = (TextInputLayout) view.findViewById(R.id.balance_layout);
        flat_number_layout = (TextInputLayout) view.findViewById(R.id.flat_number_layout);
        milk_quantity_layout = (TextInputLayout) view.findViewById(R.id.milk_quantity_layout);
        rate_layout = (TextInputLayout) view.findViewById(R.id.rate_layout);
        _dbHelper = AppUtil.getInstance().getDatabaseHandler();

        /*Set defaul rate
        * */
        if (_dbHelper.isTableNotEmpty(TableNames.TABLE_GLOBAL_SETTINGS))
            _mRate.setText(GlobalSettingTableManagement.getDefaultRate(_dbHelper.getReadableDatabase()));

        /*
        * If to edit customer details
        * */

        _mMobile.setText(getActivity().getIntent().getStringExtra("mobile"));
        _mQuantuty.setText(getActivity().getIntent().getStringExtra("quantity"));
        _mFirstName.setText(getActivity().getIntent().getStringExtra("fname"));
        _mLastName.setText(getActivity().getIntent().getStringExtra("lname"));
        _mBalance.setText(getActivity().getIntent().getStringExtra("balance"));
        _mAddress2.setText(getActivity().getIntent().getStringExtra("address2"));
        _mAddress1.setText(getActivity().getIntent().getStringExtra("address1"));
        _mRate.setText(getActivity().getIntent().getStringExtra("defaultrate"));
        _autocomplete_city_area.setText(AreaMapTableManagement.getAreaNameById(_dbHelper.getReadableDatabase(), getActivity().getIntent().getStringExtra("areaId")) + " " + AreaMapTableManagement.getCityNameById(_dbHelper.getReadableDatabase(), getActivity().getIntent().getStringExtra("cityId")));
        areaList = AreaMapTableManagement.getAreaById(_dbHelper.getReadableDatabase(), Constants.ACCOUNT_ID);
        autoCompleteData = new String[areaList.size()];
//        for (int i = 0; i < areaList.size(); i++) {
//            // Get City for area
//            // autoCompleteData[i] = AreaMapTableManagement.getCityNameById(_dbHelper.getReadableDatabase(), _areaList.get(i).getArea()) + " " + _areaList.get(i).getCity();
//            autoCompleteData[i] = areaList.get(i).getArea()+" "+AreaMapTableManagement.getCityNameById(_dbHelper.getReadableDatabase(),areaList.get(i).getCityId());
//        }
        /*adapter1 = new AreaCityAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, R.id.te1, areaList);
        _autocomplete_city_area.setAdapter(adapter1);*/
        for (int i = 0; i < areaList.size(); i++) {
            VAreaMapper areacity = new VAreaMapper();
            areacity.setArea(areaList.get(i).getArea());
            areacity.setAreaId(areaList.get(i).getAreaId());
            areacity.setCityId(areaList.get(i).getCityId());
            areacity.setCity(AreaMapTableManagement.getCityNameById(_dbHelper.getReadableDatabase(), areaList.get(i).getCityId()));
            areacity.setCityArea(areacity.getArea() + areacity.getCity());
            _areacityList.add(areacity);


        }

        AreaCityAdapter adapter1 = new AreaCityAdapter(getActivity(), 0, R.id.te1, _areacityList);
        _autocomplete_city_area.setAdapter(adapter1);

        // final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, autoCompleteData);

     /*   _autocomplete_city_area.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                adapter1.getFilter().filter(arg0);
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

/*
/*        String data = areaList.getItem(position).split(" ");*//*
        int index = Arrays.asList(mData).indexOf(data);*/
        //  _autocomplete_city_area.setAdapter(adapter);
        //setting the adapter data into the AutoCompleteTextView
        selectedAreaId = getActivity().getIntent().getStringExtra("areaId");
        selectedCityId = getActivity().getIntent().getStringExtra("cityId");

        _autocomplete_city_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _autocomplete_city_area.setText(_areacityList.get(position).getArea() + ", " + _areacityList.get(position).getCity());
                selectedAreaId = _areacityList.get(position).getAreaId();
                selectedCityId = _areacityList.get(position).getCityId();
                // int selection = parent.getSelectedItemPosition();

             /*   for (int i = 0; i < areaList.size(); i++) {
                    if (areaList.get(i).getArea().equals(_autocomplete_city_area.getText().toString())) {
                     //int index = areaList.indexOf(_autocomplete_city_area.getText().toString());
                        selectedAreaId = areaList.get(i).getAreaId();
                        selectedCityId = areaList.get(i).getCityId();
                    }
                    else{
                        Toast.makeText(getActivity(),"Sorry i cant find it",Toast.LENGTH_SHORT).show();
                    }
                }*/
              /*  for(int i=0; i < areaList.size(); i++)
                    if(areaList[i].contains(_autocomplete_city_area.getText().toString()))
                        aPosition = i;*/
                // Toast.makeText(getActivity(),"area is"+_autocomplete_city_area.getText().toString(),Toast.LENGTH_SHORT).show();
                /*if(index>=0) {
                    selectedAreaId = areaList.get(index).getAreaId();
                    selectedCityId = areaList.get(index).getCityId();
                    }*/
            }
        });
        disableKeyBoard();

        _mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //disable edit mode
                EnableEditableFields.setIsEnabled(false);
                disableKeyBoard();
                _mEdit.setVisibility(View.VISIBLE);
                _mBottomLayout.setVisibility(View.GONE);

                disableKeyBoard();

                //Clear focus from first field
                _mFirstName.clearFocus();
                _mLastName.clearFocus();
                _mBalance.clearFocus();
                _mQuantuty.clearFocus();
                _mMobile.clearFocus();
                _mAddress1.clearFocus();
                _mRate.clearFocus();
                _autocomplete_city_area.clearFocus();
                _mAddress2.clearFocus();

            }
        });
        _mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                _mFirstName.requestFocus();
                inputMethodManager.showSoftInput(_mFirstName, InputMethodManager.SHOW_IMPLICIT);
                _mBottomLayout.setVisibility(View.VISIBLE);
                _mEdit.setVisibility(View.GONE);
                _autocomplete_city_area.setEnabled(true);
                _mFirstName.setSelection(_mFirstName.getText().length());
                EnableEditableFields.setIsEnabled(true);
            }
        });
        /*
* Save button onclick Event
* */
        _mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!_mFirstName.getText().toString().equals("")
                        && !_mLastName.getText().toString().equals("") &&
                        !_mBalance.getText().toString().equals("") &&
                        !_mAddress1.getText().toString().equals("")
                        && !_mRate.getText().toString().equals("")
                        && !_mAddress2.getText().toString().equals("")
                        && !selectedCityId.equals("") && !selectedAreaId.equals("")
                        && !_mMobile.getText().toString().equals("") &&
                        !_mQuantuty.getText().toString().equals("")
                ) {


                    VCustomersList holder = new VCustomersList();
                    holder.setFirstName(_mFirstName.getText().toString());
                    holder.setLastName(_mLastName.getText().toString());
                    holder.setBalance_amount(_mBalance.getText().toString());
                    holder.setAddress1(_mAddress1.getText().toString());
                    holder.setAddress2(_mAddress2.getText().toString());
                    holder.setCityId(selectedCityId);
                    holder.setAreaId(selectedAreaId);
                    holder.setMobile(_mMobile.getText().toString());
                    holder.setQuantity(_mQuantuty.getText().toString());
                    holder.setAccountId(Constants.ACCOUNT_ID);
                    holder.setRate(_mRate.getText().toString());
                    holder.setDateAdded(getActivity().getIntent().getStringExtra("added_date"));
                    holder.setDateModified(String.valueOf(System.currentTimeMillis()));
                    holder.setCustomerId(Constants.ACCOUNT_ID + String.valueOf(System.currentTimeMillis()));
                    CustomersTableMagagement.updateCustomerDetail(_dbHelper.getWritableDatabase(), holder, getActivity().getIntent().getStringExtra("cust_id"));
                    Toast.makeText(getActivity(), "Customer edited successfully !", Toast.LENGTH_SHORT).show();
                    EnableEditableFields.setIsEnabled(false);
                    getActivity().finish();

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.fill_require_fields), Toast.LENGTH_SHORT).show();

                }


            }
        });

    }


    private void disableKeyBoard() {
        //Block default keyboard

        new EnableEditableFields(_mFirstName, getActivity(), inputMethodManager).blockDefaultKeys();
        new EnableEditableFields(_mLastName, getActivity(), inputMethodManager).blockDefaultKeys();
        // _autocomplete_city_area.setEnabled(false);
        new EnableEditableFields(_mRate, getActivity(), inputMethodManager).blockDefaultKeys();
        new EnableEditableFields(_mQuantuty, getActivity(), inputMethodManager).blockDefaultKeys();
        new EnableEditableFields(_mBalance, getActivity(), inputMethodManager).blockDefaultKeys();
        new EnableEditableFields(_mMobile, getActivity(), inputMethodManager).blockDefaultKeys();
        new EnableEditableFields(_mAddress1, getActivity(), inputMethodManager).blockDefaultKeys();
        new EnableEditableFields(_mAddress2, getActivity(), inputMethodManager).blockDefaultKeys();
    }
}