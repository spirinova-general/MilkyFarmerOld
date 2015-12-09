package com.milky.service.databaseutils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;

import com.milky.utils.Constants;
import com.milky.viewmodel.VCustomersList;

import java.util.ArrayList;

/**
 * Created by Neha on 12/4/2015.
 */
public class ConsumptionCustomersTableManagement {
    public static void insertCustomerDetail(SQLiteDatabase db, VCustomersList holder) {
        ContentValues values = new ContentValues();

        values.put(TableColumns.QUANTITY, holder.getQuantity());
        values.put(TableColumns.ACCOUNT_ID, Constants.ACCOUNT_ID);
        values.put(TableColumns.CUSTOMER_ID, holder.getCustomerId());
        values.put(TableColumns.DATE_MODIFIED, holder.getDateModified());
        values.put(TableColumns.DATE_QUANTITY_MODIFIED, Constants.QUANTITY_UPDATED_DATE);

        db.insert(TableNames.TABLE_CONSUMED_QUANTITY, null, values);
    }

    public static ArrayList<VCustomersList> getAllCustomersBySelectedDay(SQLiteDatabase db, String day) {
        String selectquery = "SELECT * FROM " + TableNames.TABLE_CONSUMED_QUANTITY + " WHERE " + TableColumns.DATE_QUANTITY_MODIFIED
                + " ='" + day + "'";
        ArrayList<VCustomersList> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectquery, null);

        if (cursor.moveToFirst()) {
            do {
                VCustomersList holder = new VCustomersList();
                if (cursor.getString(cursor.getColumnIndex(TableColumns.DATE_ADDED)) != null)
                    holder.setDateAdded(cursor.getString(cursor.getColumnIndex(TableColumns.DATE_ADDED)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.ACCOUNT_ID)) != null)
                    holder.setAccountId(cursor.getString(cursor.getColumnIndex(TableColumns.ACCOUNT_ID)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.CUSTOMER_ID)) != null)
                    holder.setCustomerId(cursor.getString(cursor.getColumnIndex(TableColumns.CUSTOMER_ID)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.FIRST_NAME)) != null)
                    holder.setFirstName(cursor.getString(cursor.getColumnIndex(TableColumns.FIRST_NAME)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.LAST_NAME)) != null)
                    holder.setLastName(cursor.getString(cursor.getColumnIndex(TableColumns.LAST_NAME)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.BALANCE)) != null)
                    holder.setBalance_amount(cursor.getString(cursor.getColumnIndex(TableColumns.BALANCE)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.ADDRESS_1)) != null)
                    holder.setAddress1(cursor.getString(cursor.getColumnIndex(TableColumns.ADDRESS_1)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.ADDRESS_2)) != null)
                    holder.setAddress2(cursor.getString(cursor.getColumnIndex(TableColumns.ADDRESS_2)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.CITY_ID)) != null)
                    holder.setCityId(cursor.getString(cursor.getColumnIndex(TableColumns.CITY_ID)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.AREA_ID)) != null)
                    holder.setAreaId(cursor.getString(cursor.getColumnIndex(TableColumns.AREA_ID)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.MOBILE)) != null)
                    holder.setMobile(cursor.getString(cursor.getColumnIndex(TableColumns.MOBILE)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.QUANTITY)) != null)
                    holder.setQuantity(cursor.getString(cursor.getColumnIndex(TableColumns.QUANTITY)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.DEFAULT_RATE)) != null)
                    holder.setRate(cursor.getString(cursor.getColumnIndex(TableColumns.DEFAULT_RATE)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.DATE_QUANTITY_MODIFIED)) != null)
                    holder.setRate(cursor.getString(cursor.getColumnIndex(TableColumns.DATE_QUANTITY_MODIFIED)));
                list.add(holder);
            }
            while (cursor.moveToNext());


        }
        cursor.close();
        if (db.isOpen())
            db.close();
        return list;
    }

    public static boolean isHasData(String ts, SQLiteDatabase db, String custId) {
        String selectQuery = "SELECT * FROM " + TableNames.TABLE_CONSUMED_QUANTITY + " WHERE " + TableColumns.DATE_QUANTITY_MODIFIED + " ='"
                + ts + "'" +" AND "+ TableColumns.CUSTOMER_ID + " ='" + custId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        Boolean result = cursor.getCount() > 0;

        cursor.close();
        return result;
    }

    public static String getCustomersBySelectedDay(SQLiteDatabase db, String custId) {
        String selectquery = "SELECT * FROM " + TableNames.TABLE_CONSUMED_QUANTITY + " WHERE " + TableColumns.DATE_QUANTITY_MODIFIED
                + " ='" + Constants.QUANTITY_UPDATED_DATE + "'" + " AND " + TableColumns.CUSTOMER_ID + " ='" + custId + "'";
        String quantity = "";

        Cursor cursor = db.rawQuery(selectquery, null);

        if (cursor.moveToFirst()) {
            do {

                if (cursor.getString(cursor.getColumnIndex(TableColumns.QUANTITY)) != null)
                    quantity = cursor.getString(cursor.getColumnIndex(TableColumns.QUANTITY));

            }
            while (cursor.moveToNext());


        }
        cursor.close();
        if (db.isOpen())
            db.close();
        return quantity;
    }

    public static void updateCustomerDetail(SQLiteDatabase db, VCustomersList holder) {
        ContentValues values = new ContentValues();

        values.put(TableColumns.QUANTITY, holder.getQuantity());
        values.put(TableColumns.ACCOUNT_ID, Constants.ACCOUNT_ID);
        values.put(TableColumns.CUSTOMER_ID, holder.getCustomerId());
        values.put(TableColumns.DATE_QUANTITY_MODIFIED, Constants.QUANTITY_UPDATED_DATE);
        db.update(TableNames.TABLE_CONSUMED_QUANTITY, values, TableColumns.DATE_QUANTITY_MODIFIED + " ='" + Constants.QUANTITY_UPDATED_DATE + "'", null);
    }
}
