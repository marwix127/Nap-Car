package es.ifp.napcar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import es.ifp.napcar.R;
import es.ifp.napcar.service.BBDDVehiculos;
import es.ifp.napcar.service.BaseDeDatos;
import es.ifp.napcar.service.DatabaseSQL;

public class VehiculosActivity extends AppCompatActivity {

    //Background de la info de vehiculo
    private TextView background3;
    //Labels de la cabecera que cambian
    private TextView userName;
    private TextView vehiclesNumber;
    private TextView alertsNumber;
    //Labels de la activity
    private TextView label6;
    private TextView label7;
    private TextView label8;
    private TextView label9;
    private TextView label10;
    private TextView label11;
    private TextView label12;
    private TextView label13;
    private TextView label14;
    private TextView label15;
    private TextView label16;
    private TextView label17;
    private TextView label18;
    private TextView label19;
    private TextView label20;

    //Imagenes del cuadro
    private ImageView img6;
    private ImageView img7;

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

    //Gestion BBDD
    protected DatabaseSQL db;
    protected BaseDeDatos db2;
    protected BBDDVehiculos db3;
    protected String[] vehiculo = new String[11];

    //Paquetes para enviar
    private Bundle extras;
    private String[] paquete;
    private int userId=0;
    private ArrayList<String> userNameBBDD = new ArrayList<String>();
    private String vehiculoId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);

        //Inicializamos BBDD
        db = new DatabaseSQL(VehiculosActivity.this);
        db2 = new BaseDeDatos(VehiculosActivity.this);
        db3 = new BBDDVehiculos(VehiculosActivity.this);

        //Limpieza de tablas
        //db.deleteAllUsers();
        //db3.deleteAllVehicles();


        background3= (TextView) findViewById(R.id.background3_vehiculos);

        userName = (TextView) findViewById(R.id.username_general);
        vehiclesNumber = (TextView) findViewById(R.id.vehiclesnumber_general);
        alertsNumber = (TextView) findViewById(R.id.alertsnumber_general);
        profileButton = (Button) findViewById(R.id.profilebutton_general);

        label6= (TextView) findViewById(R.id.label6_vehiculos);
        label7= (TextView) findViewById(R.id.label7_vehiculos);
        label8= (TextView) findViewById(R.id.label8_vehiculos);
        label9= (TextView) findViewById(R.id.label9_vehiculos);
        label10= (TextView) findViewById(R.id.label10_vehiculos);
        label11= (TextView) findViewById(R.id.label11_vehiculos);
        label12= (TextView) findViewById(R.id.label12_vehiculos);
        label13= (TextView) findViewById(R.id.label13_vehiculos);
        label14= (TextView) findViewById(R.id.label14_vehiculos);
        label15= (TextView) findViewById(R.id.label15_vehiculos);
        label16= (TextView) findViewById(R.id.label16_vehiculos);
        label17= (TextView) findViewById(R.id.label17_vehiculos);
        label18= (TextView) findViewById(R.id.label18_vehiculos);
        label19= (TextView) findViewById(R.id.label19_vehiculos);
        label20= (TextView) findViewById(R.id.label20_vehiculos);
        //Imagenes
        img6= (ImageView) findViewById(R.id.imageView6_vehiculos);
        img7= (ImageView) findViewById(R.id.imageView7_vehiculos);

        //Botones
        button1= (Button) findViewById(R.id.vehiclebutton_general);
        button2= (Button) findViewById(R.id.garagebutton_general);
        button3= (Button) findViewById(R.id.homebutton_general);
        button4= (Button) findViewById(R.id.optionsbutton_general);
        button5= (Button) findViewById(R.id.helpbutton_general);

        button6= (Button) findViewById(R.id.button6_vehiculos);
        button7= (Button) findViewById(R.id.button7_vehiculos);

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
            db.close();
        }catch(Exception e){
            Toast.makeText(this, "Ha ocurrido un error con la base de datos.", Toast.LENGTH_SHORT).show();
        }

//TODO Coger info de los vehiculos de la
        vehiculo = db3.leerVehiculo(userId);
        if (vehiculo[0]=="-1")
        {
            //Pasamos a invisible el cuadro de info entero
           label6.setVisibility(View.GONE);
           label7.setVisibility(View.GONE);
           label8.setVisibility(View.GONE);
           label9.setVisibility(View.GONE);
           label10.setVisibility(View.GONE);
           label11.setVisibility(View.GONE);
           label12.setVisibility(View.GONE);
           label13.setVisibility(View.GONE);
           label14.setVisibility(View.GONE);
           label15.setVisibility(View.GONE);
           label16.setVisibility(View.GONE);
           label17.setVisibility(View.GONE);
           label18.setVisibility(View.GONE);
           label19.setVisibility(View.GONE);
           label20.setVisibility(View.GONE);

           img6.setVisibility(View.GONE);
           img7.setVisibility(View.GONE);
           button6.setVisibility(View.GONE);

           background3.setVisibility(View.GONE);

        }
        //Existe algun vehiculo en la BBDD
        else
        {
            // Sacarlo por pantalla en los labels

            label7.setText(vehiculo[1]);//Marca
            label9.setText(vehiculo[2]);//Modelo
            label10.setText(vehiculo[6]);//Km
            label12.setText(vehiculo[4]);//Motor
            label14.setText(vehiculo[5]);//Potencia
            label18.setText(vehiculo[7]);//Matricula
            label20.setText(vehiculo[3]);//Año

        }

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(VehiculosActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() { //Botón Vehículos
            @Override
            public void onClick(View v) {
                /*pasarPantalla = new Intent (VehiculosActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);*/
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { //Botón Talleres
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (VehiculosActivity.this, TalleresActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() { //Botón Home
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (VehiculosActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() { //Botón Opciones
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (VehiculosActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() { //Botón Ayuda
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(VehiculosActivity.this, HelpActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Boton6 de informacion detallada de vehiculo
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recojo el id del vehiculo para enviarlo a la otra pantalla
                vehiculoId= vehiculo[0];
                //System.out.println("---->Idseleccionado"+idSeleccionado);
//TODO enviar paquete con el id del vehiculo a la pantalla infovehiculos
                pasarPantalla = new Intent(VehiculosActivity.this, InfoVehiculosActivity.class);
                //Mandamos el paquete de info
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                pasarPantalla.putExtra("ID", vehiculoId);
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Boton7 de registrar nuevo vehiculo
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(VehiculosActivity.this, RegistroVehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

    }
}