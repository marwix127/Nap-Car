package es.ifp.napcar.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import es.ifp.napcar.R;
import es.ifp.napcar.service.BBDDVehiculos;
import es.ifp.napcar.service.BaseDeDatos;
import es.ifp.napcar.service.DatabaseSQL;

public class HomeActivity extends AppCompatActivity {

    private TextView userName;
    private TextView vehiclesNumber;
    private TextView alertsNumber;
    private Button profileButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private ImageButton button6;
    private ListView list;

    private Intent pasarPantalla;
    //Gestion BBDD
    protected DatabaseSQL db;
    protected BaseDeDatos db2;
    protected BBDDVehiculos db3;

    private ArrayList<String> alerts = new ArrayList<String>();
    private ArrayAdapter<String> itemAdapter;
    private String[] separator;
    private String contentItem="";
    private int alertId=0;
    private int numAlerts=0;

    private Bundle extras;
    private String[] paquete;
    private int userId=0;
    private ArrayList<String> userNameBBDD = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseSQL(HomeActivity.this);
        db2 = new BaseDeDatos(HomeActivity.this);
        db3 = new BBDDVehiculos(HomeActivity.this);


        userName = (TextView) findViewById(R.id.username_general);
        vehiclesNumber = (TextView) findViewById(R.id.vehiclesnumber_general);
        alertsNumber = (TextView) findViewById(R.id.alertsnumber_general);
        profileButton = (Button) findViewById(R.id.profilebutton_general);
        button1 = (Button) findViewById(R.id.vehiclebutton_general);
        button2 = (Button) findViewById(R.id.garagebutton_general);
        button3 = (Button) findViewById(R.id.homebutton_general);
        button4 = (Button) findViewById(R.id.optionsbutton_general);
        button5 = (Button) findViewById(R.id.helpbutton_general);
        button6 = (ImageButton) findViewById(R.id.createalert_home);
        list = (ListView) findViewById(R.id.list_home);

        try{ //Accede a la BBDD para recoger la información de todos los registros y mostrarlos en el listado.
            alerts = db2.readAllAlerts();
            itemAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, alerts);
            list.setAdapter(itemAdapter);

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

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Hacer click corto envía al usuario a la escena "AlertsActivity" para modificar su contenido.
                contentItem = parent.getItemAtPosition(position).toString();

                //Recoge la información de la BBDD y almacena solamente el ID para utilizarlo en la siguiente ventana.
                separator = contentItem.split(".-");
                alertId = Integer.parseInt(separator[0]);

                pasarPantalla =  new Intent(HomeActivity.this, AlertsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                pasarPantalla.putExtra("ALERT", Integer.toString(alertId));
                startActivity(pasarPantalla);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Hacer click largo muestra un diálogo emergente para darle la opción al usuario de eliminar
                //la entrada del item seleccionado.
                contentItem = parent.getItemAtPosition(position).toString();

                //Recoge la información de la BBDD y almacena solamente el ID para indicar al método que debe
                //eliminar esa entrada.
                separator = contentItem.split("¿?");
                alertId = Integer.parseInt(separator[0]);

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("ELIMINAR RECORDATORIO");
                builder.setMessage("¿Quieres eliminar esta entrada?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db2.deleteAlerts(alertId);
                        Toast.makeText(HomeActivity.this, "Entrada borrada correctamente.", Toast.LENGTH_SHORT).show();

                        pasarPantalla = new Intent(HomeActivity.this, HomeActivity.class);
                        pasarPantalla.putExtra("USERID", Integer.toString(userId));
                        finish();
                        startActivity(pasarPantalla);
                        db2.closeBBDD();
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(HomeActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() { //Botón Vehículos
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (HomeActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { //Botón Talleres
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (HomeActivity.this, TalleresActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() { //Botón Home
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (HomeActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() { //Botón Opciones
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (HomeActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() { //Botón Ayuda
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(HomeActivity.this, HelpActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() { //Botón crear nuevo recordatorio
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (HomeActivity.this, CreateAlertsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
    }
}