package com.example.jmalberola.dbdiscos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button btnEstudiantesCiclo, btnEstudiantesCurso, btnEstudiantesCicloCurso, btnEstudiantesTodos,
            btnProfesoresCiclo, btnProfesoresCurso, btnProfesoresCicloCurso, btnProfesoresTodos, btnTodos;

    private MyDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new MyDBAdapter(this);
        dbAdapter.open();

        getViews();
        setListeners();

        // Este metodo carga algunos datos de prueba para ver el funcionamiento de la aplicacion
        //dbAdapter.cargarDatosPrueba();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getViews() {
        btnEstudiantesCiclo = (Button) findViewById(R.id.btnEstudiantesCiclo);
        btnEstudiantesCurso = (Button) findViewById(R.id.btnEstudiantesCurso);
        btnEstudiantesCicloCurso = (Button) findViewById(R.id.btnEstudiantesCicloCurso);
        btnEstudiantesTodos = (Button) findViewById(R.id.btnEstudiantesTodos);
        btnProfesoresCiclo = (Button) findViewById(R.id.btnProfesoresCiclo);
        btnProfesoresCurso = (Button) findViewById(R.id.btnProfesoresCurso);
        btnProfesoresCicloCurso = (Button) findViewById(R.id.btnProfesoresCicloCurso);
        btnProfesoresTodos = (Button) findViewById(R.id.btnProfesoresTodos);
        btnTodos = (Button) findViewById(R.id.btnTodos);
    }

    public void setListeners() {
        btnEstudiantesCiclo.setOnClickListener(this);
        btnEstudiantesCurso.setOnClickListener(this);
        btnEstudiantesCicloCurso.setOnClickListener(this);
        btnEstudiantesTodos.setOnClickListener(this);
        btnProfesoresCiclo.setOnClickListener(this);
        btnProfesoresCurso.setOnClickListener(this);
        btnProfesoresCicloCurso.setOnClickListener(this);
        btnProfesoresTodos.setOnClickListener(this);
        btnTodos.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nuevo_registro:
                Intent nuevoRegistro = new Intent(getApplicationContext(), NuevoRegistro.class);
                startActivityForResult(nuevoRegistro, 1);
                return true;
            case R.id.eliminar_bd:
                // Mostramos un mensaje para confirmar la eliminación de la base de datos
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setTitle("Eliminar base de datos");
                ad.setMessage("¿Seguro que quieres eliminarla, no podrás recuperar la información?");

                ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbAdapter.borrarDb();
                        Toast.makeText(getApplicationContext(),"Base de datos eliminada", Toast.LENGTH_SHORT).show();
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Usuario u = (Usuario) data.getParcelableExtra("usuario");
                dbAdapter.insertarRegistro(u);
            }
            if (resultCode == Activity.RESULT_CANCELED) { }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intentListaUsuarios = new Intent(getApplicationContext(), Lista_usuarios.class);
        intentListaUsuarios.putExtra("buttonId",v.getId());
        startActivity(intentListaUsuarios);
    }
}