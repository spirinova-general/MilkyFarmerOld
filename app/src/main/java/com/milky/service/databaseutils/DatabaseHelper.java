package com.milky.service.databaseutils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DatabaseVersioControl.DATABASE_NAME, null,
                DatabaseVersioControl.DATABASE_VERSION);
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableColumsDetail.ACCOUNT);
        db.execSQL(TableColumsDetail.ACCOUNT_AREA_MAPPING);
        db.execSQL(TableColumsDetail.AREA);
        db.execSQL(TableColumsDetail.BILL);
        db.execSQL(TableColumsDetail.CITY);
        db.execSQL(TableColumsDetail.CUSTOMER);
        db.execSQL(TableColumsDetail.CUSTOMER_SETTINGS);
        db.execSQL(TableColumsDetail.DELIVERY);
        db.execSQL(TableColumsDetail.GLOBAL_SETTINGS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String[] tables = {
                TableNames.TABLE_ACCOUNT,
                TableNames.TABLE_ACCOUNT_AREA_MAPPING,
                TableNames.TABLE_GLOBAL_SETTINGS,
                TableNames.TABLE_DELIVERY,
                TableNames.TABLE_CUSTOMER_SETTINGS,
                TableNames.TABLE_CUSTOMER,
                TableNames.TABLE_AREA,
                TableNames.TABLE_BILL,
                TableNames.TABLE_CITY,

        };
        for (String tableName : tables) {
            db.execSQL(String.format("DROP TABLE IF EXISTS %s", tableName));
        }

        onCreate(db);
    }


    public boolean isTableNotEmpty(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + table;

        Cursor cursor = db.rawQuery(selectQuery, null);
        Boolean result = cursor.getCount() > 0;

        cursor.close();
        return result;
    }


}
