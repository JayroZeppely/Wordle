package com.example.wordle;

import static java.lang.Math.min;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
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

    @SuppressLint("UseCompatLoadingForDrawables")
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
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        LinearLayout container = findViewById(R.id.grille);

        for (int ligne = 0; ligne < nbEssaisMax; ligne++) {
            LinearLayout linear = new LinearLayout(this);
            linear.setOrientation(LinearLayout.HORIZONTAL);
            List<TextView> ligneList = new ArrayList<>();
            for (int colonne = 0; colonne < longueur; colonne++) {
                TextView tv = new TextView(this);
                Log.d("GRILLE","CASE CREE");
                tv.setBackground(getDrawable(R.drawable.border));
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(24);
                int taille = min((width - 30)/ longueur, 180);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(taille, taille);
                params.setMargins(2,2,2,2);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                linear.setGravity(Gravity.CENTER);
                linear.addView(tv);
                ligneList.add(tv);
            }
            Log.d("GRILLE","Ligne ajoutée");
            tentatives.add(ligneList);
            container.addView(linear);
        }
    }
}