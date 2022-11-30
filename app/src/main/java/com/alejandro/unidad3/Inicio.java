package com.alejandro.unidad3;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Inicio extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference baseDeDatos;
    Button CerrarSesion;

    ImageView FotoPerfil;
    TextView UidPerfil, NombresPerfil, CorreoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Inicio");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        baseDeDatos = firebaseDatabase.getReference("Usuarios_de_app");

        FotoPerfil = findViewById(R.id.FotoPerfil);
        UidPerfil = findViewById(R.id.UidPerfil);
        NombresPerfil = findViewById(R.id.NombrePerfil);
        CorreoPerfil = findViewById(R.id.CorreoPerfil);


        CerrarSesion = findViewById(R.id.CerrarSesion);

        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CerrarSesion();
            }
        });
    }

    @Override
    protected void onStart() {
        VerificarSesion();
        super.onStart();
    }


    private void VerificarSesion(){
        if (firebaseUser != null){
            Toast.makeText(this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(Inicio.this,MainActivity.class));
            finish();
        }
    }

    private  void CerrarSesion(){
        firebaseAuth.signOut();
        Toast.makeText(this, "Se ha cerrado la sesíon", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Inicio.this,MainActivity.class));

    }

    private void CargarDatos(){
        Query query = baseDeDatos.orderByChild("correo").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){

                    String uid = ""+ds.child("uid").getValue();
                    String correo = ""+ds.child("correo").getValue();
                    String nombre = ""+ds.child("nombre").getValue();
                    String apellido = ""+ds.child("apellido").getValue();

                    UidPerfil.setText(uid);
                    CorreoPerfil.setText(correo);
                    NombresPerfil.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}