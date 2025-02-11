package es.ifp.napcar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BBDDVehiculos extends SQLiteOpenHelper {


    protected SQLiteDatabase db;

    public BBDDVehiculos(Context context) {
        super(context, "dbVehiculos", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS vehiculos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, marca TEXT, modelo TEXT, anio TEXT, motor TEXT, cv TEXT, km TEXT, matricula TEXT, combustible TEXT, itvDate TEXT, aceiteKm TEXT, aceiteDate TEXT, idUser TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS vehiculos");
        onCreate(db);
    }

    //marca, modelo, anio, motor, cv, km, matricula, combustible, itvDate, aceiteKm, aceiteDate
    //Funcion para registrar un vehiculo en la BBDD
    public void guardarVehiculo(String marca, String modelo, String anio, String motor, String cv, String km, String matricula, String combustible, String itvDate, String aceiteKm, String aceiteDate, int idUser){
        db = this.getWritableDatabase();
        db.execSQL("INSERT INTO vehiculos (marca, modelo, anio, motor, cv, km, matricula, combustible, itvDate, aceiteKm, aceiteDate, idUser) VALUES ('" + marca + "', '" + modelo +"', '" + anio +"', '" + motor +"', '" + cv +"', '" + km +"', '" + matricula +"', '" + combustible +"', '" + itvDate +"', '" + aceiteKm +"', '" + aceiteDate +"', '" +idUser+ "')");
    //TODO Hace falta un if?
    }

    /*
    //Funcion para leer toda la tabla
    @SuppressLint("Range")
    public ArrayList<String> leerVehiculos(){
        ArrayList<String> info = new ArrayList<String>();
        Cursor res=null;
        String contenido="";

        db = this.getReadableDatabase();
        res = db.rawQuery("SELECT * FROM vehiculos ORDER BY id ASC", null);
        res.moveToFirst();
        while(res.isAfterLast()==false){
            contenido = res.getInt(res.getColumnIndex("id")) + ".-" +
                    res.getString(res.getColumnIndex("marca"))+ ".-" +
                            res.getString(res.getColumnIndex("modelo"))+ ".-" +
                                    res.getString(res.getColumnIndex("anio"))+ ".-" +
                                            res.getString(res.getColumnIndex("motor"))+ ".-" +
                                                    res.getString(res.getColumnIndex("cv"))+ ".-" +
                                                            res.getString(res.getColumnIndex("km"))+ ".-" +
                                                                    res.getString(res.getColumnIndex("matricula"));
            info.add(contenido);
            res.moveToNext();
        }
        return info;
    }
    */

    //Funcion para ver un vehiculo
     public String[] leerVehiculo(String id)
    {
        //Creamos un arrayde dos de Strings para recoger la info de una fila de la tabla
        String[] car= new String[12];

        //Abrimos la tabla en modo lectura
        SQLiteDatabase db= this.getReadableDatabase();

        //Creamos cursor para leer la BBDD
        Cursor res=null;
        //Query  para ver la tabla entera
        res=db.rawQuery("SELECT * FROM vehiculos WHERE id = '"+id+"'", null);

        //Muevo el cursor a la primera linea de la tabla
        res.moveToFirst();

        //Metemos en el array lo que leemos en la fila diciendo que columnas quiero leer
        car[0]= res.getString(0);
        car[1]= res.getString(1);
        car[2]= res.getString(2);
        car[3]= res.getString(3);
        car[4]= res.getString(4);
        car[5]= res.getString(5);
        car[6]= res.getString(6);
        car[7]= res.getString(7);
        car[8]= res.getString(8);
        car[9]= res.getString(9);
        car[10]= res.getString(10);
        car[11]= res.getString(11);


        //Devuelvo el arraylist info
        return car;
    }
//TODO Esta funcion lee el primer vehiculo, habria que cambiar para tener mas vehiculos
    public String[] leerVehiculo(int idUser)
    {
        //Creamos un arrayde dos de Strings para recoger la info de una fila de la tabla
        String[] car= new String[12];

        //Abrimos la tabla en modo lectura
        SQLiteDatabase db= this.getReadableDatabase();

        //Creamos cursor para leer la BBDD
        Cursor res=null;

        //Query  para ver la tabla entera
        res=db.rawQuery("SELECT id, marca, modelo, anio, motor, cv, km, matricula, combustible, itvDate, aceiteKm, aceiteDate FROM vehiculos WHERE idUser = '"+idUser+"'", null);

        //Muevo el cursor a la primera linea de la tabla
        res.moveToFirst();

        if (res.isAfterLast())
        {
            car[0] = "-1";

        }
        else {

            //Metemos en el array lo que leemos en la fila diciendo que columnas quiero leer
            car[0] = res.getString(0);
            car[1] = res.getString(1);
            car[2] = res.getString(2);
            car[3] = res.getString(3);
            car[4] = res.getString(4);
            car[5] = res.getString(5);
            car[6] = res.getString(6);
            car[7] = res.getString(7);
            car[8] = res.getString(8);
            car[9] = res.getString(9);
            car[10] = res.getString(10);
            car[11] = res.getString(11);

        }

        //Devuelvo el arraylist info
        return car;
    }

    public void editarVehiculo(String marca, String modelo, String anio, String motor, String cv, String km, String matricula, String combustible, String itvDate, String aceiteKm, String aceiteDate, String id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE vehiculos SET marca = '"+marca+"', modelo = '"+modelo+"', anio = '"+anio+"', motor = '"+motor+"', cv = '"+cv+"', km = '"+km+"', matricula = '"+matricula+"', combustible = '"+combustible+"', itvDate = '"+itvDate+"', aceiteKm = '"+aceiteKm+"', aceiteDate = '"+aceiteDate+"' WHERE id = '"+id+"'");
    }

    public int numVehiculos(int idUser)
    {
        int nVehiculos=0;

        //Abrimos la tabla en modo lectura
        SQLiteDatabase db= this.getReadableDatabase();

        //Creamos cursor para leer la BBDD
        Cursor res=null;

        //Query  para ver la tabla entera
        res=db.rawQuery("SELECT * FROM vehiculos WHERE idUser = '"+idUser+"'", null);

        nVehiculos=res.getCount();

        return nVehiculos;
    }

    public void deleteAllVehicles(){
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM vehiculos");
    }


}
