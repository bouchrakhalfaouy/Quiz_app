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

        // Délai de 10 secondes avant de passer à l'activité suivante
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Terminer l'activité Splash pour qu'elle ne soit pas réaffichée en arrière-plan
        }, 10000); // 10 secondes
    }
}
