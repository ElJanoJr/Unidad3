package com.alejandro.unidad3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button Botonregistro;
    EditText ETCorreo, ETContraseña;
    Button BotonLogin;
    private FirebaseAuth firebaseAuth;
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
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(MainActivity.this);
        dialog = new Dialog(MainActivity.this);



        BotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = ETCorreo.getText().toString();
                String contraseña = ETContraseña.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    ETCorreo.setError("Correo invalido");
                    ETCorreo.setFocusable(true);
                }else if(contraseña.length()<7){
                    ETContraseña.setError("Contraseña invalida");
                    ETContraseña.setFocusable(true);
                }else{
                    LOGINUSUARIO(correo, contraseña);
                }
            }
        });
        Botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,actividadRegistrarse.class));
            }
        });
    }


    private void LOGINUSUARIO(String correo, String contraseña) {
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            finish();

                            startActivity(new Intent(MainActivity.this,Inicio.class));

                        }else{
                            progressDialog.dismiss();
                            NoSesion();
                            //Toast.makeText(MainActivity.this, "UPS! Algo salío mal", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private Void NoSesion(){
        Button NoSesion;
        dialog.setContentView(R.layout.no_sesion);
        NoSesion = dialog.findViewById(R.id.NoInicio);
        NoSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        return null;
    }
}