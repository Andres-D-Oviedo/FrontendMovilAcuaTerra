package com.example.monitoreoacua.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.monitoreoacua.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoRegister;
    private int contadorIntentos = 0;

    // Referencia a la base de datos de Firebase
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        // Inicializar la referencia de la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        // Configurar el evento de clic para el botón de registrarse
        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear el intent para navegar a RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent); // Iniciar RegisterActivity
            }
        });

        btnLogin.setOnClickListener(v -> iniciarSesion());

    }

    private void iniciarSesion() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (contadorIntentos >= 3) {
            btnLogin.setEnabled(false);
            Toast.makeText(this, "Acceso bloqueado por múltiples intentos fallidos", Toast.LENGTH_LONG).show();
            return;
        }

        databaseReference.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String storedPassword = userSnapshot.child("password").getValue(String.class);

                                if (storedPassword != null && storedPassword.equals(password)) {
                                    // Acceso permitido
                                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                } else {
                                    // Contraseña incorrecta
                                    contadorIntentos++;
                                    Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        } else {
                            // Email no encontrado en la base de datos
                            contadorIntentos++;
                            Toast.makeText(LoginActivity.this, "Correo no registrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "Error de base de datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}