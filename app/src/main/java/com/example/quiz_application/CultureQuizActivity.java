package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CultureQuizActivity extends AppCompatActivity {

    private TextView questionTextView, timerTextView;
    private Button option1Button, option2Button, option3Button, option4Button, nextButton;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private CountDownTimer timer;
    private static final long TIME_PER_QUESTION = 30000; // 30 secondes par question

    private Question[] questions = {
            new Question("Quelle est la capitale de la France ?", new String[]{"Berlin", "Paris", "Madrid", "Rome"}, 1),
            new Question("Qui a écrit 'Les Misérables' ?", new String[]{"Victor Hugo", "Molière", "Emile Zola", "Marcel Proust"}, 0),
            new Question("Quel est le plus grand musée du monde ?", new String[]{"Louvre", "Musée d'Orsay", "Prado", "Vatican Museums"}, 0),
            new Question("Quelle est la capitale de l'Italie ?", new String[]{"Berlin", "Paris", "Rome", "Madrid"}, 2),
            new Question("Qui a peint la Joconde ?", new String[]{"Vincent Van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"}, 2),
            new Question("Quelle est la langue officielle du Brésil ?", new String[]{"Espagnol", "Anglais", "Portugais", "Français"}, 2),
            new Question("Qui a écrit 'Hamlet' ?", new String[]{"Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"}, 1),
            new Question("Quel est le plus long fleuve du monde ?", new String[]{"Amazone", "Nil", "Yangtsé", "Mississippi"}, 0),
            new Question("Quelle est la plus petite planète du système solaire ?", new String[]{"Mars", "Mercure", "Vénus", "Terre"}, 1),
            new Question("Qui a écrit 'Le Seigneur des Anneaux' ?", new String[]{"J.K. Rowling", "J.R.R. Tolkien", "George R.R. Martin", "C.S. Lewis"}, 1),
            new Question("Quelle est la monnaie utilisée au Japon ?", new String[]{"Dollar", "Euro", "Yen", "Yuan"}, 2),
            new Question("Quel pays est connu comme le pays du soleil levant ?", new String[]{"Chine", "Japon", "Corée du Sud", "Thaïlande"}, 1),
            new Question("Qui a découvert l'Amérique en 1492 ?", new String[]{"Christophe Colomb", "Amerigo Vespucci", "Ferdinand Magellan", "Marco Polo"}, 0),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_quiz);

        // Initialiser les vues
        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        nextButton = findViewById(R.id.nextButton);

        // Afficher la première question
        displayQuestion();

        // Écouter les clics des boutons de réponse
        option1Button.setOnClickListener(view -> checkAnswer(0));
        option2Button.setOnClickListener(view -> checkAnswer(1));
        option3Button.setOnClickListener(view -> checkAnswer(2));
        option4Button.setOnClickListener(view -> checkAnswer(3));

        // Passer à la question suivante
        nextButton.setOnClickListener(view -> moveToNextQuestion());
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

        // Réinitialiser les couleurs des boutons avant d'afficher la question suivante
        resetButtonColors();
        enableAnswerButtons();
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
        Button selectedButton = getButtonForOption(selectedOption);

        // Vérifier la bonne réponse
        if (selectedOption == currentQuestion.getCorrectAnswerIndex()) {
            score++; // Incrémenter le score si la réponse est correcte
            Toast.makeText(this, "Bonne réponse!", Toast.LENGTH_SHORT).show();
            selectedButton.setBackgroundColor(getResources().getColor(R.color.green)); // Vert
        } else {
            Toast.makeText(this, "Mauvaise réponse. La bonne réponse est : " +
                    currentQuestion.getOptions()[currentQuestion.getCorrectAnswerIndex()], Toast.LENGTH_LONG).show();
            selectedButton.setBackgroundColor(getResources().getColor(R.color.red)); // Rouge

            // Mettre en vert le bouton de la bonne réponse
            Button correctButton = getButtonForOption(currentQuestion.getCorrectAnswerIndex());
            correctButton.setBackgroundColor(getResources().getColor(R.color.green)); // Vert
        }

        // Désactiver les boutons après la réponse
        disableAnswerButtons();
    }

    private Button getButtonForOption(int optionIndex) {
        switch (optionIndex) {
            case 0:
                return option1Button;
            case 1:
                return option2Button;
            case 2:
                return option3Button;
            case 3:
                return option4Button;
            default:
                return null;
        }
    }

    private void moveToNextQuestion() {
        if (timer != null) {
            timer.cancel();
        }

        // Vérifier si le quiz est terminé
        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            displayQuestion();
        } else {
            showResults(); // Si toutes les questions ont été répondues
        }
    }

    private void resetButtonColors() {
        // Réinitialiser la couleur de fond des boutons à la couleur par défaut (transparente ou autre couleur)
        option1Button.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); // ou utilisez la couleur par défaut
        option2Button.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        option3Button.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        option4Button.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
    }

    private void showResults() {
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
            Intent intent = new Intent(CultureQuizActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            showToast("Déconnexion réussie");
            Intent intent = new Intent(CultureQuizActivity.this, LoginActivity.class);
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