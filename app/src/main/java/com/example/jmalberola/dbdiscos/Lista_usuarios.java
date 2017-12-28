package com.example.jmalberola.dbdiscos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Lista_usuarios extends Activity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemLongClickListener {
    private ListView lvUsuarios;
    private Spinner spinner;
    private MyDBAdapter dbAdapter;
    private int buttonId;
    private String[] spinnerFiltro;
    private String tipoUsuario;
    private String spinnerSelectedItem;
    private int usuarioSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        dbAdapter = new MyDBAdapter(this);
        dbAdapter.open();

        getButtonId();
        setTipoUsuario();
        getViews();
        setListeners();
        registerForContextMenu(lvUsuarios);
        preparaVista();
        setListeners();
        rellenarSpinner();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Eliminar");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Eliminar"){
            // Mostramos un mensaje para confirmar la eliminación del registro
            AlertDialog.Builder ad = new AlertDialog.Builder(Lista_usuarios.this);
            ad.setTitle("Eliminar registro");
            ad.setMessage("¿Seguro que quieres eliminar este registro?");

            ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbAdapter.borrarRegistro(usuarioSeleccionado);
                    if (buttonId == R.id.btnEstudiantesTodos || buttonId == R.id.btnProfesoresTodos || buttonId == R.id.btnTodos) {
                        preparaVista();
                    } else {
                        preparaLista();
                        rellenarSpinner();
                    }
                    Toast.makeText(getApplicationContext(),"Registro eliminado", Toast.LENGTH_SHORT).show();
                }
            });
            ad.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Cancelado", Toast.LENGTH_SHORT).show();
                }
            });
            ad.show();
            return false;
        }else{
            return true;
        }
    }

    private void getButtonId() {
        // Obtenemos el id del botón pulsado en la pantalla principal
        Intent intent = getIntent();
        buttonId = intent.getIntExtra("buttonId",0);
    }

    private void setTipoUsuario() {
        switch (buttonId) {
            case R.id.btnEstudiantesCiclo:
            case R.id.btnEstudiantesCurso:
            case R.id.btnEstudiantesCicloCurso:
            case R.id.btnEstudiantesTodos: tipoUsuario = NuevoRegistro.getTipoEstudiante(); break;
            case R.id.btnProfesoresCiclo:
            case R.id.btnProfesoresCurso:
            case R.id.btnProfesoresCicloCurso:
            case R.id.btnProfesoresTodos: tipoUsuario = NuevoRegistro.getTipoProfesor(); break;
            case R.id.btnTodos: tipoUsuario = null; break;
        }
    }

    private void getViews() {
        spinner = (Spinner) findViewById(R.id.spinner);
        lvUsuarios = (ListView) findViewById(R.id.list);
    }

    private void preparaVista() {
        if (buttonId == R.id.btnEstudiantesTodos || buttonId == R.id.btnProfesoresTodos || buttonId == R.id.btnTodos) {
            spinner.setVisibility(View.GONE);
            Cursor cursor = null;
            switch (buttonId) {
                case R.id.btnEstudiantesTodos:
                    cursor = dbAdapter.recuperarTodosEstudiantes(); break;
                case R.id.btnProfesoresTodos:
                    cursor = dbAdapter.recuperarTodosProfesores(); break;
                case R.id.btnTodos:
                    cursor = dbAdapter.recuperarTodos(); break;
            }
            cambiarAdapterListView(cursor);
        }
    }

    private void setListeners() {
        spinner.setOnItemSelectedListener(this);
        lvUsuarios.setOnItemLongClickListener(this);
    }

    private void rellenarSpinner() {
        if (spinner.getVisibility() == View.VISIBLE) {
            Cursor cursor;
            if (buttonId == R.id.btnEstudiantesCiclo || buttonId == R.id.btnProfesoresCiclo) {
                spinnerFiltro = new String[]{MyDBAdapter.getCICLO()};
                cursor = dbAdapter.recuperarCiclos(tipoUsuario);
            } else {
                spinnerFiltro = new String[]{MyDBAdapter.getCURSO()};
                cursor = dbAdapter.recuperarCursos(tipoUsuario);
            }
            int[] adapterRowViews = new int[]{android.R.id.text1};
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, spinnerFiltro, adapterRowViews, 0);
            sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(sca);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        setSelectedItem();
        preparaLista();
    }

    private void preparaLista() {
        Cursor cursor = null;
        switch (buttonId) {
            case R.id.btnEstudiantesCiclo:
                cursor = dbAdapter.recuperarEstudiantesCiclo(spinnerSelectedItem); break;
            case R.id.btnEstudiantesCurso:
                cursor = dbAdapter.recuperarEstudiantesCurso(spinnerSelectedItem); break;
            case R.id.btnEstudiantesCicloCurso: break;
            case R.id.btnProfesoresCiclo:
                cursor = dbAdapter.recuperarProfesoresCiclo(spinnerSelectedItem); break;
            case R.id.btnProfesoresCurso:
                cursor = dbAdapter.recuperarProfesoresCurso(spinnerSelectedItem); break;
            case R.id.btnProfesoresCicloCurso: break;
        }
        cambiarAdapterListView(cursor);
    }

    private void cambiarAdapterListView(Cursor cursor) {
        // Aplicamos el adaptador para el cursor obtenido
        UsuarioCursorAdapter usuariosAdapter = new UsuarioCursorAdapter(this, cursor);
        // Lo añadimos al ListView
        lvUsuarios.setAdapter(usuariosAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    private void setSelectedItem() {
        Cursor cursor = (Cursor) spinner.getSelectedItem();
        spinnerSelectedItem = cursor.getString(cursor.getColumnIndex(spinnerFiltro[0]));
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int myItemInt, long l) {
        Cursor cursor = (Cursor)(lvUsuarios.getItemAtPosition(myItemInt));
        usuarioSeleccionado = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
        return;
    }
}
