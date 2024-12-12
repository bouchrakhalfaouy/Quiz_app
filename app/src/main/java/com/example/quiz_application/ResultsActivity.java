package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity {

    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Initialiser le TextView pour afficher le score
        scoreTextView = findViewById(R.id.scoreTextView);

        // Récupérer le score et le nombre total de questions passés via l'Intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0); // Valeur par défaut 0
        int totalQuestions = intent.getIntExtra("totalQuestions", 0); // Valeur par défaut 0

        // Afficher le score
        scoreTextView.setText("Votre score: " + score + " / " + totalQuestions);
    }
    public void onExitButtonClick(View view) {
        Intent intent = new Intent(ResultsActivity.this, HomeActivity.class); // ou vers la page d'accueil si nécessaire
        startActivity(intent);
        finish(); // Fermer l'activité actuelle
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflater le menu à partir du fichier XML
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Gérer les clics sur les options du menu
        if (item.getItemId() == R.id.menu_home) {
            Intent intent = new Intent(ResultsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            // Action pour Déconnecter
            showToast("Déconnexion réussie");
            Intent intent = new Intent(ResultsActivity.this, LoginActivity.class);
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
