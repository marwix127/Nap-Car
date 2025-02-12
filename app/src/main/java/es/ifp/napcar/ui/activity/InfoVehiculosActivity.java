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

public class InfoVehiculosActivity extends AppCompatActivity {

    //Labels de la cabecera que cambian
    private TextView userName;
    private TextView vehiclesNumber;
    private TextView alertsNumber;

    //Labels de info
    private TextView label61;
    private TextView label71;
    private TextView label81;
    private TextView label91;
    private TextView label101;
    private TextView label111;
    private TextView label121;
    private TextView label131;
    private TextView label141;
    private TextView label151;
    private TextView label161;

    //Botones menu
    private Button profileButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    //Botones de la info
    private Button button6;
    private Button button7;



    //Pasar pantalla
    private Intent pasarPantalla;

    //Gestion BBDD
    private DatabaseSQL db;
    private BaseDeDatos db2;
    protected BBDDVehiculos db3;
    protected String[] vehiculo = new String[11];

    //Para recivir el paquete con el id del vehiculo
    private String paquete1="";
    private Bundle extras;

    private String[] paquete;
    private int userId=0;
    private ArrayList<String> userNameBBDD = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_vehiculos);

        //Inicializamos BBDD
        db = new DatabaseSQL(InfoVehiculosActivity.this);
        db2 = new BaseDeDatos(InfoVehiculosActivity.this);
        db3 = new BBDDVehiculos(InfoVehiculosActivity.this);

        userName = (TextView) findViewById(R.id.username_general);
        vehiclesNumber = (TextView) findViewById(R.id.vehiclesnumber_general);
        alertsNumber = (TextView) findViewById(R.id.alertsnumber_general);
        profileButton = (Button) findViewById(R.id.profilebutton_general);
        label61 = (TextView) findViewById(R.id.label61_infoVehiculos);
        label71 = (TextView) findViewById(R.id.label71_infoVehiculos);
        label81 = (TextView) findViewById(R.id.label81_infoVehiculos);
        label91 = (TextView) findViewById(R.id.label91_infoVehiculos);
        label101 = (TextView) findViewById(R.id.label101_infoVehiculos);
        label111 = (TextView) findViewById(R.id.label111_infoVehiculos);
        label121 = (TextView) findViewById(R.id.label121_infoVehiculos);
        label131 = (TextView) findViewById(R.id.label131_infoVehiculos);
        label141 = (TextView) findViewById(R.id.label141_infoVehiculos);
        label151 = (TextView) findViewById(R.id.label151_infoVehiculos);
        label161 = (TextView) findViewById(R.id.label161_infoVehiculos);

        //Botones
        button1= (Button) findViewById(R.id.vehiclebutton_general);
        button2= (Button) findViewById(R.id.garagebutton_general);
        button3= (Button) findViewById(R.id.homebutton_general);
        button4= (Button) findViewById(R.id.optionsbutton_general);
        button5= (Button) findViewById(R.id.helpbutton_general);

        button6 = (Button) findViewById(R.id.button6_infoVehiculos);
        button7 = (Button) findViewById(R.id.button7_infoVehiculos);

        //Recivir paquete con id de vehiculo

        try{
            //Recoger paquetes que entran
            extras = getIntent().getExtras();
            //Almenos he recibido un paquete
            if(extras != null){
                paquete = new String[]{extras.getString("USERID")};
                userId = Integer.parseInt(paquete[0]);
                userNameBBDD = db.getUser(userId);

                paquete1= extras.getString("ID");

            }

            userName.setText(userNameBBDD.get(0));
            vehiclesNumber.setText(""+db3.numVehiculos(userId));
            alertsNumber.setText(Integer.toString(db2.getNumAlerts()));
            db2.closeBBDD();
            db.close();
        }catch(Exception e){
            Toast.makeText(this, "Ha ocurrido un error con la base de datos.", Toast.LENGTH_SHORT).show();
        }

        // Coger info de la BBDD con ese id
        vehiculo= db3.leerVehiculo(paquete1);

        // Sacar por pantalla en los labels
        //System.out.println("---->Tenemos el coche");
        label61.setText(vehiculo[1]);
        label71.setText(vehiculo[2]);
        label81.setText(vehiculo[3]);
        label91.setText(vehiculo[4]);
        label101.setText(vehiculo[5]);
        label111.setText(vehiculo[6]);
        label121.setText(vehiculo[7]);
        label131.setText(vehiculo[8]);
        label141.setText(vehiculo[9]);
        label151.setText(vehiculo[10]);
        label161.setText(vehiculo[11]);


        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(InfoVehiculosActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        //Boton de vehiculos
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(InfoVehiculosActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
//TODO        //Boton talleres
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(InfoVehiculosActivity.this, TalleresActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Boton home
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(InfoVehiculosActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

//TODO        //Boton opciones
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(InfoVehiculosActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

//TODO        //Boton ayuda
       button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(InfoVehiculosActivity.this, HelpActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Boton6 Volver
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pasarPantalla = new Intent(InfoVehiculosActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Boton7 Editar
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enviar paquete y hacer que salgan en la activity registrarVehiculo

                pasarPantalla = new Intent(InfoVehiculosActivity.this, RegistroVehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                //Mandamos el paquete de info
                pasarPantalla.putExtra("ID", paquete1);
                finish();
                startActivity(pasarPantalla);
            }
        });

    }
}