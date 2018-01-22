package com.example.jmalberola.dbdiscos;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AsignaturaCursorAdapter extends CursorAdapter {

    public AsignaturaCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_asig, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Obtenemos los views
        TextView txtNombre = (TextView) view.findViewById(R.id.nomAsig);
        TextView txtHoras = (TextView) view.findViewById(R.id.horasAsig);

        // Obtenemos la información del cursor
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
        String horas = cursor.getString(cursor.getColumnIndexOrThrow("horas"));

        // Rellenamos los views con la información obtenida del cursor
        txtNombre.setText(nombre);
        txtHoras.setText(horas);
    }
}
