package com.example.tonio.rentallmanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Cargar extends AppCompatActivity {

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText)findViewById(R.id.editText);
    }

    public void cargar(View view){
        if(!(editText.getText().toString().matches(""))){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"cabanas");
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues guardar = new ContentValues();
            String nom = editText.getText().toString();
            guardar.put("name", nom);
            db.insert("cabanas", null, guardar);
            editText.setText("");
            Toast.makeText(this,"Cargado correctamente", Toast.LENGTH_LONG).show();
            db.close();
    }else{
            Toast.makeText(this, "Falta completar", Toast.LENGTH_LONG).show();

}}}
