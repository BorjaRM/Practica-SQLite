package com.example.jmalberola.dbdiscos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class NuevoRegistro extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    private TextView notaMedia, despacho, msjPrincipal;
    private EditText txtNota, txtDespacho, txtNombre, txtEdad, txtCiclo, txtCurso;
    private ToggleButton tgButton;
    private Button btnGuardar, btnCancelar;
    private String tipo, nombre, edad, ciclo, curso, nota, des;

    private final String ON_ALUMNO ="CAMBIAR A NUEVO ALUMNO";
    private final String OFF_PROFESOR = "CAMBIAR A NUEVO PROFESOR";

    private final String MSJ_ALUMNO ="Introduzca los datos para el nuevo alumno";
    private final String MSJ_PROFESOR ="Introduzca los datos para el nuevo profesor";

    private static final String TIPO_ESTUDIANTE = "estudiante";
    private static final String TIPO_PROFESOR = "profesor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_registro);

        getViews();
        preparaVista();
        setListeners();
    }

    // Prepara la vista por defecto (Alumno seleccionado)
    private void preparaVista() {
        // Muestra el texto referente al alumno en el toggleButton y el mensaje principal
        tgButton.setText(ON_ALUMNO);
        msjPrincipal.setText(MSJ_PROFESOR);
        // Establece el texto a mostrar en el toggleButton para On/Off
        tgButton.setTextOn(OFF_PROFESOR);
        tgButton.setTextOff(ON_ALUMNO);
    }

    // Obtenemos la referencia a los views
    private void getViews() {
        tgButton = (ToggleButton) findViewById(R.id.toggleButton);
        notaMedia = (TextView) findViewById(R.id.tvNota);
        despacho = (TextView) findViewById(R.id.tvDespacho);
        txtNota = (EditText) findViewById(R.id.txtNota);
        txtDespacho = (EditText) findViewById(R.id.txtDespacho);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        txtCiclo = (EditText) findViewById(R.id.txtCiclo);
        txtCurso = (EditText) findViewById(R.id.txtCurso);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        msjPrincipal = (TextView) findViewById(R.id.msj);
    }

    // Establecemos los listeners necesarios
    private void setListeners() {
        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        tgButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnGuardar.getId()) {
            getInformacionGeneral();
            if (tgButton.isChecked()) { // Alumno seleccionado
                tipo = TIPO_ESTUDIANTE;
                getInformacionAlumno();
            } else {
                tipo = TIPO_PROFESOR;
                getInformacionProfesor();
            }
            Usuario u = new Usuario(tipo,nombre,edad,ciclo,curso,nota,des); // Creamos un nuevo usuario

            Intent returnIntent = new Intent();
            returnIntent.putExtra("usuario",u);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();

        } else if (v.getId() == btnCancelar.getId()){
            finish(); // Cerramos este Activity y volvemos al MainActivity
        }
    }

    // Detecta cuando alternamos entre Alumno y Profesor
    @Override
    public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
        showAlumnoOrProfesorViews();
        cambiarMensajePrincipal();
    }

    // Modificamos la vista en función de si esta seleccionado Alumno o Profesor
    private void showAlumnoOrProfesorViews() {
        notaMedia.setVisibility(notaMedia.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        txtNota.setVisibility(txtNota.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        despacho.setVisibility(despacho.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        txtDespacho.setVisibility(txtDespacho.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private void cambiarMensajePrincipal() {
        msjPrincipal.setText(tgButton.getText() == OFF_PROFESOR ? MSJ_PROFESOR : MSJ_ALUMNO);
    }

    private void getInformacionGeneral() {
        nombre = txtNombre.getText().toString();
        edad = txtEdad.getText().toString();
        ciclo = txtCiclo.getText().toString();
        curso = txtCurso.getText().toString();
    }

    private void getInformacionAlumno() {
        nota = txtNota.getText().toString();
        des = null;
    }

    private void getInformacionProfesor() {
        des = txtDespacho.getText().toString();
        nota = null;
    }

    public static String getTipoEstudiante() {
        return TIPO_ESTUDIANTE;
    }

    public static String getTipoProfesor() {
        return TIPO_PROFESOR;
    }
}
