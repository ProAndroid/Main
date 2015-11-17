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
    ArrayList<String> target = new ArrayList<String>();
    ArrayAdapter <String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cabanias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,target);
        lv.setAdapter(adapter);
        
        pasar(this.getApplicationContext());
    }

    public  void pasar (Context context){
        try {
            AdminSQLiteOpenHelper dab = new AdminSQLiteOpenHelper(context,"cabanas");
            SQLiteDatabase db = dab.getReadableDatabase();
            Cursor fila = db.rawQuery("SELECT * from cabanas", null);

            int i = 0;


            if (fila.moveToFirst()){
                do {
                    String linea = fila.getString(1);
                    adapter.add(linea);
                }while (fila.moveToNext());{
                }

                adapter.notifyDataSetChanged();

            }

        }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            System.out.println(e);
        }

}}
