package com.example.tonio.rentallmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        actualizarcabanas();
    }
    public void lanzar(View view) {
        Intent i = new Intent(this, listaCabanias.class );
        startActivity(i);
    }
    public void cargarCabana(View view) {
        Intent i = new Intent(this, Cargar.class);
        startActivity(i);
    }

    public void alquilarCabana(View view){
        Intent i = new Intent(this, Alquilar.class);
        startActivity(i);
    }

    public void actualizarcabanas() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("SELECT * FROM Alquiladas", null);
        if(fila.moveToFirst()){
            do {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
                try {
                    Date date1 = sdf.parse(fila.getString(1) + "-" + fila.getString(2));
                    String msTime = String.valueOf(System.currentTimeMillis());
                    Date date2 = sdf.parse(msTime);
                    if (date1.equals(date2)|| date1.before(date2)){
                        Cursor filaC = db.rawQuery("SELECT name FROM cabanas WHERE (idc = '" + fila.getString(0)+"'"+")", null);
                        filaC.moveToFirst();
                        Toast.makeText(this, "La cabaña "+filaC.getString(0)+ " , ya no esta mas alquilada", Toast.LENGTH_LONG);
                        db.delete("Alquiladas","idc = "+ fila.getInt(0), null);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while (fila.moveToNext());
        }

    }
}
