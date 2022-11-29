package com.alejandro.unidad3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button Botonregistro;
    EditText ETCorreo, ETContraseña;
    Button BotonLogin;

    private ProgressDialog progressDialog;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Botonregistro = findViewById(R.id.Botonregistro);

        ETCorreo = findViewById(R.id.ETCorreo);
        ETContraseña = findViewById(R.id.ETContraseña);
        BotonLogin = findViewById(R.id.BotonLogin);

        progressDialog = new ProgressDialog(MainActivity.this);
        dialog = new Dialog(MainActivity.this);

        BotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        Botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,actividadRegistrarse.class));
            }
        });
    }

}