package com.milky.service.databaseutils;

/**
 * Created by Neha on 11/30/2015.
 */
public class TableColumsDetail {

    //ACCOUNT
    public static final String ACCOUNT = "CREATE TABLE " + TableNames.TABLE_ACCOUNT + "("
            + TableColumns.ID + " TEXT PRIMARY KEY," + TableColumns.FARMER_CODE + " TEXT," + TableColumns.DATE_MODIFIED + " TEXT,"
            + TableColumns.DATE_ADDED + " TEXT" +
            ")";

    //Account_Area_Mapping
    public static final String ACCOUNT_AREA_MAPPING = "CREATE TABLE " + TableNames.TABLE_ACCOUNT_AREA_MAPPING + "("
            + TableColumns.ID + " TEXT," + TableColumns.ACCOUNT_ID + " TEXT," + TableColumns.AREA_ID + " TEXT" + ")";

    //AREA
    public static final String AREA = "CREATE TABLE " + TableNames.TABLE_AREA + "(" + TableColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + TableColumns.AREA_ID + " TEXT," + TableColumns.AREA_NAME + " TEXT," + TableColumns.CITY_NAME + " TEXT," + TableColumns.CITY_ID + " TEXT," + TableColumns.ACCOUNT_ID + " TEXT" + ")";


    //BILL
    public static final String BILL = "CREATE TABLE " + TableNames.TABLE_BILL + "(" + TableColumns.ID + " TEXT PRIMARY KEY," + TableColumns.ACCOUNT_ID + " TEXT,"
            + TableColumns.CUSTOMER_ID + " TEXT," + TableColumns.START_DATE + " TEXT," + TableColumns.END_DATE + " TEXT," + TableColumns.QUANTITY + " TEXT," + TableColumns.BALANCE + " TEXT,"
            + TableColumns.ADJUSTMENTS + " TEXT," + TableColumns.TAX + " TEXT," + TableColumns.IS_CLEARED + " TEXT,"
            + TableColumns.PAYMENT_MADE + " TEXT,"
            + TableColumns.DATE_ADDED + " TEXT,"
            + TableColumns.DATE_MODIFIED + " TEXT" + ")";
    //CITY
    public static final String CITY = "CREATE TABLE " + TableNames.TABLE_CITY + "(" + TableColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + TableColumns.AREA_ID + " TEXT," + TableColumns.ACCOUNT_ID + " TEXT," + TableColumns.CITY_ID + " TEXT,"
            + TableColumns.CITY_NAME + " TEXT" + ")";

    //CUSTOMER
    public static final String CUSTOMER = "CREATE TABLE " + TableNames.TABLE_CUSTOMER + "(" + TableColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + TableColumns.FIRST_NAME + " TEXT,"
            + TableColumns.LAST_NAME + " TEXT," + TableColumns.MOBILE + " TEXT," + TableColumns.ADDRESS_1 + " TEXT," + TableColumns.ADDRESS_2 + " TEXT," + TableColumns.BALANCE + " TEXT,"
            + TableColumns.DATE_ADDED + " TEXT," + TableColumns.AREA_ID + " TEXT," + TableColumns.CITY_ID + " TEXT,"
            + TableColumns.DATE_MODIFIED + " TEXT," + TableColumns.ACCOUNT_ID + " TEXT," + TableColumns.QUANTITY + " TEXT," + TableColumns.DEFAULT_RATE + " TEXT,"
            + TableColumns.CUSTOMER_ID + " TEXT," + TableColumns.DATE_QUANTITY_MODIFIED + " TEXT" +
            ")";

    //Milk consumption by customer
    public static final String CUSTOMER_MILK_CONSUMPTION = "CREATE TABLE " + TableNames.TABLE_CONSUMED_QUANTITY + "(" + TableColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+TableColumns.DATE_QUANTITY_MODIFIED + " TEXT," +
            TableColumns.DATE_MODIFIED + " TEXT," + TableColumns.ACCOUNT_ID + " TEXT," + TableColumns.QUANTITY + " TEXT,"
            + TableColumns.CUSTOMER_ID + " TEXT" +
            ")";

    //CUSTOMER SETTINGS
    public static final String CUSTOMER_SETTINGS = "CREATE TABLE " + TableNames.TABLE_CUSTOMER_SETTINGS + "(" + TableColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + TableColumns.ACCOUNT_ID + " TEXT," + TableColumns.CUSTOMER_ID + " TEXT," + TableColumns.RATE + " TEXT,"
            + TableColumns.DEFAULT_QUANTITY + " TEXT," + TableColumns.DATE_MODIFIED + " TEXT" + ")";
    //DELIVERY
    public static final String DELIVERY = "CREATE TABLE " + TableNames.TABLE_DELIVERY + "(" + TableColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + TableColumns.ACCOUNT_ID + " TEXT,"
            + TableColumns.CUSTOMER_ID + " TEXT," + TableColumns.DELIVERY_DATE + " TEXT," + TableColumns.QUANTITY + " TEXT," + TableColumns.START_DATE + " TEXT," + TableColumns.DATE_MODIFIED + " TEXT" + ")";
    //GLOBAL_SETTINGS
    public static final String GLOBAL_SETTINGS = "CREATE TABLE " + TableNames.TABLE_GLOBAL_SETTINGS + "(" + TableColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + TableColumns.DEFAULT_RATE + " TEXT,"
            + TableColumns.DATE_MODIFIED + " TEXT," + TableColumns.TAX + " TEXT," + TableColumns.ACCOUNT_ID + " TEXT" + ")";


}
