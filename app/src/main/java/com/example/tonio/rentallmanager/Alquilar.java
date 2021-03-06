package com.example.tonio.rentallmanager;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alquilar extends AppCompatActivity {
    public EditText nombre, apellido, etDni, llegada, salida, email;
    public Button btn;
    public DatePickerDialog llega, sale;
    public Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquilar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nombre = (EditText) findViewById(R.id.etNom);
        apellido = (EditText) findViewById(R.id.etApe);
        llegada = (EditText) findViewById(R.id.etLlegada);
        salida = (EditText) findViewById(R.id.etSalida);
        email = (EditText) findViewById(R.id.etEmail);
        btn = (Button) findViewById(R.id.btn);
        etDni = (EditText)findViewById(R.id.etDNI);

        llegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final Calendar calendarL = Calendar.getInstance();
                llega = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        calendarL.set(Calendar.YEAR, i);
                        calendarL.set(Calendar.MONTH, i1);
                        calendarL.set(Calendar.DAY_OF_MONTH, i2);

                        String fecha = DateUtils.formatDateTime(view.getContext(), calendarL.getTimeInMillis(), DateUtils.FORMAT_NUMERIC_DATE);

                        String[] tmpArray = fecha.split("/");
                        String ret = "";

                        String tmp = tmpArray[0];
                        tmpArray[0] = tmpArray[1];
                        tmpArray[1] = tmp;

                        for (int j = 0; j < tmpArray.length; j++) {
                            ret += tmpArray[j];
                            if (!(j == tmpArray.length - 1)) {
                                ret += "/";

                            }
                        }
                        llegada.setText(ret);

                    }
                }, calendarL.get(Calendar.YEAR), calendarL.get(Calendar.MONTH), calendarL.get(Calendar.DAY_OF_MONTH));

                llega.show();
            }


        });


        salida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final Calendar calendarS = Calendar.getInstance();
                sale = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        calendarS.set(Calendar.YEAR, i);
                        calendarS.set(Calendar.MONTH, i1);
                        calendarS.set(Calendar.DAY_OF_MONTH, i2);

                        String fecha = DateUtils.formatDateTime(view.getContext(), calendarS.getTimeInMillis(), DateUtils.FORMAT_NUMERIC_DATE);

                        String[] tmpArray = fecha.split("/");
                        String ret = "";

                        String tmp = tmpArray[0];
                        tmpArray[0] = tmpArray[1];
                        tmpArray[1] = tmp;

                        for (int j = 0; j < tmpArray.length; j++) {
                            ret += tmpArray[j];
                            if (!(j == tmpArray.length - 1)) {
                                ret += "/";

                            }
                        }
                        salida.setText(ret);
                        actualizarSpinner();
                    }
                }, calendarS.get(Calendar.YEAR), calendarS.get(Calendar.MONTH), calendarS.get(Calendar.DAY_OF_MONTH));
                sale.show();
            }
        });
    }


    public void cargar(View view) {
        //Abrimos la base de datos y le pedimos las tablas.
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        //Get a las variables para poder guardarlas.
        String name = nombre.getText().toString() +"/"+ apellido.getText().toString() ;
        String mail = email.getText().toString();
        int dni = Integer.parseInt(etDni.getText().toString());
        String search = spinner1.getSelectedItem().toString();
        System.out.println(search);
        Cursor fil = db.rawQuery("select idc from cabanas where name='" + search + "'", null);
        fil.moveToFirst();
        String id = fil.getString(0);
        int aux_id = Integer.parseInt(id);

        ContentValues toAlquilada = new ContentValues();
        toAlquilada.put("idc", aux_id);
        String toSplit = llegada.getText().toString();
        String[] parts = toSplit.split("/");
        toAlquilada.put("checkind",Integer.parseInt(parts[0]));
        toAlquilada.put("checkinm",Integer.parseInt(parts[1]));
        String toSplits = salida.getText().toString();
        String[] partss = toSplits.split("/");
        toAlquilada.put("checkoutd", Integer.parseInt(partss[0]));
        toAlquilada.put("checkoutm", Integer.parseInt(partss[1]));
        db.insert("Alquiladas", null, toAlquilada);

        System.out.println("Esto tiene toAlquilada --->  " + toAlquilada.toString());

        ContentValues toPersona = new ContentValues();
        toPersona.put("idc", aux_id);
        toPersona.put("dni", dni);
        toPersona.put("nombre", name);
        toPersona.put("email", mail);
        db.insert("Persona", null, toPersona);
        System.out.println("Esto tiene toPersona ---> " + toPersona.toString());

        db.close();
        nombre.setText("");
        apellido.setText("");
        email.setText("");
        etDni.setText("");
        llegada.setText("");
        salida.setText("");
        Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_LONG).show();
        System.out.println(toPersona.toString());
        System.out.println(toAlquilada.toString());
        actualizarSpinner();
    }

    public void actualizarSpinner() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        List<String> labels = new ArrayList<>();
        spinner1 = (Spinner) findViewById(R.id.spinner);
        String toSplit1 = llegada.getText().toString();
        String[] parts1 = toSplit1.split("/");
        String toSplit = salida.getText().toString();
        String [] parts = toSplit.split("/");
        Cursor fila = db.rawQuery("SELECT name FROM cabanas WHERE idc NOT IN (SELECT idc FROM Alquiladas WHERE checkind BETWEEN '"+ parts[0]+"'"+ " AND '"+parts1[0]+"'"+" AND "+ "checkinm BETWEEN '"+  parts[1] +"'"+" AND '"+parts1[1]+"'"+")",null);
        if (fila.moveToFirst()) {
            do {
                labels.add(fila.getString(0));
            } while (fila.moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        fila.close();
        db.close();
    }

}

