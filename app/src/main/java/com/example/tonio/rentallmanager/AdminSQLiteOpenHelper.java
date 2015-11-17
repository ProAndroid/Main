package com.example.tonio.rentallmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by facu on 10/11/15.
 */


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    //public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    //  super(context, name, factory, version);
    private static final int DATABASE_VERSION = 1;
    public AdminSQLiteOpenHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CABANAS = "CREATE TABLE " + Cabana.TABLE
                + "("
                + Cabana.KEY_ID + " integer primary key autoincrement,"
                + Cabana.KEY_name + " text" + ")";
        db.execSQL(CREATE_TABLE_CABANAS);

        String CREATE_TABLE_ALQUILADAS = "CREATE TABLE " + Alquiladas.TABLE
                +"("
                + Alquiladas.KEY_ID + " integer primary key autoincrement,"
                + Cabana.KEY_ID + " int,"
                + Alquiladas.KEY_checkin + " text,"
                + Alquiladas.KEY_checkout + " text"
                +")";
        db.execSQL(CREATE_TABLE_ALQUILADAS);

        String CREATE_TABLE_PERSONA="CREATE TABLE "+Persona.TABLE
                +"("
                +Persona.kEY_dni+" integer primary key,"
                +Persona.KEY_ID+ " int,"
                +Persona.KEY_nombre+" text,"
                +Persona.KEY_email+" text"
                +")";
        db.execSQL(CREATE_TABLE_PERSONA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IS EXISTS "+ Cabana.TABLE);
        onCreate(db);

    }
}
