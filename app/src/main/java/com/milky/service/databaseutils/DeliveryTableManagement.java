package com.milky.service.databaseutils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.milky.utils.Constants;
import com.milky.viewmodel.VCustomersList;
import com.milky.viewmodel.VDelivery;
import com.tyczj.extendedcalendarview.DateQuantityModel;
import com.tyczj.extendedcalendarview.Day;

import java.util.ArrayList;

/**
 * Created by Neha on 12/4/2015.
 */
public class DeliveryTableManagement {
    public static void insertCustomerDetail(SQLiteDatabase db, VCustomersList holder) {
        ContentValues values = new ContentValues();

        values.put(TableColumns.QUANTITY, holder.getQuantity());
        values.put(TableColumns.ACCOUNT_ID, Constants.ACCOUNT_ID);
        values.put(TableColumns.CUSTOMER_ID, holder.getCustomerId());
        values.put(TableColumns.DAY, Constants.QUANTITY_UPDATED_DAY);
        values.put(TableColumns.MONTH, Constants.QUANTITY_UPDATED_MONTH);
        values.put(TableColumns.YEAR, Constants.QUANTITY_UPDATED_YEAR);
        values.put(TableColumns.DELIVERY_DATE, holder.getDeliverydate());

        db.insert(TableNames.TABLE_DELIVERY, null, values);
    }


    public static boolean isHasData(SQLiteDatabase db, String custId, String deliveryDate) {
        String selectQuery = "SELECT * FROM " + TableNames.TABLE_DELIVERY + " WHERE " + TableColumns.DELIVERY_DATE + " ='"
                + deliveryDate + "'" + " AND "
                + TableColumns.CUSTOMER_ID + " ='" + custId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        Boolean result = cursor.getCount() > 0;

        cursor.close();
        return result;
    }

    public static String getCustomersBySelectedDay(SQLiteDatabase db, String custId, String deliveryDate) {
        String selectquery = "SELECT * FROM " + TableNames.TABLE_DELIVERY + " WHERE " + TableColumns.DELIVERY_DATE
                + " ='" + deliveryDate + "'" + " AND " + TableColumns.CUSTOMER_ID + " ='" + custId + "'";
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
        values.put(TableColumns.DAY, Constants.QUANTITY_UPDATED_DAY);
        values.put(TableColumns.MONTH, Constants.QUANTITY_UPDATED_MONTH);
        values.put(TableColumns.YEAR, Constants.QUANTITY_UPDATED_YEAR);
        values.put(TableColumns.DELIVERY_DATE, holder.getDeliverydate());

        db.update(TableNames.TABLE_DELIVERY, values, TableColumns.DELIVERY_DATE + " ='" + holder.getDeliverydate() + "'"
                + " AND " + TableColumns.CUSTOMER_ID +
                " ='" + holder.getCustomerId() + "'", null);
    }


    public static ArrayList<DateQuantityModel> getQuantityOfDay(SQLiteDatabase db) {
        String selectquery = "SELECT * FROM " + TableNames.TABLE_DELIVERY;
        ArrayList<DateQuantityModel> quantityList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectquery, null);

        if (cursor.moveToFirst()) {
            do {
                DateQuantityModel holder = new DateQuantityModel();
                holder.setCustomerId(cursor.getString(cursor.getColumnIndex(TableColumns.CUSTOMER_ID)));
                holder.setQuantity(cursor.getString(cursor.getColumnIndex(TableColumns.QUANTITY)));
                holder.setDay(cursor.getString(cursor.getColumnIndex(TableColumns.DAY)));
                holder.setMonth(cursor.getString(cursor.getColumnIndex(TableColumns.MONTH)));
                holder.setYear(cursor.getString(cursor.getColumnIndex(TableColumns.YEAR)));
                holder.setDate(cursor.getString(cursor.getColumnIndex(TableColumns.DELIVERY_DATE)));
                quantityList.add(holder);
            }
            while (cursor.moveToNext());


        }
        cursor.close();
        if (db.isOpen())
            db.close();
        return quantityList;
    }

    public static ArrayList<DateQuantityModel> gwtMilkQuantityofCustomer(SQLiteDatabase db, final String custId) {
        String selectquery = "SELECT * FROM " + TableNames.TABLE_DELIVERY + " WHERE " + TableColumns.CUSTOMER_ID + " ='" + custId + "'";
        ArrayList<DateQuantityModel> quantityList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectquery, null);

        if (cursor.moveToFirst()) {
            do {
                DateQuantityModel holder = new DateQuantityModel();
                holder.setCustomerId(cursor.getString(cursor.getColumnIndex(TableColumns.CUSTOMER_ID)));
                holder.setQuantity(cursor.getString(cursor.getColumnIndex(TableColumns.QUANTITY)));
                holder.setDay(cursor.getString(cursor.getColumnIndex(TableColumns.DAY)));
                holder.setMonth(cursor.getString(cursor.getColumnIndex(TableColumns.MONTH)));
                holder.setYear(cursor.getString(cursor.getColumnIndex(TableColumns.YEAR)));
                holder.setDate(cursor.getString(cursor.getColumnIndex(TableColumns.DELIVERY_DATE)));
                quantityList.add(holder);
            }
            while (cursor.moveToNext());


        }
        cursor.close();
        if (db.isOpen())
            db.close();
        return quantityList;
    }



}
