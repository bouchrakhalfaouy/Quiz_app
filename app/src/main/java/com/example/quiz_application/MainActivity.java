package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Délai de 5 secondes avant de passer à l'activité suivante (Splash Screen)
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Terminer cette activité pour qu'elle ne soit pas réaffichée en arrière-plan
        }, 3000); // 5 secondes
    }

}
