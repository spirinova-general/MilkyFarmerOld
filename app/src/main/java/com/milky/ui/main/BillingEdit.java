package com.milky.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import com.milky.R;

/**
 * Created by Neha on 11/20/2015.
 */
public class BillingEdit extends AppCompatActivity {
    private Intent intent;
    private Toolbar _mToolbar;
    private EditText milk_quantity;
    private EditText rate;
    private EditText balance_amount;
    private EditText tax;
    private Button save;

    private void getview() {

        milk_quantity = (EditText) findViewById(R.id.milk_quantity);
        rate = (EditText) findViewById(R.id.rate);
        balance_amount = (EditText) findViewById(R.id.balance_amount);
        tax = (EditText) findViewById(R.id.tax);
        save = (Button) findViewById(R.id.save);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_fragment);
        intent = this.getIntent();
        setActionBar();
        getview();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }


    private void setActionBar() {
        _mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        /*
        * Set Custome action bar
        * */
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar_layout, null);

        TextView title = (TextView) mCustomView.findViewById(R.id.title);
        TextView subTitle = (TextView) mCustomView.findViewById(R.id.date);
        subTitle.setVisibility(View.GONE);
        title.setText(intent.getStringExtra("fname") + intent.getStringExtra("lname"));
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
