package com.milky.service.databaseutils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.milky.viewmodel.VDelivery;
import com.milky.viewmodel.VGlobalSettings;

import java.util.ArrayList;

/**
 * Created by Neha on 11/30/2015.
 */
public class DeliveryTableManagement {
    public static void insertGlobaleDeliveryData(SQLiteDatabase db, VDelivery holder) {
        ContentValues values = new ContentValues();
        values.put(TableColumns.ACCOUNT_ID, holder.getAccountId());
        values.put(TableColumns.CUSTOMER_ID, holder.getCustomerId());
        values.put(TableColumns.DELIVERY_DATE, holder.getDeliveryDate());
        values.put(TableColumns.START_DATE, holder.getStartDate());
        values.put(TableColumns.DATE_MODIFIED, holder.getDateModified());
        values.put(TableColumns.QUANTITY, holder.getQuantity());
        db.insert(TableNames.TABLE_DELIVERY, null, values);
    }

    public static ArrayList<VDelivery> getDeliveryDetails(SQLiteDatabase db) {
        String selectquery = "SELECT * FROM " + TableNames.TABLE_CUSTOMER;
        ArrayList<VDelivery> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectquery, null);

        if (cursor.moveToFirst()) {
            do {
                VDelivery holder = new VDelivery();
                if (cursor.getString(cursor.getColumnIndex(TableColumns.ID)) != null)
                    holder.setId(cursor.getString(cursor.getColumnIndex(TableColumns.ID)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.ACCOUNT_ID)) != null)
                    holder.setAccountId(cursor.getString(cursor.getColumnIndex(TableColumns.ACCOUNT_ID)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.CUSTOMER_ID)) != null)
                    holder.setCustomerId(cursor.getString(cursor.getColumnIndex(TableColumns.CUSTOMER_ID)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.DELIVERY_DATE)) != null)
                    holder.setDeliveryDate(cursor.getString(cursor.getColumnIndex(TableColumns.DELIVERY_DATE)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.QUANTITY)) != null)
                    holder.setQuantity(cursor.getString(cursor.getColumnIndex(TableColumns.QUANTITY)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.START_DATE)) != null)
                    holder.setStartDate(cursor.getString(cursor.getColumnIndex(TableColumns.START_DATE)));
                if (cursor.getString(cursor.getColumnIndex(TableColumns.DATE_MODIFIED)) != null)
                    holder.setDateModified(cursor.getString(cursor.getColumnIndex(TableColumns.DATE_MODIFIED)));

                list.add(holder);
            }


            while (cursor.moveToNext());


        }
        cursor.close();
        if (db.isOpen())
            db.close();
        return list;
    }
}