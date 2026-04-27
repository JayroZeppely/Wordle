package com.example.wordle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    SharedPreferences spScore;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spScore = getSharedPreferences("Scores", Context.MODE_PRIVATE);
        TextView txt_statut = findViewById(R.id.txt_statut);
        TextView txt_mot = findViewById(R.id.txt_mot);
        TextView txt_score = findViewById(R.id.txt_currentScore);
        TextView txt_best = findViewById(R.id.txt_BestScore);
        txt_score.setText("Score actuel\n"+ spScore.getInt("score", 0));
        txt_best.setText("Meilleur score\n"+ spScore.getInt("bestScore", 0));

        String mot = Objects.requireNonNull(getIntent().getExtras()).getString("mot");
        boolean statut = Objects.requireNonNull(getIntent().getExtras()).getBoolean("statut");

        txt_mot.setText(mot);
        if (statut) {
            txt_mot.setTextColor(Color.GREEN);
            txt_statut.setText(R.string.success);
        } else {
            txt_mot.setTextColor(Color.RED);
            txt_statut.setText(R.string.failure);
        }
    }

    public void replay(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void quit(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}