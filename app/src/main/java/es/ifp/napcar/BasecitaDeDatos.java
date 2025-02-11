package es.ifp.napcar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BasecitaDeDatos extends SQLiteOpenHelper {

    protected SQLiteDatabase db;

    public BasecitaDeDatos(Context context) {
        super(context, "pruebitas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS alertas (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, type TEXT, alert TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alertas");
    }

    @SuppressLint("Range")
    public ArrayList<String> readAllAlerts(){
        ArrayList<String> alerts = new ArrayList<String>();
        Cursor res=null;
        String contenido="";

        db = this.getReadableDatabase();
        res = db.rawQuery("SELECT * FROM alertas ORDER BY id ASC", null);
        res.moveToFirst();
        while(res.isAfterLast()==false){
            contenido = res.getInt(res.getColumnIndex("id")) + ".-" +
                    res.getString(res.getColumnIndex("alert"));
            alerts.add(contenido);
            res.moveToNext();
        }
        return alerts;
    }
    public void insertAlerts(String type, String text){
        db = this.getWritableDatabase();
        db.execSQL("INSERT INTO alertas (type, alert) VALUES ('" + type + "', '" + text +"')");
    }
    public void deleteAlerts(int id){
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM alertas WHERE id=" + id);
    }
    public void modifyAlert(int id, String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("alert", text);
        int rowsAffected = db.update("alertas", values, "id" + " = ?", new String[]{String.valueOf(id)});
        Log.d("ModifyAlert", "Filas afectadas: " + rowsAffected);
        db.close();
    }
    @SuppressLint("Range")
    public ArrayList<String> readAlert(int id){
        ArrayList<String> alerts = new ArrayList<String>();
        Cursor res = null;
        String contenido = "";
        String[] columns = {"id","alert"};
        String selection="id=?";
        String[] selectionArgs={String.valueOf(id)};

        db = this.getReadableDatabase();
        res = db.query("alertas", columns, selection, selectionArgs, null, null, null);
        if(res != null && res.moveToFirst()){
            contenido = res.getInt(res.getColumnIndex("id")) + ".-" +
                    res.getString(res.getColumnIndex("alert"));
            alerts.add(contenido);
        }
        return alerts;
    }

    public void closeBBDD(){
        db.close();
    }

    public int getNumAlerts(){
        db = this.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM alertas", null);
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return count;
    }

}
