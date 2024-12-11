package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class ScienceQuizActivity extends AppCompatActivity {

    private TextView questionTextView, timerTextView;
    private Button option1Button, option2Button, option3Button, option4Button, nextButton,logoutButton,homeButton;

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
        logoutButton = findViewById(R.id.logoutButton);
        homeButton = findViewById(R.id.homeButton);

        // Initialiser les questions
        questions = new Question[]{
                new Question("Quelle est la capitale de la France?", new String[]{"Paris", "Berlin", "Madrid", "Rome"}, 0),
                new Question("Quel est l'élément chimique de symbole O?", new String[]{"Oxygène", "Ozone", "Or", "Osmium"}, 0),
                new Question("Quel est le plus grand océan du monde?", new String[]{"Atlantique", "Indien", "Arctique", "Pacifique"}, 3),
                new Question("Qui a écrit '1984'?", new String[]{"George Orwell", "J.K. Rowling", "Ernest Hemingway", "Mark Twain"}, 0),
                new Question("Quel est l'animal terrestre le plus rapide?", new String[]{"Guépard", "Lion", "Tigre", "Éléphant"}, 0),
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
        // Action pour le bouton Déconnexion
        logoutButton.setOnClickListener(view -> {
            //showToast("Déconnexion réussie");
            Intent intent = new Intent(ScienceQuizActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Action pour le bouton Accueil
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ScienceQuizActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
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
}
