package com.milky.service.databaseutils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.milky.viewmodel.VBill;

/**
 * Created by Neha on 11/30/2015.
 */
public class BillTableManagement {
    public static void insertBillData(SQLiteDatabase db, VBill holder) {
        ContentValues values = new ContentValues();
        values.put(TableColumns.ACCOUNT_ID, holder.getAccountId());
        values.put(TableColumns.CUSTOMER_ID, holder.getCustomerId());
        values.put(TableColumns.START_DATE, holder.getStartDate());
        values.put(TableColumns.END_DATE, holder.getEndDate());
        values.put(TableColumns.QUANTITY, holder.getQuantity());
        values.put(TableColumns.BALANCE, holder.getEndDate());
        values.put(TableColumns.ADJUSTMENTS, holder.getAdjustment());
        values.put(TableColumns.TAX, holder.getTax());
        values.put(TableColumns.IS_CLEARED, holder.getIsCleared());
        values.put(TableColumns.PAYMENT_MADE, holder.getPaymentMode());
        values.put(TableColumns.DATE_ADDED, holder.getDateAdded());
        values.put(TableColumns.DATE_MODIFIED, holder.getDateModified());

        db.insert(TableNames.TABLE_BILL, null, values);
    }

}
