package com.example.tonio.rentallmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class listaCabanias extends AppCompatActivity {
    ListView lv;
    ArrayList<String> target = new ArrayList<>();
    ArrayAdapter <String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cabanias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,target);
        lv.setAdapter(adapter);
        pasar();
    }

    public  void pasar (){
        try {
            System.out.println("Entre al try");
            //FIXME: no hace nada
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery("SELECT * FROM cabanas WHERE idc IN (SELECT idc FROM Alquiladas)", null);
            System.out.println("Estoy justo antes del if");
            if (fila.moveToFirst()){
                do {
                    String linea = fila.getString(1) + " (Alquilada)";
                    adapter.add(linea);
                }while (fila.moveToNext());
                adapter.notifyDataSetChanged();
            }
            Cursor filaC = db.rawQuery("SELECT name FROM cabanas WHERE idc NOT IN (SELECT idc FROM Alquiladas)", null);
            if (filaC.moveToFirst()){
                do {
                    String linea = filaC.getString(0);
                    adapter.add(linea);
                }while (filaC.moveToNext());
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            Toast.makeText( this, e.toString(), Toast.LENGTH_LONG).show();
            System.out.println(e.toString());
        }

    }
}
