package com.example.jmalberola.dbdiscos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBAdapter {
    // Definiciones y constantes
    private static final String DATABASE_NAME = "asignaturas.db";
    private static final String DATABASE_TABLE = "usuarios";
    private static final String DATABASE_TABLE_ASIG = "asignaturas";
    private static final int DATABASE_VERSION = 1;

    // Campos tabla usuarios
    private static final String ID = "_id";
    private static final String TIPO = "tipo";
    private static final String NOMBRE = "nombre";
    private static final String EDAD = "edad";
    private static final String CICLO = "ciclo";
    private static final String CURSO = "curso";
    private static final String NOTA_MEDIA = "nota_media";
    private static final String DESPACHO = "despacho";

    // Campos tabla asignaturas
    private static final String NOMBRE_ASIG = "nombre";
    private static final String HORAS_ASIG = "horas";

    // Instrucciones
    private static final String DATABASE_CREATE = "CREATE TABLE "+DATABASE_TABLE+" (_id integer primary key autoincrement, tipo text, nombre text, edad text, ciclo text, curso text, nota_media text, despacho text);";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS "+DATABASE_TABLE+"; DROP TABLE IF EXISTS "+DATABASE_TABLE_ASIG+";";
    private static final String DATABASE_CREATE_ASIG = "CREATE TABLE "+DATABASE_TABLE_ASIG+" (_id integer primary key autoincrement, nombre text, horas text);";

    // Queries
    private static String[] camposTabla;
    private static String where;
    private static String[] argsWhere;
    private static String groupBy;
    private static String having;
    private static String orderBy;

    // Contexto de la aplicación que usa la base de datos
    private final Context context;
    // Clase SQLiteOpenHelper para crear/actualizar la base de datos
    private MyDbHelper dbHelper;
    // Instancia de la base de datos
    private SQLiteDatabase db;

    private boolean datosCargados = false;

    public MyDBAdapter (Context c){
        context = c;
        dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        //OJO open();
    }

    // Este metodo nos permite abrir una instancia de la base de datos
    public void open(){
       try{
            db = dbHelper.getWritableDatabase();
        }catch(SQLiteException e){
            db = dbHelper.getReadableDatabase();
        }
    }


    public void insertarRegistro(Usuario u){
        //Creamos un nuevo registro de valores a insertar
        ContentValues newValues = new ContentValues();
        //Asignamos los valores de cada campo
        newValues.put(TIPO,u.getTipo());
        newValues.put(NOMBRE,u.getNombre());
        newValues.put(EDAD,u.getEdad());
        newValues.put(CICLO,u.getCiclo());
        newValues.put(CURSO,u.getCurso());
        newValues.put(NOTA_MEDIA,u.getNotaMedia());
        newValues.put(DESPACHO,u.getDespacho());
        db.insert(DATABASE_TABLE,null,newValues);
    }

    public void insertarAsignatura(Asignatura a){
        //Creamos un nuevo registro de valores a insertar
        ContentValues newValues = new ContentValues();
        //Asignamos los valores de cada campo
        newValues.put(NOMBRE_ASIG,a.getNombre());
        newValues.put(HORAS_ASIG,a.getHoras());
        db.insert(DATABASE_TABLE_ASIG,null,newValues);
    }

    public Cursor recuperarAsignaturas() {
        camposTabla = null;
        where = null;
        argsWhere = null;
        groupBy = null;
        having = null;
        orderBy = "nombre ASC";

        Cursor cursor = db.query(DATABASE_TABLE_ASIG, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }

    public void cargarDatosPrueba() {
        // Cargamos alumnos
        db.execSQL("INSERT INTO "+DATABASE_TABLE+" (tipo,nombre,edad,ciclo,curso,nota_media,despacho) VALUES " +
                "('estudiante','Carlos','20','DAM','1','7','null'),('estudiante','Alejandro','20','DAM','1','7','null')," +
                "('estudiante','Roberto','20','DAM','1','7','null'),('estudiante','Ana','20','DAM','2','7','null')," +
                "('estudiante','Paula','20','DAM','1','7','null'),('estudiante','Jorge','20','DAM','2','7','null')," +
                "('estudiante','Carla','20','DAW','1','7','null'),('estudiante','Tania','20','DAW','1','7','null')," +
                "('estudiante','Jesus','20','DAW','2','8','null'),('estudiante','Sara','20','DAW','1','7','null')," +
                "('estudiante','Andrea','20','TSMR','1','7','null'),('estudiante','Pedro','20','TSMR','1','7','null')," +
                "('estudiante','Carlos','20','TSMR','1','7','null'),('estudiante','Alejandra','20','TSMR','1','7','null')," +
                "('estudiante','Jose','20','TSMR','2','7','null'),('estudiante','Mercedes','20','TSMR','2','7','null')," +
                "('estudiante','Belen','20','ASIR','1','7','null'),('estudiante','Jaime','20','ASIR','1','7','null')," +
                "('estudiante','Marcos','20','ASIR','1','7','null'),('estudiante','Francisco','20','ASIR','2','7','null')  ");

        // Cargamos profesores
        db.execSQL("INSERT INTO "+DATABASE_TABLE+" (tipo,nombre,edad,ciclo,curso,nota_media,despacho) VALUES " +
                "('profesor','Belen','20','DAM','1','null','1A'),('profesor','Juan Miguel','20','DAM','2','null','1A')," +
                "('profesor','Paco','20','DAW','1','null','1B'),('profesor','Toni','20','DAW','2','null','1B')," +
                "('profesor','Manel','20','TSMR','1','null','1C'),('profesor','Raquel','20','TSMR','2','null','1C')," +
                "('profesor','Manuel','20','ASIR','1','null','2A'),('profesor','Tomas','20','ASIR','2','null','2A')  ");
    }

    /*public ArrayList<Usuario> recuperarEstudiantesCiclo() {
        ArrayList<Usuario> estudiantesCiclo = new ArrayList<Usuario>();

        // Recuperamos en un cursor la consulta realizada
        Cursor cursor = db.query(DATABASE_TABLE,null,null,null,null,null,null);

        // Recorremos el cursor
        if (cursor != null & cursor.moveToFirst()) {
            do {
                String tipo = cursor.getString(1);
                String nombre = cursor.getString(2);
                String edad = cursor.getString(3);
                String ciclo = cursor.getString(4);
                String curso = cursor.getString(5);
                String nota = cursor.getString(6);
                // Añadimos el estudiante al array
                estudiantesCiclo.add(new Usuario(tipo,nombre,edad,ciclo,curso,nota,null));
            } while(cursor.moveToNext());
        }
        return estudiantesCiclo;
    }*/

    /* db.query(DATABASE_TABLE,CamposTabla,WHERE,argsWhere,GROUPby,HAVING,ORDERby); */

    public Cursor recuperarCiclos(String tipoUsuario) {
        camposTabla = new String[]{ID,CICLO};
        where = TIPO+"=?";
        argsWhere = new String[]{tipoUsuario};
        groupBy = CICLO;
        having = null;
        orderBy = "ciclo ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }

    public Cursor recuperarCursos(String tipoUsuario) {
        camposTabla = new String[]{ID,CURSO};
        where = TIPO+"=?";
        argsWhere = new String[]{tipoUsuario};
        groupBy = CURSO;
        having = null;
        orderBy = "curso ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }

    /*public Cursor recuperarCiclosYCursos(String tipoUsuario) {
        camposTabla = new String[]{ID,CICLO,CURSO};
        where = TIPO+"=?";
        argsWhere = new String[]{tipoUsuario};
        groupBy = null;
        having = null;
        orderBy = "curso ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }*/

    public Cursor recuperarEstudiantesCiclo(String ciclo) {
        camposTabla = null;
        where = TIPO+"=? AND "+CICLO+"=?";
        argsWhere = new String[]{NuevoRegistro.getTipoEstudiante(), ciclo};
        groupBy = null;
        having = null;
        orderBy = "nombre ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }

    public Cursor recuperarEstudiantesCurso(String curso) {
        camposTabla = null;
        where = TIPO+"=? AND "+CURSO+"=?";
        argsWhere = new String[]{NuevoRegistro.getTipoEstudiante(), curso};
        groupBy = null;
        having = null;
        orderBy = "nombre ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }

    public Cursor recuperarTodosEstudiantes() {
        camposTabla = null;
        where = TIPO+"=?";
        argsWhere = new String[]{NuevoRegistro.getTipoEstudiante()};
        groupBy = null;
        having = null;
        orderBy = "nombre ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, null);
        return cursor;
    }

    public Cursor recuperarProfesoresCiclo(String ciclo) {
        camposTabla = null;
        where = TIPO+"=? AND "+CICLO+"=?";
        argsWhere = new String[]{NuevoRegistro.getTipoProfesor(), ciclo};
        groupBy = null;
        having = null;
        orderBy = "nombre ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }

    public Cursor recuperarProfesoresCurso(String curso) {
        camposTabla = null;
        where = TIPO+"=? AND "+CURSO+"=?";
        argsWhere = new String[]{NuevoRegistro.getTipoProfesor(), curso};
        groupBy = null;
        having = null;
        orderBy = "nombre ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }

    public Cursor recuperarTodosProfesores() {
        camposTabla = null;
        where = TIPO+"=?";
        argsWhere = new String[]{NuevoRegistro.getTipoProfesor()};
        groupBy = null;
        having = null;
        orderBy = "nombre ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }

    public Cursor recuperarTodos() {
        camposTabla = null;
        where = null;
        argsWhere = null;
        groupBy = null;
        having = null;
        orderBy = "nombre ASC";

        Cursor cursor = db.query(DATABASE_TABLE, camposTabla, where, argsWhere, groupBy, having, orderBy);
        return cursor;
    }

    public void borrarRegistro(int row) {
        where = "_id=?";
        argsWhere = new String[]{String.valueOf(row) };
        db.delete(DATABASE_TABLE, where, argsWhere);
    }

    public void borrarDb() {
        dbHelper.onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION);
    }

    private static class MyDbHelper extends SQLiteOpenHelper {
        public MyDbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context,name,factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_ASIG);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DATABASE_DROP);
            onCreate(db);
        }
    }

    public static String getCICLO() {
        return CICLO;
    }

    public static String getCURSO() {
        return CURSO;
    }
}