package es.ifp.napcar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class OptionsActivity extends AppCompatActivity {

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
    private Spinner drop1;
    private Spinner drop2;
    private Spinner drop3;
    private Spinner drop4;
    private Spinner drop5;

    private String itemDrop1;
    private String itemDrop2;
    private String itemDrop3;
    private String itemDrop4;
    private String itemDrop5;
    private List<String> itemList1 = new ArrayList<>();
    private List<String> itemList2 = new ArrayList<>();
    private List<String> itemList3 = new ArrayList<>();
    private List<String> itemList4 = new ArrayList<>();
    private List<String> itemList5 = new ArrayList<>();
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter3;
    private ArrayAdapter<String> adapter4;
    private ArrayAdapter<String> adapter5;

    private Intent pasarPantalla;
    //Gestion BBDD
    protected DatabaseSQL db;
    protected BasecitaDeDatos db2;
    protected BBDDVehiculos db3;

    private Bundle extras;
    private String[] paquete;
    private int userId=0;
    private ArrayList<String> userNameBBDD = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        db = new DatabaseSQL(OptionsActivity.this);
        db2 = new BasecitaDeDatos(OptionsActivity.this);
        db3 = new BBDDVehiculos(OptionsActivity.this);

        userName = (TextView) findViewById(R.id.username_general);
        vehiclesNumber = (TextView) findViewById(R.id.vehiclesnumber_general);
        alertsNumber = (TextView) findViewById(R.id.alertsnumber_general);
        profileButton = (Button) findViewById(R.id.profilebutton_general);
        button1 = (Button) findViewById(R.id.vehiclebutton_general);
        button2 = (Button) findViewById(R.id.garagebutton_general);
        button3 = (Button) findViewById(R.id.homebutton_general);
        button4 = (Button) findViewById(R.id.optionsbutton_general);
        button5 = (Button) findViewById(R.id.helpbutton_general);
        drop1 = (Spinner) findViewById(R.id.spinner1_options);
        drop2 = (Spinner) findViewById(R.id.spinner2_options);
        drop3 = (Spinner) findViewById(R.id.spinner3_options);
        drop4 = (Spinner) findViewById(R.id.spinner4_options);
        drop5 = (Spinner) findViewById(R.id.spinner5_options);
        button6 = (Button) findViewById(R.id.buttonapply_options);
        button7 = (Button) findViewById(R.id.buttondelete_options);

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

        //db.READUSERINFO();
        //db.READUSEROPTIONS();

        itemList1.add("Emergentes");
        itemList1.add("Ninguna");
        itemList2.add("Automático");
        itemList2.add("Manual");
        itemList3.add("Activado");
        itemList3.add("Desactivado");
        itemList4.add("Activado");
        itemList4.add("Desactivado");
        itemList5.add("Sí");
        itemList5.add("No");

        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList1);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList2);
        adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList3);
        adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList4);
        adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList5);

        drop1.setAdapter(adapter1);
        drop2.setAdapter(adapter2);
        drop3.setAdapter(adapter3);
        drop4.setAdapter(adapter4);
        drop5.setAdapter(adapter5);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(OptionsActivity.this, PerfilUserActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() { //Botón Vehículos
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (OptionsActivity.this, VehiculosActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { //Botón Talleres
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (OptionsActivity.this, TalleresActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() { //Botón Home
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (OptionsActivity.this, HomeActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() { //Botón Opciones
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent (OptionsActivity.this, OptionsActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() { //Botón Ayuda
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(OptionsActivity.this, HelpActivity.class);
                pasarPantalla.putExtra("USERID", Integer.toString(userId));
                finish();
                startActivity(pasarPantalla);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ALGO HARÁ ESTO.
                //db.MODIFYUSEROPTIONS();
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OptionsActivity.this);
                builder.setTitle("ELIMINAR CUENTA");
                builder.setMessage("¿Seguro que deseas eliminar tu cuenta?");
                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*db.DELETEACCOUNT(idUser);
                        Toast.makeText(HomeActivityTest.this, "Tu cuenta se ha eliminado.", Toast.LENGTH_SHORT).show();

                        pasarPantalla = new Intent(OptionsActivity.this, StartActivity.class);
                        finish();
                        startActivity(pasarPantalla);
                        db.CLOSE();*/
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}