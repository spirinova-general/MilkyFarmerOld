package com.milky.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.milky.R;
import com.milky.service.databaseutils.AreaMapTableManagement;
import com.milky.service.databaseutils.DatabaseHelper;
import com.milky.service.databaseutils.TableNames;
import com.milky.utils.AppUtil;
import com.milky.utils.Constants;
import com.milky.viewmodel.VAreaMapper;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Toolbar _mToolbar;
    private DatabaseHelper _dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      /*
      * init resources
      * */
        initResources();

        /*
        * Set up ACTIONBAR
        * */
        supportActionBar();
        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navView);
        mNavigationView.setVisibility(View.VISIBLE);
        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new MainTabFragment()).commit();


        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_settings:
                        Intent i = new Intent(MainActivity.this, GlobalSetting.class);
                        startActivity(i);

                        break;
                }
                mDrawerLayout.closeDrawers();

                return true;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customers_menu, menu);
        return true;
    }

    private void supportActionBar() {
        _mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_mToolbar);
        final ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    public static int POSITION = 0;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int page = POSITION;
        switch (page) {
            case 0:
                menu.findItem(R.id.areas).setVisible(true);
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_add_bill).setVisible(false);


                break;
            case 1:
                menu.findItem(R.id.areas).setVisible(true);
                menu.findItem(R.id.action_search).setVisible(true);
                menu.findItem(R.id.action_add_bill).setVisible(false);

                break;
            case 2:
                menu.findItem(R.id.areas).setVisible(true);
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_add_bill).setVisible(false);
                break;
        }
        return true;
    }


    private void initResources() {
        String Area[] = new String[]{"Hadaspar", "Worli", "Baner", "Phase5", "Sector 71"};
        int AreaID[] = new int[]{1, 2, 3, 4, 5};
        String City[] = new String[]{"Pune", "Mumbai", "Mohali"};
        int CityId[] = new int[]{1, 2, 3};

        _dbHelper = AppUtil.getInstance().getDatabaseHandler();
        if (!_dbHelper.isTableNotEmpty(TableNames.TABLE_AREA))
            for (int i = 0; i < 5; i++) {
                VAreaMapper holder = new VAreaMapper();
                holder.setArea(Area[i]);
                holder.setAreaId(String.valueOf(AreaID[i]));
                if ((i == 0 || i == 2)) {
                    holder.setCity(City[0]);
                    holder.setCityId(String.valueOf(CityId[0]));
                } else if (i == 1) {
                    holder.setCity(City[1]);
                    holder.setCityId(String.valueOf(CityId[1]));
                } else {
                    holder.setCity(City[2]);
                    holder.setCityId(String.valueOf(CityId[2]));
                }

                holder.setAccountId(Constants.ACCOUNT_ID);
                AreaMapTableManagement.insertAreaDetail(_dbHelper.getWritableDatabase(), holder);


            }
        for (int i = 0; i < City.length; i++) {
            VAreaMapper holder = new VAreaMapper();
            holder.setCityId(String.valueOf(CityId[i]));
            holder.setCity(City[i]);
            holder.setAccountId(Constants.ACCOUNT_ID);
            AreaMapTableManagement.insertCityDetail(_dbHelper.getWritableDatabase(), holder);
        }
    }
}