package com.example.wordle;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class gameActivity extends AppCompatActivity {

    private wordManager wm;
    private String selectWord;
    private int longueur;
    private int nbEssaisMax;

    private List<List<TextView>> tentatives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        wm = new wordManager(this);
        selectWord = wm.getCurrentWord();
        longueur = wm.getWordSize();
        nbEssaisMax = wm.getMaxEssais();
        tentatives = new ArrayList<>();
        LinearLayout grille = new LinearLayout(this);
        grille.setOrientation(LinearLayout.VERTICAL);

        for (int ligne = 0; ligne <  nbEssaisMax; ligne++) {
            LinearLayout linear = new LinearLayout(this);
            linear.setOrientation(LinearLayout.HORIZONTAL);
            List<TextView> ligneList = new ArrayList<>();
            for (int colonne = 0; colonne < longueur; colonne++) {
                TextView tv = new TextView(this);
                linear.addView(tv);
                ligneList.add(tv);
            }
            tentatives.add(ligneList);
            grille.addView(linear);
        }
    }

}