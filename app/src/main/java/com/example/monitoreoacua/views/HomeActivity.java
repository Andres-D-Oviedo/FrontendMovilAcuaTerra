package com.example.monitoreoacua.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoacua.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Referenciar el botón "Cerrar Sesión"
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Agregar funcionalidad al botón
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar un cuadro de diálogo de confirmación
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Cerrar sesión")
                        .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cerrar la aplicación
                                finishAffinity(); // Cierra todas las actividades relacionadas
                            }
                        })
                        .setNegativeButton("No", null) // No hacer nada si se presiona "No"
                        .show();
            }
        });
    }
}
