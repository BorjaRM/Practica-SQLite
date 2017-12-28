package com.example.jmalberola.dbdiscos;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class UsuarioCursorAdapter extends CursorAdapter {

    public UsuarioCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        return LayoutInflater.from(context).inflate(R.layout.row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Obtenemos los views
        TextView txtId = (TextView) view.findViewById(R.id.lvId);
        TextView txtNombre = (TextView) view.findViewById(R.id.lvNombre);
        TextView txtEdad = (TextView) view.findViewById(R.id.lvEdad);
        TextView txtCiclo = (TextView) view.findViewById(R.id.lvCiclo);
        TextView txtCurso = (TextView) view.findViewById(R.id.lvCurso);
        TextView txtNota = (TextView) view.findViewById(R.id.lvNota);
        TextView txtDespacho = (TextView) view.findViewById(R.id.lvDespacho);

        // Obtenemos la información del cursor
        String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
        String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
        String edad = cursor.getString(cursor.getColumnIndexOrThrow("edad"));
        String ciclo = cursor.getString(cursor.getColumnIndexOrThrow("ciclo"));
        String curso = cursor.getString(cursor.getColumnIndexOrThrow("curso"));
        String nota = cursor.getString(cursor.getColumnIndexOrThrow("nota_media"));
        String despacho = cursor.getString(cursor.getColumnIndexOrThrow("despacho"));

        // Rellenamos los views con la información obtenida del cursor
        txtId.setText(id);
        txtNombre.setText(nombre);
        txtEdad.setText(edad + " años");
        txtCiclo.setText("Ciclo: " + ciclo);
        txtCurso.setText("Curso: " + curso);
        txtNota.setText("Nota media: " + nota);
        txtDespacho.setText("Despacho: " + despacho);

        if (tipo.equalsIgnoreCase(NuevoRegistro.getTipoProfesor())) {
            txtNota.setVisibility(View.GONE);
            txtDespacho.setVisibility(View.VISIBLE);
        } else {
            txtNota.setVisibility(View.VISIBLE);
            txtDespacho.setVisibility(View.GONE);
        }
    }
}
