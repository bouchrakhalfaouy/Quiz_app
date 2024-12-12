package com.example.quiz_application;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

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
                emailEditText.setError(getString(R.string.enter_email_error));  // Utilisation de string pour l'erreur
                emailEditText.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                passwordEditText.setError(getString(R.string.enter_password_error));  // Utilisation de string pour l'erreur
                passwordEditText.requestFocus();
                return;
            }

            // Vérification du format de l'e-mail
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError(getString(R.string.invalid_email_error));  // Utilisation de string pour l'erreur
                emailEditText.requestFocus();
                return;
            }
            // Authentification
            String fullName = db.validateUser(email, password);
            if (fullName != null) {
                // Succès
                Toast.makeText(LoginActivity.this, getString(R.string.welcome_toast, fullName), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Échec
                Toast.makeText(LoginActivity.this, getString(R.string.invalid_credentials_toast), Toast.LENGTH_SHORT).show();
            }

        });
        createAccountTextView.setOnClickListener(v -> {
            // Rediriger vers l'activité d'inscription
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflater le menu à partir du fichier XML
        getMenuInflater().inflate(R.menu.langue_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Vérifier l'ID de l'élément sélectionné
        int id = item.getItemId();
        if (id == R.id.menu_french) {
            setLocale("fr");
            return true;
        } else if (id == R.id.menu_english) {
            setLocale("en");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        // Redémarrer l'activité pour appliquer la langue choisie
        Toast.makeText(this, getString(R.string.change_language) + langCode, Toast.LENGTH_SHORT).show();
        Intent refresh = new Intent(this, LoginActivity.class);
        startActivity(refresh);
        finish(); // Cette méthode redémarre l'activité pour appliquer les changements
    }
}
