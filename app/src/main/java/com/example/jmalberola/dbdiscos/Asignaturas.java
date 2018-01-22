package com.example.jmalberola.dbdiscos;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Asignaturas extends Activity implements View.OnClickListener{
    private MyDBAdapter dbAdapter;
    private Button btnGuardar, btnVer;
    private EditText nombre, horas;
    private ListView lista_asig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturas);

        dbAdapter = new MyDBAdapter(this);
        dbAdapter.open();

        getViews();
        setListeners();

    }

    public void getViews() {
        btnGuardar = (Button) findViewById(R.id.btn1);
        btnVer = (Button) findViewById(R.id.btn2);
        nombre = (EditText) findViewById(R.id.txtNombreAs);
        horas = (EditText) findViewById(R.id.txtHorasAs);
        lista_asig = (ListView) findViewById(R.id.lista_asignaturas);
    }

    public void setListeners() {
        btnGuardar.setOnClickListener(this);
        btnVer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnGuardar.getId()) {
            guardarAsignatura();
        } else if (view.getId() == btnVer.getId()) {
            verAsignaturas();
        }
    }

    private void guardarAsignatura() {
        if (nombre.length() > 0) {
            if (horas.length() > 0) {
                Asignatura asig = new Asignatura(nombre.getText().toString(),horas.getText().toString());
                dbAdapter.insertarAsignatura(asig);
            } else
                horas.setError("Debe introducir un numero de horas");
        } else
            nombre.setError("Debe introducir un nombre");

    }

    private void verAsignaturas() {
        Cursor cursor = null;
        cursor = dbAdapter.recuperarAsignaturas();
        AsignaturaCursorAdapter adapter = new AsignaturaCursorAdapter(this, cursor);
        // Lo añadimos al ListView
        lista_asig.setAdapter(adapter);
    }
}
