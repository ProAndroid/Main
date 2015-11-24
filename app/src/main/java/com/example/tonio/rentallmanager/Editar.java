package com.example.tonio.rentallmanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Editar extends AppCompatActivity {

    public EditText editText2, editText3, editText4, editText5, editText6, editText7, editText8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        editText5 = (EditText)findViewById(R.id.editText5);
        editText6 = (EditText)findViewById(R.id.editText6);
        editText7 = (EditText)findViewById(R.id.editText7);
        editText8 = (EditText)findViewById(R.id.editText8);
        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("nombre");
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor filaC = db.rawQuery("SELECT * FROM cabanas WHERE name= '" + dato + "'", null);
        filaC.moveToFirst();
        Cursor filaA = db.rawQuery("SELECT * FROM Alquilada WHERE idc='"+filaC.getString(1)+"'",null);
        Cursor filaP = db.rawQuery("SELECT * FROM Persona WHERE idc='"+filaC.getString(1)+"'",null);
        filaA.moveToFirst();
        filaP.moveToFirst();
        String toSplit = filaP.getString(1);
        String[] parts = toSplit.split("/");
        editText2.setText(parts[0]);
        editText3.setText(parts[1]);
        editText4.setText(filaP.getString(2));
        editText5.setText(filaP.getString(3));
        editText6.setText(filaA.getString(1));
        editText7.setText(filaA.getString(2));
        editText8.setText(filaC.getString(1));
    }
    public void editar(View view){
        String nombreP, nombreC, dni, email, llegada, salida;
        nombreP = editText2.getText().toString() +"/"+ editText3.getText().toString();
        dni = editText4.getText().toString();
        email = editText5.getText().toString();
        llegada= editText6.getText().toString();
        salida = editText7.getText().toString();
        nombreC = editText8.getText().toString();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor idc = db.rawQuery("SELECT idc FROM cabanas WHERE name='"+nombreC+"'",null);
        idc.moveToFirst();
        ContentValues toCabanas = new ContentValues();
        toCabanas.put("name", nombreC);
        db.update("cabanas", toCabanas, "name ='" + nombreC + "'", null);
        ContentValues toAlquiladas = new ContentValues();
        String[] partsin = llegada.split("/");
        String[] partsout = salida.split("/");
        toAlquiladas.put("checkind", partsin[0]);
        toAlquiladas.put("checkinm", partsin[1]);
        toAlquiladas.put("checkoutd", partsout[0]);
        toAlquiladas.put("checkoutm", partsout[1]);
        db.update("Alquilada", toAlquiladas, "idc= " + idc.getInt(0), null);
        ContentValues toPersona = new ContentValues();
        toPersona.put("nombre",nombreP);
        int aux_dni = Integer.parseInt(dni);
        toPersona.put("dni", aux_dni);
        toPersona.put("email",email);
        db.update("Persona", toPersona, "dni =" + aux_dni, null);
        db.close();
        Toast.makeText(this,"Editado correctamente", Toast.LENGTH_LONG).show();
    }
}
