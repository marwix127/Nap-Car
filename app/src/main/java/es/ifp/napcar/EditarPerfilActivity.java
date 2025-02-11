package es.ifp.napcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EditarPerfilActivity extends AppCompatActivity {

    //Labels de la cabecera que cambian
    private TextView userName;
    private TextView vehiclesNumber;
    private TextView alertsNumber;

    //Label del perfil
    private TextView label1;
    private TextView label2;
    private TextView label3;
    private TextView label4;
    private TextView label5;

    //Cajas del perfil
    private EditText caja2;
    private EditText caja3;
    private EditText caja4;
    private EditText caja5;

    //Atributos para guardar la info
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;


    //Botones del menu de abajo
    private Button profileButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    //Botones de esta actividad
    private Button button6;
    private Button button7;


    //Para cambiar de actividad
    private Intent pasarPantalla;
    //Info del user
    private String[] user;
    //Gestion BBDD
    private DatabaseSQL db;
    private BasecitaDeDatos db2;
    protected BBDDVehiculos db3;

    private Bundle extras;
    private String[] paquete;
    private int userId=0;
    private ArrayList<String> userNameBBDD = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        db = new DatabaseSQL(EditarPerfilActivity.this);
        db2 = new BasecitaDeDatos(EditarPerfilActivity.this);
        db3 = new BBDDVehiculos(EditarPerfilActivity.this);


        userName = (TextView) findViewById(R.id.username_general);
        vehiclesNumber = (TextView) findViewById(R.id.vehiclesnumber_general);
        alertsNumber = (TextView) findViewById(R.id.alertsnumber_general);
        profileButton = (Button) findViewById(R.id.profilebutton_general);

        label1= (TextView) findViewById(R.id.label1_editarPerfil);
        label2= (TextView) findViewById(R.id.label2_editarPerfil);
        label3= (TextView) findViewById(R.id.label3_editarPerfil);
        label4= (TextView) findViewById(R.id.label4_editarPerfil);
        label5= (TextView) findViewById(R.id.label5_editarPerfil);

        caja2= (EditText) findViewById(R.id.caja2_editarPerfil);
        caja3= (EditText) findViewById(R.id.caja3_editarPerfil);
        caja4= (EditText) findViewById(R.id.caja4_editarPerfil);
        caja5= (EditText) findViewById(R.id.caja5_editarPerfil);

        //Botones
        button1= (Button) findViewById(R.id.vehiclebutton_general);
        button2= (Button) findViewById(R.id.garagebutton_general);
        button3= (Button) findViewById(R.id.homebutton_general);
        button4= (Button) findViewById(R.id.optionsbutton_general);
        button5= (Button) findViewById(R.id.helpbutton_general);

        button6= (Button) findViewById(R.id.button6_editarPerfil);
        button7= (Button) findViewById(R.id.button7_editarPerfil);

        try{
            extras = getIntent().getExtras();
            if(extras != null){
                paquete = new String[]{extras.getString("USERID")};
                userId = Integer.parseInt(paquete[0]);
                userNameBBDD = db.getUser(userId);
            }

            userName.setText(userNameBBDD.get(0));
            vehiclesNumber.setText(""+db3.numVehiculos(userId));
            alertsNumber.setText(Integer.toString(db2.getNumAlerts()));
            db2.closeBBDD();

            user = new String[3];
            //Recogemos la info del user en el array user
            user = db.getPerfil(userId);

            db.close();
        }catch(Exception e){
            Toast.makeText(this, "Ha ocurrido un error con la base de datos.", Toast.LENGTH_SHORT).show();
        }

        //Sacamos info por pantalla
        caja2.setText(user[1]);
        caja3.setText(user[2]);
        caja4.setText(user[0]);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(EditarPerfilActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        //Boton de vehiculos
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(EditarPerfilActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
//TODO        //Boton talleres
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(EditarPerfilActivity.this, TalleresActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Boton home
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(EditarPerfilActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

//TODO        //Boton opciones
       button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(EditarPerfilActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

//TODO        //Boton ayuda
       button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(EditarPerfilActivity.this, HelpActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(EditarPerfilActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Si las cajas obligatorias estan vacias
                if (caja2.getText().toString().equals("") || caja3.getText().toString().equals("") || caja4.getText().toString().equals(""))
                {
                    Toast.makeText(EditarPerfilActivity.this, "Completa todos los parametros con * del formulario", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Guardar la info de las cajas en los strings
                    nombre= caja2.getText().toString();
                    apellidos= caja3.getText().toString();
                    email= caja4.getText().toString();
                    telefono= caja5.getText().toString();

                    db.editarPerfil(email, nombre, apellidos, userId);
                    Toast.makeText(EditarPerfilActivity.this, "Perfil editado correctamente", Toast.LENGTH_SHORT).show();

                    pasarPantalla = new Intent(EditarPerfilActivity.this, PerfilUserActivity.class);
                    pasarPantalla.putExtra("USERID", Integer.toString(userId));
                    finish();
                    startActivity(pasarPantalla);
                }
            }
        });



    }
}