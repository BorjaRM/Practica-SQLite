package com.example.jmalberola.dbdiscos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Borja on 15/12/2017.
 */

public class Usuario implements Parcelable {
    private String tipo;
    private String nombre;
    private String edad;
    private String ciclo;
    private String curso;
    private String notaMedia;
    private String despacho;

    public Usuario(String tipo, String nombre, String edad, String ciclo, String curso, String notaMedia, String despacho) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.edad = edad;
        this.ciclo = ciclo;
        this.curso = curso;
        this.notaMedia = notaMedia;
        this.despacho = despacho;
    }

    protected Usuario(Parcel in) {
        tipo = in.readString();
        nombre = in.readString();
        edad = in.readString();
        ciclo = in.readString();
        curso = in.readString();
        notaMedia = in.readString();
        despacho = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tipo);
        dest.writeString(nombre);
        dest.writeString(edad);
        dest.writeString(ciclo);
        dest.writeString(curso);
        dest.writeString(notaMedia);
        dest.writeString(despacho);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(String notaMedia) {
        this.notaMedia = notaMedia;
    }

    public String getDespacho() {
        return despacho;
    }

    public void setDespacho(String despacho) {
        this.despacho = despacho;
    }
}