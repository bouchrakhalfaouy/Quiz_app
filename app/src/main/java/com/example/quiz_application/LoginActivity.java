package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private database db;
    private TextView createAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des composants
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        createAccountTextView = findViewById(R.id.registerTextView);

        db = new database(this); // Initialisation de la base de données

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Vérification des champs vides
            if (email.isEmpty()) {
                emailEditText.setError("Veuillez entrer un e-mail");
                emailEditText.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                passwordEditText.setError("Veuillez entrer un mot de passe");
                passwordEditText.requestFocus();
                return;
            }

            // Vérification du format de l'e-mail
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Veuillez entrer un e-mail valide");
                emailEditText.requestFocus();
                return;
            }
            // Authentification
            String fullName = db.validateUser(email, password);
            if (fullName != null) {
                // Succès
                Toast.makeText(LoginActivity.this, "Bienvenue " + fullName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Échec
                Toast.makeText(LoginActivity.this, "E-mail ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }

        });
        createAccountTextView .setOnClickListener(v -> {
            // Rediriger vers l'activité d'inscription
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
