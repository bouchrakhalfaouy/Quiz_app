package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private TextView loginTextView;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialiser les composants
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);

        db = new database(this); // Initialisation de la base de données

        // Gérer l'inscription
        registerButton.setOnClickListener(view -> {
            String firstName = firstNameEditText.getText().toString().trim();
            String lastName = lastNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Vérifier les champs vides
            if (firstName.isEmpty()) {
                firstNameEditText.setError("Veuillez entrer votre prénom");
                firstNameEditText.requestFocus();
                return;
            }
            if (lastName.isEmpty()) {
                lastNameEditText.setError("Veuillez entrer votre nom");
                lastNameEditText.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                emailEditText.setError("Veuillez entrer votre email");
                emailEditText.requestFocus();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Veuillez entrer un email valide");
                emailEditText.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                passwordEditText.setError("Veuillez entrer un mot de passe");
                passwordEditText.requestFocus();
                return;
            }

            // Vérification si l'email existe déjà
            if (db.validateUser(email, password) != null) {
                Toast.makeText(RegisterActivity.this, "Cet email est déjà utilisé", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ajouter l'utilisateur à la base de données
            long result = db.addUser(firstName, lastName, email, password);
            if (result != -1) {
                Toast.makeText(RegisterActivity.this, "Compte créé avec succès !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Erreur lors de la création du compte", Toast.LENGTH_SHORT).show();
            }
        });

        // Rediriger vers la page de connexion
        loginTextView.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
