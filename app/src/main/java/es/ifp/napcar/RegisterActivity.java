package es.ifp.napcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    protected DatabaseSQL db;
    private int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText nombre_t = (EditText) findViewById(R.id.text_nombre);
        EditText email_t = (EditText) findViewById(R.id.text_email);
        EditText password_t = (EditText) findViewById(R.id.text_pass);
        EditText apellidos_t = (EditText) findViewById(R.id.text_nombre2);
        Button btn_registrar = (Button) findViewById(R.id.btn_register);
        TextView login = (TextView) findViewById(R.id.btn_go_login);
        db = new DatabaseSQL(this);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombre_t.getText().toString();
                String apellidos = apellidos_t.getText().toString();
                String email = email_t.getText().toString();
                String password = password_t.getText().toString();

                try{
                    if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()|| apellidos.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    } else {
                        if(!db.esEmailValido(email)){
                            Toast.makeText(RegisterActivity.this, "Email invalido", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (!db.emailExists(email)) {
                                if (!db.registerUser(email, password, nombre, apellidos)) {

                                    Toast.makeText(RegisterActivity.this, "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                                } else {
                                    id = db.getUserId(email);
                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    intent.putExtra("USERID", Integer.toString(id));
                                    startActivity(intent);
                                    //db.close();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "email en uso", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }catch(Exception e){
                    Toast.makeText(RegisterActivity.this, "Peta BBDD.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                startActivity(intent);
            }

        });





    }
}
