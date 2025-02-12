package es.ifp.napcar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import es.ifp.napcar.R;
import es.ifp.napcar.service.BBDDVehiculos;
import es.ifp.napcar.service.BaseDeDatos;
import es.ifp.napcar.service.DatabaseSQL;

public class CreateAlertsActivity extends AppCompatActivity {

    private TextView userName;
    private TextView vehiclesNumber;
    private TextView alertsNumber;
    private Button profileButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private TextView label1;
    private EditText text1;

    private Intent pasarPantalla;
    //Gestion BBDD
    protected DatabaseSQL db;
    protected BaseDeDatos db2;
    protected BBDDVehiculos db3;

    private String alert="";

    private Bundle extras;
    private String[] paquete;
    private int userId=0;
    private ArrayList<String> userNameBBDD = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createalerts);

        db = new DatabaseSQL(CreateAlertsActivity.this);
        db2 = new BaseDeDatos(CreateAlertsActivity.this);
        db3 = new BBDDVehiculos(CreateAlertsActivity.this);


        userName = (TextView) findViewById(R.id.username_general);
        vehiclesNumber = (TextView) findViewById(R.id.vehiclesnumber_general);
        alertsNumber = (TextView) findViewById(R.id.alertsnumber_general);
        profileButton = (Button) findViewById(R.id.profilebutton_general);
        button1 = (Button) findViewById(R.id.vehiclebutton_general);
        button2 = (Button) findViewById(R.id.garagebutton_general);
        button3 = (Button) findViewById(R.id.homebutton_general);
        button4 = (Button) findViewById(R.id.optionsbutton_general);
        button5 = (Button) findViewById(R.id.helpbutton_general);
        button6 = (Button) findViewById(R.id.button1_createalerts);
        button7 = (Button) findViewById(R.id.button2_createalerts);
        label1 = (TextView) findViewById(R.id.label1_createalerts);
        text1 = (EditText) findViewById(R.id.text1_createalerts);

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

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(CreateAlertsActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() { //Botón Vehículos
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (CreateAlertsActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { //Botón Talleres
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (CreateAlertsActivity.this, TalleresActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() { //Botón Home
            @Override
            public void onClick(View v) {
                /*pasarPantalla = new Intent (HomeActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);*/
            }
        });
        button4.setOnClickListener(new View.OnClickListener() { //Botón Opciones
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (CreateAlertsActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() { //Botón Ayuda
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(CreateAlertsActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(CreateAlertsActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text1.getText().toString().equals("")){
                    Toast.makeText(CreateAlertsActivity.this, "Debes añadir contenido a tu recordatorio para poder crearlo.", Toast.LENGTH_SHORT).show();
                }else{
                    alert = text1.getText().toString();
                    db2.insertAlerts("Recordatorio", alert);
                    db2.closeBBDD();

                    pasarPantalla = new Intent(CreateAlertsActivity.this, HomeActivity.class);
                    pasarPantalla.putExtra("USERID", Integer.toString(userId));
                    finish();
                    startActivity(pasarPantalla);
                }
            }
        });
    }
}