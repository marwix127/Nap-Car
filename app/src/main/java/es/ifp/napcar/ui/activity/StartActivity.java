package es.ifp.napcar.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.ifp.napcar.R;
import es.ifp.napcar.service.DatabaseSQL;

public class StartActivity extends AppCompatActivity {


    private Button login;
    protected DatabaseSQL db;
    private Intent pasarPantalla;
    private int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = (Button) findViewById(R.id.btn_login);
        TextView email_t = (TextView) findViewById(R.id.text_email_l);
        TextView password_t = (TextView) findViewById(R.id.text_pass_l);
        TextView go_register = (TextView) findViewById(R.id.btn_go_register);
        db = new DatabaseSQL(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_t.getText().toString();
                String password = password_t.getText().toString();
                try{
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(StartActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    } else {
                        if (db.loginUser(email, password)) {
                            id = db.getUserId(email);
                            pasarPantalla = new Intent(StartActivity.this, HomeActivity.class);
                            pasarPantalla.putExtra("USERID", Integer.toString(id));
                            finish();
                            startActivity(pasarPantalla);
                            //db.close();
                        } else {
                            Toast.makeText(StartActivity.this, "Credenciales Invalidas", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch(Exception e){
                    Toast.makeText(StartActivity.this, "Peta BBDD.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        go_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);

            }
        });
    }
}