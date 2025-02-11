package es.ifp.napcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AlertsActivity extends AppCompatActivity {

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
    protected BasecitaDeDatos db2;
    protected BBDDVehiculos db3;

    private Bundle recivedItem;
    private int alertId=0;
    private ArrayList<String> alert = new ArrayList<String>();
    private String[] prueba;
    private String content="";
    private String newAlert="";

    private Bundle extras;
    private String[] paquete;
    private int userId=0;
    private ArrayList<String> userNameBBDD = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        db = new DatabaseSQL(AlertsActivity.this);
        db2 = new BasecitaDeDatos(AlertsActivity.this);
        db3 = new BBDDVehiculos(AlertsActivity.this);


        userName = (TextView) findViewById(R.id.username_general);
        vehiclesNumber = (TextView) findViewById(R.id.vehiclesnumber_general);
        alertsNumber = (TextView) findViewById(R.id.alertsnumber_general);
        profileButton = (Button) findViewById(R.id.profilebutton_general);
        button1 = (Button) findViewById(R.id.vehiclebutton_general);
        button2 = (Button) findViewById(R.id.garagebutton_general);
        button3 = (Button) findViewById(R.id.homebutton_general);
        button4 = (Button) findViewById(R.id.optionsbutton_general);
        button5 = (Button) findViewById(R.id.helpbutton_general);
        button6 = (Button) findViewById(R.id.button1_alerts);
        button7 = (Button) findViewById(R.id.button2_alerts);
        label1 = (TextView) findViewById(R.id.label1_alerts);
        text1 = (EditText) findViewById(R.id.text1_alerts);

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

        recivedItem = getIntent().getExtras();
        if(recivedItem!=null){
            alertId = Integer.parseInt(recivedItem.getString("ALERT"));
            try{
                alert = db2.readAlert(alertId);
                content = alert.get(0).toString();
                prueba = content.split(".-");
                text1.setText(prueba[1]);
            }catch(Exception e) {
                Toast.makeText(this, "Se ha producido un error con la base de datos.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Se ha producido un error.", Toast.LENGTH_SHORT).show();
        }

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(AlertsActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() { //Botón Vehículos
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (AlertsActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { //Botón Talleres
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (AlertsActivity.this, TalleresActivity.class);
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
                pasarPantalla = new Intent (AlertsActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() { //Botón Ayuda
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(AlertsActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(AlertsActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAlert = text1.getText().toString();
                if(text1.getText().toString().equals("")){
                    Toast.makeText(AlertsActivity.this, "Debes añadir contenido a tu recordatorio para poder editarlo.", Toast.LENGTH_SHORT).show();
                }else if(text1.getText().toString().equals(prueba[1])){
                    Toast.makeText(AlertsActivity.this, "Para editar un recordatorio debes modificar su contenido.", Toast.LENGTH_SHORT).show();
                }else{
                    db2.modifyAlert(alertId, newAlert);
                    db2.closeBBDD();

                    pasarPantalla = new Intent(AlertsActivity.this, HomeActivity.class);
                    pasarPantalla.putExtra("USERID", Integer.toString(userId));
                    finish();
                    startActivity(pasarPantalla);
                }
            }
        });
    }
}