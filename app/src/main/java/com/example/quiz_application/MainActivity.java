package com.example.quiz_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
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
        }, 5000); // 5 secondes
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Gonfler le menu (ajouter les options dans la barre d'outils)
        getMenuInflater().inflate(R.menu.menu, menu); // Assurez-vous que le fichier "menu.xml" existe dans res/menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            startActivity(new Intent(this, HomeActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {

            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
