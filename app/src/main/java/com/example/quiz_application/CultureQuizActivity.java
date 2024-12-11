package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CultureQuizActivity extends AppCompatActivity {

    private TextView questionTextView, timerTextView;
    private Button option1Button, option2Button, option3Button, option4Button,logoutButton,homeButton;
    private Button nextButton;

    private int currentQuestionIndex = 0;
    private int score = 0;

    private CountDownTimer timer;
    private static final long TIME_PER_QUESTION = 30000; // 30 secondes par question

    private Question[] questions = {
            new Question("Quel est le capital de la France ?", new String[]{"Berlin", "Paris", "Madrid", "Rome"}, 1),
            new Question("Qui a écrit 'Les Misérables' ?", new String[]{"Victor Hugo", "Molière", "Emile Zola", "Marcel Proust"}, 0),
            new Question("Quel est le plus grand musée du monde ?", new String[]{"Louvre", "Musée d'Orsay", "Prado", "Vatican Museums"}, 0),
            // Ajoutez d'autres questions pour le domaine Culture
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_quiz);

        // Initialiser les vues
        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView); // Ajouter un TextView pour le timer dans le layout XML
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        nextButton = findViewById(R.id.nextButton);
        logoutButton = findViewById(R.id.logoutButton);
        homeButton = findViewById(R.id.homeButton);

        // Afficher la première question
        displayQuestion();

        // Écouter les clics des boutons de réponse
        option1Button.setOnClickListener(view -> checkAnswer(0));
        option2Button.setOnClickListener(view -> checkAnswer(1));
        option3Button.setOnClickListener(view -> checkAnswer(2));
        option4Button.setOnClickListener(view -> checkAnswer(3));

        // Passer à la question suivante
        nextButton.setOnClickListener(view -> moveToNextQuestion());

        // Action pour le bouton Déconnexion
        logoutButton.setOnClickListener(view -> {
            //showToast("Déconnexion réussie");
            Intent intent = new Intent(CultureQuizActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Action pour le bouton Accueil
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(CultureQuizActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void displayQuestion() {
        // Réinitialiser le timer pour chaque question
        if (timer != null) {
            timer.cancel();
        }
        startTimer();

        // Obtenez la question actuelle et affichez-la
        Question currentQuestion = questions[currentQuestionIndex];
        questionTextView.setText(currentQuestion.getQuestionText());
        option1Button.setText(currentQuestion.getOptions()[0]);
        option2Button.setText(currentQuestion.getOptions()[1]);
        option3Button.setText(currentQuestion.getOptions()[2]);
        option4Button.setText(currentQuestion.getOptions()[3]);
    }

    private void startTimer() {
        timer = new CountDownTimer(TIME_PER_QUESTION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Temps restant : " + millisUntilFinished / 1000 + " secondes");
            }

            @Override
            public void onFinish() {
                Toast.makeText(CultureQuizActivity.this, "Temps écoulé!", Toast.LENGTH_SHORT).show();
                moveToNextQuestion();
            }
        };
        timer.start();
    }

    private void checkAnswer(int selectedOption) {
        if (timer != null) {
            timer.cancel();
        }

        Question currentQuestion = questions[currentQuestionIndex];

        if (selectedOption == currentQuestion.getCorrectAnswerIndex()) {
            score++; // Incrémenter le score si la réponse est correcte
            Toast.makeText(this, "Bonne réponse!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mauvaise réponse. La bonne réponse est : " + currentQuestion.getOptions()[currentQuestion.getCorrectAnswerIndex()], Toast.LENGTH_LONG).show();
        }
        disableAnswerButtons();
    }

    private void moveToNextQuestion() {
        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            enableAnswerButtons();
            displayQuestion();
        } else {
            showResults(); // Afficher les résultats lorsque toutes les questions sont terminées
        }
    }

    private void showResults() {
        if (timer != null) {
            timer.cancel();
        }

        Intent intent = new Intent(CultureQuizActivity.this, ResultsActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", questions.length);
        startActivity(intent);
        finish();
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
}