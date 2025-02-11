package es.ifp.napcar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Patterns;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class DatabaseSQL extends SQLiteOpenHelper{
    protected SQLiteDatabase db_users;

    public DatabaseSQL(Context context) {
        super(context, "db_users", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, email TEXT,password TEXT,name TEXT,lastname TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean registerUser(String email, String password, String name, String lastname) {
        //if(!emailExists(email)) {
            db_users = this.getWritableDatabase();
            db_users.execSQL("INSERT INTO users (email,password,name,lastname) VALUES ('" + email + "', '" + password + "', '" + name + "', '" + lastname + "')");
            return true;
       // }
       //return false;
    }

    public boolean loginUser(String email, String password){

        db_users = this.getReadableDatabase();
            Cursor cursor = db_users.rawQuery(
                    "SELECT * FROM users WHERE email = ? AND password = ?",
                    new String[]{email, password}
            );

            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
    }

    public boolean emailExists(String email){
        db_users = this.getReadableDatabase();
        Cursor cursor = db_users.rawQuery(
                "SELECT * FROM users WHERE email = '"+email+"'",null

        );

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
    public boolean esEmailValido(String email) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();
    }
    public void deleteAllUsers(){
        db_users = this.getWritableDatabase();
        db_users.execSQL("DELETE FROM users");
    }

    public void deleteUser(int id){
        db_users = this.getWritableDatabase();
        db_users.execSQL("DELETE FROM users WHERE id ="+id);
    }

    @SuppressLint("Range")
    public ArrayList<String> getUser(int id){
        ArrayList<String> userName = new ArrayList<String>();
        Cursor res = null;
        String contenido = "";
        String[] columns = {"name","lastname"};
        String selection="id=?";
        String[] selectionArgs={String.valueOf(id)};

        db_users = this.getReadableDatabase();
        res = db_users.query("users", columns, selection, selectionArgs, null, null, null);
        if(res != null && res.moveToFirst()){
            contenido = res.getString(res.getColumnIndex("name")) + " " +
                    res.getString(res.getColumnIndex("lastname"));
            userName.add(contenido);
        }
        return userName;
    }
    @SuppressLint("Range")
    public int getUserId(String email){
        int id=0;
        Cursor res = null;
        String[] columns = {"id"};
        String selection="email=?";
        String[] selectionArgs={String.valueOf(email)};

        db_users = this.getReadableDatabase();
        res = db_users.query("users", columns, selection, selectionArgs, null, null, null);
        if(res != null && res.moveToFirst()){
            id = res.getInt(res.getColumnIndex("id"));
        }
        return id;
    }

    //Funcion para ver info del perfil
    public String[] getPerfil(int idUser)
    {
        String[] perfil = new String[3];
        //Abrimos la tabla en modo lectura
        SQLiteDatabase db= this.getReadableDatabase();

        //Creamos cursor para leer la BBDD
        Cursor res=null;
        //Query  para ver la tabla entera
        res=db.rawQuery("SELECT email, name, lastname FROM users WHERE id = '"+idUser+"'", null);

        //Muevo el cursor a la primera linea de la tabla
        res.moveToFirst();

        //Metemos en el array lo que leemos en la fila diciendo que columnas quiero leer
        perfil[0]= res.getString(0);//email
        perfil[1]= res.getString(1);//name
        perfil[2]= res.getString(2);//lastname

        return perfil;
    }

    //Funcion para editar perfil
    public void editarPerfil(String email, String name, String lastname, int idUser)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE users SET email = '"+email+"', name = '"+name+"', lastname = '"+lastname+"' WHERE id = '"+idUser+"'");
    }

}
