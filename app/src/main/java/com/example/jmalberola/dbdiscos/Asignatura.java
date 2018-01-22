package com.example.jmalberola.dbdiscos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Borja on 22/01/2018.
 */

public class Asignatura{
    private String nombre;
    private String horas;

    public Asignatura(String nombre, String horas) {
        this.nombre = nombre;
        this.horas = horas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

}
