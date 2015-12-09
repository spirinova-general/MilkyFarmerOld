package com.milky.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.milky.R;
import com.milky.service.databaseutils.DatabaseHelper;
import com.milky.service.databaseutils.GlobalSettingTableManagement;
import com.milky.service.databaseutils.TableNames;
import com.milky.utils.AppUtil;
import com.milky.utils.Constants;
import com.milky.utils.EnableEditableFields;
import com.milky.utils.TextValidationMessage;
import com.milky.viewmodel.VGlobalSettings;

/**
 * Created by Neha on 11/19/2015.
 */
public class GlobalSetting extends AppCompatActivity {
    private Toolbar _mToolbar;
    private TextInputLayout rate_layout, custCode_layout, tax_layouts;
    private EditText custCode, rate, tax;
    private InputMethodManager inputMethodManager;
    private LinearLayout _mBottomLayout;
    private FloatingActionButton _editFab;
    private DatabaseHelper _dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gloable_setting_layout);

       /*Set Actionbar to layut*/
        setActionBar();
        /*init xml resources*/
        initResources();

        /*
        * disable keyboard*/
        disableKeyBoard();

        custCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }


    private void initResources() {
        inputMethodManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        rate_layout = (TextInputLayout) findViewById(R.id.rate_layout);
        custCode_layout = (TextInputLayout) findViewById(R.id.custCode_layout);
        tax_layouts = (TextInputLayout) findViewById(R.id.tax_percent_layout);
        custCode = (EditText) findViewById(R.id.custCode);
        rate = (EditText) findViewById(R.id.rate);
        tax = (EditText) findViewById(R.id.tax);
        _mBottomLayout = (LinearLayout) findViewById(R.id.bottomLayout);
        _editFab = (FloatingActionButton) findViewById(R.id.editFab);

        /*Get DBhelper*/
        _dbHelper = AppUtil.getInstance().getDatabaseHandler();
         /*
        * Set text field listeners*/
//        custCode.addTextChangedListener(new TextValidationMessage(custCode_layout, this, false));
        rate.addTextChangedListener(new TextValidationMessage(rate_layout, this, false));
        tax.addTextChangedListener(new TextValidationMessage(tax_layouts, this, false));

         /* Let fields be enabled if edit button has been clicked only.
        * */

        rate.setOnTouchListener(new EnableEditableFields(rate, this, inputMethodManager));
        tax.setOnTouchListener(new EnableEditableFields(tax, this, inputMethodManager));
        /*Set default cust code*/
        custCode.setText("1Axc");
        /*Fill fields from db*/
        if (_dbHelper.isTableNotEmpty(TableNames.TABLE_GLOBAL_SETTINGS)) {
            VGlobalSettings holder = GlobalSettingTableManagement.getGlobalSettingDetail(_dbHelper.getReadableDatabase());
            custCode.setText(holder.getId());
            rate.setText(holder.getDefaultRate());
            tax.setText(holder.getTax());
        }
    }

    private void setActionBar() {
        _mToolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(_mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.global_setting));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editFab:
                /* on click Edit button*/
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                rate.requestFocus();
                inputMethodManager.showSoftInput(rate, InputMethodManager.SHOW_IMPLICIT);
                _mBottomLayout.setVisibility(View.VISIBLE);
                _editFab.setVisibility(View.GONE);
                rate.setSelection(rate.getText().length());
                EnableEditableFields.setIsEnabled(true);
                break;
            case R.id.save:
                if (TextValidationMessage.getIfValid()) {
                    if (_dbHelper.isTableNotEmpty(TableNames.TABLE_GLOBAL_SETTINGS)) {
                        VGlobalSettings holder = new VGlobalSettings();
                        holder.setTax(tax.getText().toString().trim());
                        holder.setDefaultRate(rate.getText().toString().trim());
                        holder.setDateModified(String.valueOf(System.currentTimeMillis()));
                        GlobalSettingTableManagement.updateGlobalSettingData(_dbHelper.getWritableDatabase(), holder, Constants.ACCOUNT_ID);
                    } else {
                        VGlobalSettings holder = new VGlobalSettings();
                        holder.setId(custCode.getText().toString());
                        holder.setTax(tax.getText().toString().trim());
                        holder.setAccountId(Constants.ACCOUNT_ID);
                        holder.setDefaultRate(rate.getText().toString().trim());
                        holder.setDateModified(String.valueOf(System.currentTimeMillis()));
                        GlobalSettingTableManagement.insertGlobalSettingData(_dbHelper.getWritableDatabase(), holder);

                    }
                    Toast.makeText(GlobalSetting.this, getResources().getString(R.string.data_saved_successfully), Toast.LENGTH_SHORT).show();

                    GlobalSetting.this.finish();

                } else {
                    Toast.makeText(GlobalSetting.this, getResources().getString(R.string.fill_require_fields), Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.cancel:
                EnableEditableFields.setIsEnabled(false);
                _editFab.setVisibility(View.VISIBLE);
                _mBottomLayout.setVisibility(View.GONE);

                disableKeyBoard();

                //Clear focus from first field
                custCode.clearFocus();
                tax.clearFocus();
                rate.clearFocus();
                break;
        }
    }

    private void disableKeyBoard() {
        //Block default keyboard
        new EnableEditableFields(custCode, this, inputMethodManager).blockDefaultKeys();
        new EnableEditableFields(rate, this, inputMethodManager).blockDefaultKeys();
        new EnableEditableFields(tax, this, inputMethodManager).blockDefaultKeys();

    }
}
