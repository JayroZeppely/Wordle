package com.example.wordle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

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

        TextView txt_statut = findViewById(R.id.txt_statut);
        TextView txt_mot = findViewById(R.id.txt_mot);

        String mot = Objects.requireNonNull(getIntent().getExtras()).getString("mot");
        boolean statut = Objects.requireNonNull(getIntent().getExtras()).getBoolean("statut");

        txt_mot.setText(mot);
        if (statut) {
            txt_statut.setTextColor(Color.GREEN);
        } else {
            txt_statut.setTextColor(Color.RED);
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