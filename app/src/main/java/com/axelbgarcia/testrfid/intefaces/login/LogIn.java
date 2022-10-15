package com.axelbgarcia.testrfid.intefaces.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.axelbgarcia.testrfid.MainActivity;
import com.axelbgarcia.testrfid.R;
import com.google.android.material.textfield.TextInputLayout;


import java.sql.Connection;


public class LogIn extends AppCompatActivity {
    /*int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
    int height = Resources.getSystem().getDisplayMetrics().heightPixels;*/

    Connection connection;
    EditText usrName, pass;
    Button login;
    String dbUsrName, dbPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        login = (Button) findViewById(R.id.inlog);
        usrName = (EditText) findViewById(R.id.usrName);
        pass = (EditText) findViewById(R.id.passw);

        TextInputLayout til1 = (TextInputLayout) findViewById(R.id.textfield);
        TextInputLayout til2 = (TextInputLayout) findViewById(R.id.textfield2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbUsrName = usrName.getText().toString();
                dbPass = pass.getText().toString();
                ConnectionClass c = new ConnectionClass();
                connection = c.connectionclasss(dbUsrName, dbPass);

                if (connection != null){
                    startActivity(new Intent(LogIn.this, MainActivity.class));
                }else{
                    System.out.println("Conexión con la base de datos:" + connection);
                    til1.setError("Usuario incorrecto");
                    til2.setError("Contraseña incorrecta");
                }
            }
        });
    }
}