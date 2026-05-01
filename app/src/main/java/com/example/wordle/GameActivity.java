package com.example.wordle;

import static java.lang.Math.min;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    SharedPreferences sp, spScore;
    private wordManager wm;
    private String selectWord;

    private StringBuilder currentWord;
    private int longueur;
    private int nbEssaisMax;

    private Handler mHandler;

    private boolean statusPartie;

    //Pour suivre l'avancement de la partie
    private int currentLigne, currentColonne, currentEssaie = 0;

    //Va permettre de stocker les btn pour ensuite pouvoir les modifier
    private Map<String,Button> clavier;
    private List<List<TextView>> tentatives;

    private Button delete, enter;

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

        sp = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        spScore = getSharedPreferences("Scores", Context.MODE_PRIVATE);
        String langue = sp.getString("langue", "french");
        longueur = sp.getInt("longueur",0);
        nbEssaisMax = sp.getInt("nbAttempts",6);
        wm = new wordManager(this, langue, longueur, nbEssaisMax);
        selectWord = wm.getCurrentWord();
        Log.d("MOT",selectWord);
        longueur = wm.getWordSize();
        tentatives = new ArrayList<>();
        statusPartie = false;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
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
                int taille = min((width - 30)/ longueur, 160);
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

        LinearLayout azerty = findViewById(R.id.clavier);
        //Préparation clavier
        List<String> touches1 = Arrays.asList("A","Z","E","R","T","Y","U","I","O","P");
        List<String> touches2 = Arrays.asList("Q","S","D","F","G","H","J","K","L","M");
        List<String> touches3 = Arrays.asList("W","X","C","V","B","N");

        //Initialisation map
        clavier = new HashMap<String,Button>();

        //Preparation du background des boutons
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(16f);
        drawable.setColor(getColor(R.color.vert));

        GradientDrawable btns_del_enter = new GradientDrawable();
        btns_del_enter.setShape(GradientDrawable.RECTANGLE);
        btns_del_enter.setCornerRadius(16f);
        btns_del_enter.setColor(Color.BLUE);


        for (int l = 0; l < 3; l++) {
            LinearLayout ligne = new LinearLayout(this);
            ligne.setOrientation(LinearLayout.HORIZONTAL);
            ligne.setGravity(Gravity.CENTER);
            switch (l) {
                case 0 :
                    for (String lettre : touches1) {
                        Button btn = new Button(this);
                        btn.setText(lettre);
                        btn.setTextColor(Color.WHITE);
                        btn.setBackground(drawable);
                        btn.setTextSize(17);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(95,120);
                        params.setMargins(4, 4, 4, 4);
                        btn.setLayoutParams(params);
                        ligne.addView(btn);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ajouterLettre(lettre);
                            }
                        });
                        clavier.put(lettre, btn);
                    }
                    Log.d("CLAVIER",touches1.toString());
                    break;
                case 1 :
                    for (String lettre : touches2) {
                        Button btn = new Button(this);
                        btn.setText(lettre);
                        btn.setTextColor(Color.WHITE);
                        btn.setBackground(drawable);
                        btn.setTextSize(17);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(95,120);
                        params.setMargins(4, 4, 4, 4);
                        btn.setLayoutParams(params);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ajouterLettre(lettre);
                            }
                        });
                        ligne.addView(btn);
                        clavier.put(lettre, btn);
                    }
                    Log.d("CLAVIER",touches2.toString());
                    break;
                case 2 :
                    //Bouton Delete
                    delete = new Button(this);
                    delete.setText("Del");
                    delete.setTextColor(Color.WHITE);
                    delete.setBackground(btns_del_enter);
                    delete.setTextSize(14);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(170,120);
                    params.setMargins(4, 4, 4, 4);
                    delete.setLayoutParams(params);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            supprimerLettre();
                        }
                    });
                    ligne.addView(delete);
                    clavier.put("←", delete);
                    for (String lettre : touches3) {
                        Button btn = new Button(this);
                        btn.setText(lettre);
                        btn.setTextColor(Color.WHITE);
                        btn.setBackground(drawable);
                        btn.setTextSize(17);
                        params = new LinearLayout.LayoutParams(95,120);
                        params.setMargins(4, 4, 4, 4);
                        btn.setLayoutParams(params);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ajouterLettre(lettre);
                            }
                        });
                        ligne.addView(btn);
                        clavier.put(lettre, btn);
                    }
                    enter = new Button(this);
                    enter.setText("Enter");
                    enter.setTextColor(Color.WHITE);
                    enter.setBackground(btns_del_enter);
                    enter.setTextSize(14);
                    params = new LinearLayout.LayoutParams(200,120);
                    params.setMargins(4, 4, 4, 4);
                    enter.setLayoutParams(params);
                    enter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmerMot();
                        }
                    });
                    ligne.addView(enter);
                    clavier.put("↑", enter);
                    Log.d("CLAVIER",touches3.toString());
                    break;
            }
            azerty.addView(ligne);
        }
        partie();
    }

    private void partie() {
        currentWord = new StringBuilder();
        delete.setEnabled(false);
        enter.setEnabled(false);
    }

    private void ajouterLettre(String lettre) {
        if (currentLigne < nbEssaisMax) {
            if (currentColonne < longueur) {
                tentatives.get(currentLigne).get(currentColonne).setText(lettre);
                currentColonne += 1;
                currentWord.append(lettre);
                Log.d("MOT",currentWord.toString());
                if (currentColonne > 0 )
                    delete.setEnabled(true);
                if (currentColonne == longueur)
                    enter.setEnabled(true);
            }
        }
    }
    private void supprimerLettre() {
        if (currentColonne > 0) {
            currentColonne -=1;
            tentatives.get(currentLigne).get(currentColonne).setText("");
            currentWord.deleteCharAt(currentWord.length() - 1);
            Log.d("MOT",currentWord.toString());
            if (currentColonne == 0)
                delete.setEnabled(false);
            if (currentColonne < longueur) {
                enter.setEnabled(false);
            }
        }
    }

    private void confirmerMot() {
        if (currentColonne == longueur && wm.isValidWord(currentWord.toString())) {
            if (verifierTentative()) {
                Toast.makeText(this,"Partie terminée", Toast.LENGTH_SHORT).show();
                setScore(spScore.getInt("score",0) + 1);
                statusPartie = true;
                mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(goToResult, 2000);
                return;
            }
            currentColonne = 0;
            currentLigne += 1;
            if (currentLigne >= nbEssaisMax) {
                Toast.makeText(this,"Partie terminée", Toast.LENGTH_SHORT).show();
                setScore(0);
                mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(goToResult, 2000);
            } else {
                currentWord = new StringBuilder();
                Log.d("MOT",currentWord.toString());
                delete.setEnabled(false);
                enter.setEnabled(false);
            }
        } else if (!wm.isValidWord(currentWord.toString())){
            Toast.makeText(this, "Ce mot n'est pas dans le dictionnaire", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verifierTentative() {
        List<TextView> tentative = tentatives.get(currentLigne);
        if (currentWord.toString().equals(selectWord)) {
            for (int n = 0; n < longueur; n++) {
                tentative.get(n).setBackgroundColor(getColor(R.color.found));
            }
            return true;
        } else {
            for (int n = 0; n < longueur; n++) {
                if (currentWord.charAt(n) == selectWord.charAt(n)) {
                    tentative.get(n).setBackgroundColor(getColor(R.color.found));
                } else if (selectWord.contains(String.valueOf(currentWord.charAt(n))) && currentWord.charAt(n) != selectWord.charAt(n)) {
                    tentative.get(n).setBackgroundColor(getColor(R.color.wrong_place));
                }
            }
            return false;
        }
    }

    private Runnable goToResult = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra("statut", statusPartie);
            intent.putExtra("mot", selectWord);
            startActivity(intent);
        }
    };

    private void setScore(int score) {
        SharedPreferences.Editor editor = spScore.edit();
        editor.putInt("score", score);
        int best = spScore.getInt("bestScore", 0);
        if (score > best)
            editor.putInt("bestScore", score);
        editor.apply();
    }
}