package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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
}
