package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button homeButton, logoutButton, scienceButton, cultureButton, artButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Boutons d'en-tête
        homeButton = findViewById(R.id.homeButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Boutons des domaines
        scienceButton = findViewById(R.id.scienceButton);
        cultureButton = findViewById(R.id.cultureButton);
        artButton = findViewById(R.id.artButton);

        // Action pour le bouton Accueil
        homeButton.setOnClickListener(view ->
                // Pas besoin de navigation, déjà sur la page d'accueil
                showToast("Vous êtes déjà sur la page d'accueil")
        );

        // Action pour le bouton Déconnexion
        logoutButton.setOnClickListener(view -> {
            showToast("Déconnexion réussie");
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Actions des boutons des domaines
        scienceButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ScienceQuizActivity.class);
            startActivity(intent);
        });

        cultureButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CultureQuizActivity.class);
            startActivity(intent);
        });

        artButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ArtQuizActivity.class);
            startActivity(intent);
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
