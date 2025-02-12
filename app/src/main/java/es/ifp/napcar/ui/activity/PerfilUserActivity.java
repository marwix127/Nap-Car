package es.ifp.napcar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import es.ifp.napcar.R;
import es.ifp.napcar.service.BBDDVehiculos;
import es.ifp.napcar.service.BaseDeDatos;
import es.ifp.napcar.service.DatabaseSQL;

public class PerfilUserActivity extends AppCompatActivity {
    //Labels de la cabecera que cambian
    private TextView userName;
    private TextView vehiclesNumber;
    private TextView alertsNumber;

    private TextView label1;
    private TextView label2;
    private TextView label3;
    private TextView label4;
    private TextView label5;
    private TextView label6;
    private TextView label7;
    private TextView label8;
    private TextView label9;

    //Botones del menu de abajo
    private Button profileButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    //Botones de esta actividad
    private Button button6;

    //Para cambiar de actividad
    private Intent pasarPantalla;
    //Info del user
    private String[] user;
    //Gestion BBDD
    private DatabaseSQL db;
    private BaseDeDatos db2;
    protected BBDDVehiculos db3;

    private Bundle extras;
    private String[] paquete;
    private int userId=0;
    private ArrayList<String> userNameBBDD = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        db = new DatabaseSQL(PerfilUserActivity.this);
        db2 = new BaseDeDatos(PerfilUserActivity.this);
        db3 = new BBDDVehiculos(PerfilUserActivity.this);


        userName = (TextView) findViewById(R.id.username_general);
        vehiclesNumber = (TextView) findViewById(R.id.vehiclesnumber_general);
        alertsNumber = (TextView) findViewById(R.id.alertsnumber_general);
        profileButton = (Button) findViewById(R.id.profilebutton_general);

        label1= (TextView) findViewById(R.id.label1_perfilUser);
        label2= (TextView) findViewById(R.id.label2_perfilUser);
        label3= (TextView) findViewById(R.id.label3_perfilUser);
        label4= (TextView) findViewById(R.id.label4_perfilUser);
        label5= (TextView) findViewById(R.id.label5_perfilUser);
        label6= (TextView) findViewById(R.id.label6_perfilUser);
        label7= (TextView) findViewById(R.id.label7_perfilUser);
        label8= (TextView) findViewById(R.id.label8_perfilUser);
        label9= (TextView) findViewById(R.id.label9_perfilUser);

        //Botones
        button1= (Button) findViewById(R.id.vehiclebutton_general);
        button2= (Button) findViewById(R.id.garagebutton_general);
        button3= (Button) findViewById(R.id.homebutton_general);
        button4= (Button) findViewById(R.id.optionsbutton_general);
        button5= (Button) findViewById(R.id.helpbutton_general);

        button6= (Button) findViewById(R.id.button6_perfilUser);

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
            db3.close();
            db2.closeBBDD();

            user = new String[3];
            //Recogemos la info del user en el array user
            user = db.getPerfil(userId);

            db.close();
        }catch(Exception e){
            Toast.makeText(this, "Ha ocurrido un error con la base de datos.", Toast.LENGTH_SHORT).show();
        }

        //Sacamos info por pantalla
        label3.setText(user[1]);
        label5.setText(user[2]);
        label7.setText(user[0]);
        label9.setText("N/S");

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(PerfilUserActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        //Boton de vehiculos
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(PerfilUserActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
//TODO        //Boton talleres
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(PerfilUserActivity.this, TalleresActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Boton home
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(PerfilUserActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

//TODO        //Boton opciones
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(PerfilUserActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

//TODO        //Boton ayuda
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(PerfilUserActivity.this, HelpActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Boton6 Editar Perfil
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//TODO es otra activity a donde tiene que ir
                pasarPantalla = new Intent(PerfilUserActivity.this, EditarPerfilActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });



    }
}