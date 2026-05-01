package com.example.wordle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sp;

    public SettingsFragment() {
        // Required empty public constructor
    }

    private Button btn_currentLenght, btn_currentLang;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void changeLangue(String langue) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("langue", langue);
        editor.apply();
        reinit_score();
    }

    public void changeLongueur(int longueur) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("longueur", longueur);
        editor.apply();
        reinit_score();
    }

    public void changeMaxAttempts(int nb) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("nbAttempts", nb);
        editor.apply();
        reinit_score();
    }

    public void reinitialiserParams() {
        changeLangue("french");
        changeLongueur(0);
        changeMaxAttempts(6);
        Button btn_random = requireActivity().findViewById(R.id.btn_random);
        Button btn_french = requireActivity().findViewById(R.id.btn_french);
        changeLangSelected(btn_french);
        changeLengthSelected(btn_random);
        reinit_score();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        //Boutons des langues
        Button btn_french = view.findViewById(R.id.btn_french);
        Button btn_english = view.findViewById(R.id.btn_english);
        Button btn_spain = view.findViewById(R.id.btn_spain);
        btn_french.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLangue("french");
                changeLangSelected(btn_french);
            }
        });
        btn_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLangue("english");
                changeLangSelected(btn_english);
            }
        });
        btn_spain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLangue("spain");
                changeLangSelected(btn_spain);
            }
        });

        //Boutons pour la longueur
        Button btn_four = view.findViewById(R.id.btn_four);
        Button btn_five = view.findViewById(R.id.btn_five);
        Button btn_six = view.findViewById(R.id.btn_six);
        Button btn_random = view.findViewById(R.id.btn_random);
        btn_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLongueur(4);
                changeLengthSelected(btn_four);
            }});
        btn_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {changeLongueur(5);
                changeLengthSelected(btn_five);}});
        btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {changeLongueur(6);
                changeLengthSelected(btn_six);}});
        btn_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {changeLongueur(0);
                changeLengthSelected(btn_random);}});

        //Slider
        Slider slide = view.findViewById(R.id.slider_attempts);
        TextView txt_nbAttempts = view.findViewById(R.id.txt_value_nbAttempts);
        slide.setValue(sp.getInt("nbAttempts",6));
        int nb = Math.round(slide.getValue());
        txt_nbAttempts.setText(String.valueOf(nb));
        slide.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                int nb = Math.round(v);
                txt_nbAttempts.setText(String.valueOf(nb));
                changeMaxAttempts(nb);
            }
        });

        Button btn_reinit = view.findViewById(R.id.btn_reinit);
        btn_reinit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reinitialiserParams();
                slide.setValue(6);
            }
        });

        //Initialiser les options selectionner
        String lang = sp.getString("langue", "french");
        int length = sp.getInt("longueur", 0);
        switch (lang) {
            case "french":
                btn_french.setBackgroundColor(getResources().getColor(R.color.vert));
                btn_currentLang = btn_french;
                break;
            case "english":
                btn_english.setBackgroundColor(getResources().getColor(R.color.vert));
                btn_currentLang = btn_english;
                break;
            case "spain":
                btn_spain.setBackgroundColor(getResources().getColor(R.color.vert));
                btn_currentLang = btn_spain;
                break;
        }

        switch (length) {
            case 0:
                btn_random.setBackgroundColor(getResources().getColor(R.color.vert));
                btn_currentLenght = btn_random;
                break;
            case 4:
                btn_four.setBackgroundColor(getResources().getColor(R.color.vert));
                btn_currentLenght = btn_four;
                break;
            case 5:
                btn_five.setBackgroundColor(getResources().getColor(R.color.vert));
                btn_currentLenght = btn_five;
                break;
            case 6:
                btn_six.setBackgroundColor(getResources().getColor(R.color.vert));
                btn_currentLenght = btn_six;
                break;
        }
        return view;
    }

    private void reinit_score() {
        SharedPreferences spScore = requireActivity().getSharedPreferences("Scores", Context.MODE_PRIVATE);
        int score = spScore.getInt("score", 0);
        int best = spScore.getInt("bestScore", 0);
        SharedPreferences.Editor editor = spScore.edit();
        if (score > best) {
            editor.putInt("bestScore", score);
        }
        editor.putInt("score", 0);
        editor.apply();
    }

    public void changeLangSelected(Button newButton) {
        btn_currentLang.setBackgroundColor(getResources().getColor(R.color.default_setting));
        newButton.setBackgroundColor(getResources().getColor(R.color.vert));
        btn_currentLang = newButton;
    }

    public void changeLengthSelected(Button newButton) {
        btn_currentLenght.setBackgroundColor(getResources().getColor(R.color.default_setting));
        newButton.setBackgroundColor(getResources().getColor(R.color.vert));
        btn_currentLenght = newButton;
    }
}