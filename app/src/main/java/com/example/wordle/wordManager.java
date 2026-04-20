package com.example.wordle;

import android.content.Context;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class wordManager {

    private List<String> mots;
    private int maxEssais;
    private String currentMot;
    private String langue;

    public wordManager(Context context, String langue) {
        getWords(context, langue);
        selectWord();
    }

    private void getWords(Context context, String langue) {
        mots = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.words);
            int size = is.available(); //retourne une estimation du nb de bytes
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer,"UTF-8");
            JSONObject obj = new JSONObject(json);

            maxEssais = obj.getInt("essaies");
            JSONArray wordsArray = obj.getJSONArray(langue);

            for (int i = 0; i < wordsArray.length(); i++) {
                mots.add(wordsArray.getString(i).toUpperCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectWord() {
        if (!mots.isEmpty()) {
            Random rdn = new Random();
            currentMot = mots.get(rdn.nextInt(mots.size()));
        }
    }

    public String getCurrentWord() {
        return currentMot;
    }

    public int getMaxEssais() {
        return maxEssais;
    }

    public int getWordSize() {
        return currentMot != null ? currentMot.length() : 0;
    }

    public boolean isValidWord(String word) {
        return mots.contains(word.toUpperCase());
    }
}

