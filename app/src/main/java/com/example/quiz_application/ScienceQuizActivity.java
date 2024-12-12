package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScienceQuizActivity extends AppCompatActivity {

    private TextView questionTextView, timerTextView;
    private Button option1Button, option2Button, option3Button, option4Button, nextButton, logoutButton, homeButton;

    private Question[] questions;
    private int currentQuestionIndex = 0;
    private int score = 0; // Variable pour le score
    private CountDownTimer countDownTimer;
    private static final long TIME_PER_QUESTION = 30000; // 30 secondes en millisecondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_quiz);

        // Initialiser les vues
        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        nextButton = findViewById(R.id.nextQuestionButton);

        // Initialiser les questions
        questions = new Question[]{
                new Question("Quelle est la formule chimique de l'eau ?", new String[]{"H₂O", "CO₂", "NaCl", "O₂"}, 0),
                new Question("Quel est l'organe principal du système respiratoire humain ?", new String[]{"Le cœur", "Les reins", "Les poumons", "L'estomac"}, 2),
                new Question("Quelle planète est la plus proche du soleil ?", new String[]{"Mars", "Vénus", "Terre", "Mercure"}, 3),
                new Question("Quelle est la vitesse de la lumière dans le vide ?", new String[]{"299 792 458 mètres par seconde", "150 000 000 mètres par seconde", "120 000 000 mètres par seconde", "1 000 000 mètres par seconde"}, 0),
                new Question("Qui a proposé la théorie de la relativité ?", new String[]{"Isaac Newton", "Albert Einstein", "Nikola Tesla", "Galileo Galilei"}, 1),
                new Question("Quel est l'élément chimique ayant pour symbole 'O' ?", new String[]{"Or", "Hydrogène", "Oxygène", "Azote"}, 2),
                new Question("Quel est le plus grand organe du corps humain ?", new String[]{"Le foie", "Le cerveau", "La peau", "Les poumons"}, 2),
                new Question("Quelle force attire les objets vers le centre de la Terre ?", new String[]{"La friction", "L'attraction magnétique", "La gravité", "La pression atmosphérique"}, 2),
                new Question("Quelle est l'unité de mesure de la force dans le système international ?", new String[]{"Le kilogramme (kg)", "Le mètre (m)", "Le newton (N)", "Le joule (J)"}, 2),
                new Question("Quelle est la principale source d'énergie pour la Terre ?", new String[]{"Le vent", "Le soleil", "L'eau", "Le pétrole"}, 1)
        };
        // Afficher la première question
        displayQuestion();
        // Actions des boutons des options
        option1Button.setOnClickListener(view -> checkAnswer(0));
        option2Button.setOnClickListener(view -> checkAnswer(1));
        option3Button.setOnClickListener(view -> checkAnswer(2));
        option4Button.setOnClickListener(view -> checkAnswer(3));

        // Action pour le bouton suivant
        nextButton.setOnClickListener(view -> moveToNextQuestion());

    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.length) {
            Question currentQuestion = questions[currentQuestionIndex];
            questionTextView.setText(currentQuestion.getQuestionText());

            option1Button.setText(currentQuestion.getOptions()[0]);
            option2Button.setText(currentQuestion.getOptions()[1]);
            option3Button.setText(currentQuestion.getOptions()[2]);
            option4Button.setText(currentQuestion.getOptions()[3]);

            // Démarrer le timer
            startTimer();
        } else {
            // Si toutes les questions ont été répondues, afficher les résultats
            showResults();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(TIME_PER_QUESTION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                timerTextView.setText("Temps restant : " + secondsLeft + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Temps écoulé !");
                moveToNextQuestion(); // Passer automatiquement à la question suivante
            }
        }.start();
    }

    private void checkAnswer(int selectedOption) {
        Question currentQuestion = questions[currentQuestionIndex];

        if (selectedOption == currentQuestion.getCorrectAnswerIndex()) {
            score++; // Incrémenter le score si la réponse est correcte
            Toast.makeText(this, "Bonne réponse!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mauvaise réponse. La bonne réponse est : " +
                    currentQuestion.getOptions()[currentQuestion.getCorrectAnswerIndex()], Toast.LENGTH_LONG).show();
        }

        disableAnswerButtons();
    }

    private void disableAnswerButtons() {
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
    }

    private void enableAnswerButtons() {
        option1Button.setEnabled(true);
        option2Button.setEnabled(true);
        option3Button.setEnabled(true);
        option4Button.setEnabled(true);
    }

    private void moveToNextQuestion() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        currentQuestionIndex++;
        enableAnswerButtons();
        displayQuestion();
    }

    private void showResults() {
        Toast.makeText(this, "Votre score final est : " + score + " sur " + questions.length, Toast.LENGTH_LONG).show();

        // Passer à l'activité des résultats
        Intent intent = new Intent(ScienceQuizActivity.this, ResultsActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", questions.length);
        startActivity(intent);
        finish();
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
            Intent intent = new Intent(ScienceQuizActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            // Action pour Déconnecter
            showToast("Déconnexion réussie");
            Intent intent = new Intent(ScienceQuizActivity.this, LoginActivity.class);
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
