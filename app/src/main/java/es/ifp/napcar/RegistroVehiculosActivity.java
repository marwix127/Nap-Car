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

public class RegistroVehiculosActivity extends AppCompatActivity {

    //Labels de la cabecera que cambian
    private TextView userName;
    private TextView vehiclesNumber;
    private TextView alertsNumber;
    //Cajas de texto
    private EditText caja6;
    private EditText caja7;
    private EditText caja8;
    private EditText caja9;
    private EditText caja10;
    private EditText caja11;
    private EditText caja12;
    private EditText caja13;
    private EditText caja14;
    private EditText caja15;
    private EditText caja16;
    //Strings para guardar la info de las cajas
    private String marca;
    private String modelo;
    private String anio;

    private String motor;

    private String potencia;

    private String km;

    private String matricula;
    private String combustible;

    private String itvDate;

    private String aceitekm;

    private String aceiteDate;

    //Botones menu
    private Button profileButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    //Botones del registro
    private Button button6;
    private Button button7;



    //Pasar pantalla
    private Intent pasarPantalla;
    //Gestion BBDD
    private DatabaseSQL db;
    private BasecitaDeDatos db2;
    protected BBDDVehiculos db3;

    //Para recivir el paquete con el id del vehiculo
    private String paquete1="";
    private Bundle extras;
    //Para info de db
    private String[] vehiculo = new String[11];

    private String[] paquete;
    private int userId=0;
    private ArrayList<String> userNameBBDD = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_vehiculos);

        //Inicializamos BBDD
        db = new DatabaseSQL(RegistroVehiculosActivity.this);
        db2 = new BasecitaDeDatos(RegistroVehiculosActivity.this);
        db3 = new BBDDVehiculos(RegistroVehiculosActivity.this);

        userName = (TextView) findViewById(R.id.username_general);
        vehiclesNumber = (TextView) findViewById(R.id.vehiclesnumber_general);
        alertsNumber = (TextView) findViewById(R.id.alertsnumber_general);
        profileButton = (Button) findViewById(R.id.profilebutton_general);

        caja6 = (EditText) findViewById(R.id.caja6_regVehiculos);
        caja7 = (EditText) findViewById(R.id.caja7_regVehiculos);
        caja8 = (EditText) findViewById(R.id.caja8_regVehiculos);
        caja9 = (EditText) findViewById(R.id.caja9_regVehiculos);
        caja10 = (EditText) findViewById(R.id.caja10_regVehiculos);
        caja11 = (EditText) findViewById(R.id.caja11_regVehiculos);
        caja12 = (EditText) findViewById(R.id.caja12_regVehiculos);
        caja13 = (EditText) findViewById(R.id.caja13_regVehiculos);
        caja14 = (EditText) findViewById(R.id.caja14_regVehiculos);
        caja15 = (EditText) findViewById(R.id.caja15_regVehiculos);
        caja16 = (EditText) findViewById(R.id.caja16_regVehiculos);

        //Botones
        button1= (Button) findViewById(R.id.vehiclebutton_general);
        button2= (Button) findViewById(R.id.garagebutton_general);
        button3= (Button) findViewById(R.id.homebutton_general);
        button4= (Button) findViewById(R.id.optionsbutton_general);
        button5= (Button) findViewById(R.id.helpbutton_general);

        button6 = (Button) findViewById(R.id.button6_regVehiculos);
        button7 = (Button) findViewById(R.id.button7_regVehiculos);

        try{
            extras = getIntent().getExtras();
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

        //Editamos un vehiculo
        if(paquete1 != null)
        {
            //Cogemos info del vehiculo
            vehiculo= db3.leerVehiculo(paquete1);
            //Sacamos por pantalla info
            caja6.setText(vehiculo[1]);
            caja7.setText(vehiculo[2]);
            caja8.setText(vehiculo[3]);
            if (!vehiculo[4].equals("N/S"))
            {
                caja9.setText(vehiculo[4]);
            }

            if (!vehiculo[5].equals("N/S"))
            {
                caja10.setText(vehiculo[5]);
            }

            caja11.setText(vehiculo[6]);
            caja12.setText(vehiculo[7]);
            caja13.setText(vehiculo[8]);
            caja14.setText(vehiculo[9]);
            if (!vehiculo[10].equals("N/S"))
            {
                caja15.setText(vehiculo[10]);
            }

            if (!vehiculo[11].equals("N/S"))
            {
                caja16.setText(vehiculo[11]);
            }
            db3.close();
        }

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(RegistroVehiculosActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        //Boton de vehiculos
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(RegistroVehiculosActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
//TODO        //Boton talleres
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(RegistroVehiculosActivity.this, TalleresActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        //Boton home
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(RegistroVehiculosActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

//TODO        //Boton opciones
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(RegistroVehiculosActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

//TODO        //Boton ayuda
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(RegistroVehiculosActivity.this, HelpActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });


        //Boton6 Volver
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pasarPantalla = new Intent(RegistroVehiculosActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        //Boton7 Guardar registro
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Si las cajas obligatorias estan vacias
                if (caja6.getText().toString().equals("") || caja7.getText().toString().equals("") || caja8.getText().toString().equals("") || caja11.getText().toString().equals("") || caja12.getText().toString().equals("") || caja13.getText().toString().equals("") || caja14.getText().toString().equals(""))
                {
                    Toast.makeText(RegistroVehiculosActivity.this, "Completa todos los parametros con * del formulario", Toast.LENGTH_SHORT).show();
                }
                //Si estan rellenadas
                else
                {
                    //Guardar la info de las cajas en los strings
                    marca= caja6.getText().toString();
                    modelo= caja7.getText().toString();
                    anio= caja8.getText().toString();
                    motor= caja9.getText().toString();
                    potencia= caja10.getText().toString();
                    km= caja11.getText().toString();
                    matricula= caja12.getText().toString();
                    combustible= caja13.getText().toString();
                    itvDate= caja14.getText().toString();
                    aceitekm= caja15.getText().toString();
                    aceiteDate= caja16.getText().toString();


                    //Si deja vacias las cajas que NO son obligatorias guardare N/S en su interior
                    if (motor.equals(""))
                    {
                        motor= "N/S";
                    }
                    if (potencia.equals(""))
                    {
                        potencia= "N/S";
                    }
                    if (aceitekm.equals(""))
                    {
                        aceitekm= "N/S";
                    }
                    if (aceiteDate.equals(""))
                    {
                        aceiteDate= "N/S";
                    }
                    //Para elegir si edita un vehiculo o si guarda uno nuevo
                    if (paquete1!=null)
                    {
                        //Funcion de UPDATE
                        db3.editarVehiculo(marca, modelo, anio, motor, potencia, km, matricula, combustible, itvDate, aceitekm, aceiteDate, paquete1);
                        // Toast de que se ha editado
                        Toast.makeText(RegistroVehiculosActivity.this, "Vehiculo editado correctamente", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // guardar la info en la BBDD
                        db3.guardarVehiculo(marca, modelo, anio, motor, potencia, km, matricula, combustible, itvDate, aceitekm, aceiteDate, userId);
                        // Toast de que se ha guardado
                        Toast.makeText(RegistroVehiculosActivity.this, "Vehiculo guardado correctamente", Toast.LENGTH_SHORT).show();
                    }


                    pasarPantalla = new Intent(RegistroVehiculosActivity.this, VehiculosActivity.class);
                    pasarPantalla.putExtra("USERID", Integer.toString(userId));
                    finish();
                    startActivity(pasarPantalla);
                }



            }
        });

        
    }
}