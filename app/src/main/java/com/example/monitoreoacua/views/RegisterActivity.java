package com.example.monitoreoacua.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.monitoreoacua.R;
import com.example.monitoreoacua.models.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText etNombre, etFicha, etJornada, etSede, etEmail, etPassword, etPasswordVerify;
    private Button btnRegistrar;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Usuarios");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        etNombre = findViewById(R.id.etNombre);
        etFicha = findViewById(R.id.etFicha);
        etJornada = findViewById(R.id.etJornada);
        etSede = findViewById(R.id.etSede);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordVerify = findViewById(R.id.etPasswordVerify);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> registrarUsuario());

    }

    private void registrarUsuario() {
        String nombre = etNombre.getText().toString();
        String ficha = etFicha.getText().toString();
        String jornada = etJornada.getText().toString();
        String sede = etSede.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String passwordVerify = etPasswordVerify.getText().toString().trim();

        // Validar campos
        if (nombre.isEmpty() || ficha.isEmpty() || jornada.isEmpty() || sede.isEmpty() || email.isEmpty() || password.isEmpty() || passwordVerify.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que las contraseñas coincidan
        if (!password.equals(passwordVerify)) {
            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar la complejidad de la contraseña (opcional)
        if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objeto Usuario
        Usuario usuario = new Usuario(nombre, ficha, jornada, sede, email, password);

        // Guardar el usuario en Firebase Database
        // databaseReference.child("Estudiantes").child(edtCodigo.getText().toString()).setValue(estudiantes);
        databaseReference.push().setValue(usuario)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}