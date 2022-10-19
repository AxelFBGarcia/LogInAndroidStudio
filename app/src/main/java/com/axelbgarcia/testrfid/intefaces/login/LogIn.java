package com.axelbgarcia.testrfid.intefaces.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.axelbgarcia.testrfid.RFIDActions;
import com.axelbgarcia.testrfid.R;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;


public class LogIn extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "username";
    private static final String KEY_NAME = "key_username";
    private static final String SHARED_PREF_PASSWORD = "password";
    private static final String KEY_PASSWORD = "key_password";

    Connection connection;
    EditText usrName, pass;
    Button login;
    String dbUsrName, dbPass;
    String lOutVal = "";
    MaterialCheckBox rmbrMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        rmbrMe = findViewById(R.id.rememberMe);
        login = findViewById(R.id.inlog);
        usrName = findViewById(R.id.usrName);
        pass = findViewById(R.id.passw);
        SharedPreferences reset = getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        lOutVal = reset.getString("resChBox", "false");

        boxChecked(lOutVal);
    }

    public void boxChecked(String ch){
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.apply();

        checkedLogIn(ch);

        rmbrMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()){
                    editor.clear();
                    editor.apply();
                    editor.putString("remember", "true");
                    editor.apply();

                }else if (!compoundButton.isChecked()){
                    editor.clear();
                    editor.apply();
                    editor.putString("remember", "false");
                    editor.apply();

                }
            }
        });
    }

    private void checkedLogIn(String checkedBox){
        if (checkedBox.equals("true")){
            logIn(true);
        }else if (checkedBox.equals("false") || checkedBox.equals(null)){
            logIn(false);
            Toast.makeText(this, "Porfavor Inicia Sesión", Toast.LENGTH_SHORT).show();
        }
    }

    private void logIn(Boolean remMe){
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences sp2 = getSharedPreferences(SHARED_PREF_PASSWORD, MODE_PRIVATE);
        TextInputLayout til1 = findViewById(R.id.textfield);
        TextInputLayout til2 = findViewById(R.id.textfield2);
        ConnectionClass c = new ConnectionClass();

        dbUsrName = sp.getString(KEY_NAME, null);
        dbPass = sp2.getString(KEY_PASSWORD, null);

        if (remMe == true){

            dbUsrName = sp.getString(KEY_NAME, null);
            dbPass = sp2.getString(KEY_PASSWORD, null);
            connection = c.connectionclasss(dbUsrName, dbPass);

            if (connection != null){
                startActivity(new Intent(LogIn.this, RFIDActions.class));
            }else {
                System.out.println("Conexión con la base de datos:" + connection);
                til1.setError("Usuario incorrecto");
                til2.setError("Contraseña incorrecta");
            }
        }else{
            login.setOnClickListener(view -> {
                saveNP();

                dbUsrName = sp.getString(KEY_NAME, null);
                dbPass = sp2.getString(KEY_PASSWORD, null);
                connection = c.connectionclasss(dbUsrName, dbPass);

                if (dbUsrName.isEmpty() || dbPass.isEmpty()){
                    til1.setError("Usuario incorrecto");
                    til2.setError("Contraseña incorrecta");
                }else{
                    if (connection != null){
                        startActivity(new Intent(LogIn.this, RFIDActions.class));
                    }else{
                        System.out.println("Conexión con la base de datos:" + connection);
                        til1.setError("Usuario incorrecto");
                        til2.setError("Contraseña incorrecta");
                    }
                }
            });
        }
    }

    private void saveNP() {
        String name = usrName.getText().toString();
        String password = pass.getText().toString();

        // Check if editText is empty
        if (name.isEmpty() || password.isEmpty()) {
            name = "";
            password = "";
        }
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences sp2 = getSharedPreferences(SHARED_PREF_PASSWORD, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        SharedPreferences.Editor editor2 = sp2.edit();

        editor.putString(KEY_NAME, name);
        editor2.putString(KEY_PASSWORD, password);

        editor.apply();
        editor2.apply();
    }
}