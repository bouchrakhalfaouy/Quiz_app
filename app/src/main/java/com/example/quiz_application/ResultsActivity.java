package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikhaellopez.circularprogressbar.CircularProgressBar; // Ajoutez cette importation

public class ResultsActivity extends AppCompatActivity {

    private CircularProgressBar circularProgressBar; // Remplacez ProgressBar par CircularProgressBar
    private TextView percentageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Initialisation des vues
        circularProgressBar = findViewById(R.id.circularProgressBar);
        percentageTextView = findViewById(R.id.scoreTextView);
        Button exitButton = findViewById(R.id.exitButton);

        // Récupération du score via l'intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 1); // Évite la division par 0

        // Calcul du pourcentage
        int percentage = (int) ((score / (float) totalQuestions) * 100);

        // Mise à jour de la barre circulaire et du texte
        circularProgressBar.setProgress(percentage); // Cette méthode met à jour la progression de la barre
        percentageTextView.setText(percentage + "%");

        // Gestion du bouton retour à l'accueil
        exitButton.setOnClickListener(view -> {
            Intent homeIntent = new Intent(ResultsActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            Intent intent = new Intent(ResultsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
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