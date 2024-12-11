package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ArtQuizActivity extends AppCompatActivity {

    private TextView questionTextView, timerTextView;
    private Button option1Button, option2Button, option3Button, option4Button,logoutButton,homeButton;
    private Button nextButton;

    private int currentQuestionIndex = 0;
    private int score = 0;

    private CountDownTimer timer;
    private static final long TIME_PER_QUESTION = 30000; // 30 secondes

    private Question[] questions = {
            new Question("Qui a peint la Mona Lisa ?", new String[]{"Vincent van Gogh", "Pablo Picasso", "Léonard de Vinci", "Claude Monet"}, 2),
            new Question("Quel est le courant artistique de 'La Nuit étoilée' ?", new String[]{"Impressionnisme", "Cubisme", "Réalisme", "Post-impressionnisme"}, 3),
            new Question("Quel peintre a utilisé la technique du 'Pointillisme' ?", new String[]{"Georges Seurat", "Claude Monet", "Henri Matisse", "Pierre-Auguste Renoir"}, 0),
            new Question("Qui a peint 'Les Demoiselles d'Avignon' ?", new String[]{"Vincent van Gogh", "Pablo Picasso", "Léonard de Vinci", "Claude Monet"}, 1),
            new Question("Quel mouvement artistique est associé à Salvador Dalí ?", new String[]{"Cubisme", "Surréalisme", "Impressionnisme", "Expressionnisme"}, 1),
            new Question("Quel peintre est célèbre pour ses nénuphars ?", new String[]{"Paul Cézanne", "Vincent van Gogh", "Claude Monet", "Édouard Manet"}, 2),
            new Question("Quel artiste a peint 'La naissance de Vénus' ?", new String[]{"Sandro Botticelli", "Michel-Ange", "Raphaël", "Donatello"}, 0),
            new Question("Qui est l'auteur du tableau 'Le Cri' ?", new String[]{"Edvard Munch", "Gustav Klimt", "Egon Schiele", "Wassily Kandinsky"}, 0),
            new Question("Dans quel musée se trouve 'La Joconde' ?", new String[]{"Musée du Prado", "Musée d'Orsay", "Louvre", "British Museum"}, 2),
            new Question("Quel artiste est connu pour ses 'boîtes de soupe Campbell' ?", new String[]{"Andy Warhol", "Roy Lichtenstein", "Jean-Michel Basquiat", "Jasper Johns"}, 0),
            new Question("Quelle technique artistique utilise des petits points de couleur pour créer une image ?", new String[]{"Cubisme", "Impressionnisme", "Pointillisme", "Surréalisme"}, 2),
            new Question("Quel sculpteur est connu pour 'Le Penseur' ?", new String[]{"Auguste Rodin", "Henry Moore", "Constantin Brâncuși", "Alberto Giacometti"}, 0),
            new Question("Qui a peint 'Guernica' ?", new String[]{"Salvador Dalí", "Pablo Picasso", "Joan Miró", "Henri Matisse"}, 1)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_quiz);

        // Initialiser les vues
        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView);
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
        nextButton.setOnClickListener(view -> goToNextQuestion());

        // Action pour le bouton Déconnexion
        logoutButton.setOnClickListener(view -> {
            //showToast("Déconnexion réussie");
            Intent intent = new Intent(ArtQuizActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Action pour le bouton Accueil
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ArtQuizActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void displayQuestion() {
        // Annuler le timer précédent s'il existe
        if (timer != null) {
            timer.cancel();
        }

        // Obtenez la question actuelle et affichez-la
        Question currentQuestion = questions[currentQuestionIndex];
        questionTextView.setText(currentQuestion.getQuestionText());
        option1Button.setText(currentQuestion.getOptions()[0]);
        option2Button.setText(currentQuestion.getOptions()[1]);
        option3Button.setText(currentQuestion.getOptions()[2]);
        option4Button.setText(currentQuestion.getOptions()[3]);

        // Démarrer le timer pour la question
        startTimer();
    }

    private void startTimer() {
        timer = new CountDownTimer(TIME_PER_QUESTION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Temps restant : " + millisUntilFinished / 1000 + " secondes");
            }

            @Override
            public void onFinish() {
                Toast.makeText(ArtQuizActivity.this, "Temps écoulé !", Toast.LENGTH_SHORT).show();
                goToNextQuestion();
            }
        };
        timer.start();
    }

    private void checkAnswer(int selectedOption) {
        Question currentQuestion = questions[currentQuestionIndex];

        if (selectedOption == currentQuestion.getCorrectAnswerIndex()) {
            score++; // Incrémenter le score si la réponse est correcte
            Toast.makeText(this, "Bonne réponse!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mauvaise réponse. La bonne réponse est : " + currentQuestion.getOptions()[currentQuestion.getCorrectAnswerIndex()], Toast.LENGTH_LONG).show();
        }
        disableAnswerButtons();
    }

    private void goToNextQuestion() {
        if (timer != null) {
            timer.cancel();
        }

        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            enableAnswerButtons();
            displayQuestion();
        } else {
            // Si toutes les questions sont terminées, afficher le score
            Intent intent = new Intent(ArtQuizActivity.this, ResultsActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("totalQuestions", questions.length);
            startActivity(intent);
            finish();
        }
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