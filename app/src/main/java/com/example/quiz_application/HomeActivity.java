package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private ImageButton scienceButton, cultureButton, artButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Boutons des domaines
        scienceButton = findViewById(R.id.scienceButton);
        cultureButton = findViewById(R.id.cultureButton);
        artButton = findViewById(R.id.artButton);

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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Gonfler le menu (ajouter les options dans la barre d'outils)
        getMenuInflater().inflate(R.menu.menu, menu); // Assurez-vous que le fichier XML existe
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Gérer les clics sur les options du menu
        if (item.getItemId() == R.id.menu_home) {
            // Action pour Accueil
            showToast("Vous êtes déjà sur la page d'accueil");
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            // Action pour Déconnecter
            showToast("Déconnexion réussie");
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}