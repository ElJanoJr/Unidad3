package com.alejandro.unidad3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PathEffect;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class actividadRegistrarse extends AppCompatActivity {


    EditText Correo, Contraseña, Nombre, Apellido;
    Button BtnCrear;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_registrarse);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Registro");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Correo = findViewById(R.id.Correo);
        Contraseña = findViewById(R.id.Contraseña);
        Nombre = findViewById(R.id.Nombre);
        Apellido = findViewById(R.id.Apellido);
        BtnCrear = findViewById(R.id.BtnCrear);
        firebaseAuth = FirebaseAuth.getInstance();

        BtnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = Correo.getText().toString();
                String contraseña = Contraseña.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    Correo.setError("Correo no valido");
                    Correo.setFocusable(true);
                }else if(contraseña.length()<6){
                    Contraseña.setError("Contraseña debe ser mayor a 6 digitos");
                    Contraseña.setFocusable(true);
                }else{
                    BtnCrear(correo,contraseña);
                }
            }
        });
    }

    private void BtnCrear(String correo, String contraseña) {
        firebaseAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            String uid = user.getUid();
                            String correo = Correo.getText().toString();
                            String contraseña = Contraseña.getText().toString();
                            String nombre = Nombre.getText().toString();
                            String apellido = Apellido.getText().toString();


                            HashMap<Object, String> DatosUsuario = new HashMap<>();

                            DatosUsuario.put("uid", uid);
                            DatosUsuario.put("correo", correo);
                            DatosUsuario.put("contraseña", contraseña);
                            DatosUsuario.put("nombre", nombre);
                            DatosUsuario.put("apellido", apellido);



                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Usuarios_de_app");
                            reference.child(uid).setValue(DatosUsuario);
                            Toast.makeText(actividadRegistrarse.this, "Se registró correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(actividadRegistrarse.this, Inicio.class));
                        } else {
                            Toast.makeText(actividadRegistrarse.this, "¡Ups!, Algo salió mal", Toast.LENGTH_SHORT).show();
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(actividadRegistrarse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}