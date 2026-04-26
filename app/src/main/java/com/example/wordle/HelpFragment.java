package com.example.wordle;

import static java.lang.Math.min;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<List<TextView>> grilles;

    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        grilles = new ArrayList<>();
        LinearLayout grille1 = view.findViewById(R.id.grille1);
        LinearLayout grille2 = view.findViewById(R.id.grille2);
        LinearLayout grille3 = view.findViewById(R.id.grille3);
        grille1.setGravity(Gravity.CENTER);
        grille2.setGravity(Gravity.CENTER);
        grille3.setGravity(Gravity.CENTER);
        createLigne(grille1);
        createLigne(grille2);
        createLigne(grille3);
        return view;
    }

    private void createLigne(LinearLayout v) {
        List<TextView> list = new ArrayList<>();
        String[] lettres1 = {"T","R","O","U","P","E"};
        String[] lettres2 = {"B","O","U","G","E","R"};
        String[] lettres3 = {"M","A","N","G","E","R"};

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        for (int i =0; i < 6; i++) {
            TextView tv = new TextView(requireContext());
            Log.d("GRILLE","CASE CREE");
            tv.setBackground(getResources().getDrawable(R.drawable.border));
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(16);
            switch (grilles.size()) {
                case 0:
                    switch (list.size()) {
                        case 0:
                            tv.setText(lettres1[0]);
                            break;
                        case 1:
                            tv.setBackgroundColor(Color.YELLOW);
                            tv.setText(lettres1[1]);
                            break;
                        case 2:
                            tv.setText(lettres1[2]);
                            break;
                        case 3:
                            tv.setText(lettres1[3]);
                            break;
                        case 4:
                            tv.setText(lettres1[4]);
                            break;
                        case 5:
                            tv.setBackgroundColor(Color.YELLOW);
                            tv.setText(lettres1[5]);
                            break;
                    }
                    break;
                case 1:
                    switch (list.size()) {
                        case 0:
                            tv.setText(lettres2[0]);
                            break;
                        case 1:
                            tv.setText(lettres2[1]);
                            break;
                        case 2:
                            tv.setText(lettres2[2]);
                            break;
                        case 3:
                            tv.setBackgroundColor(Color.GREEN);
                            tv.setText(lettres2[3]);
                            break;
                        case 4:
                            tv.setBackgroundColor(Color.GREEN);
                            tv.setText(lettres2[4]);
                            break;
                        case 5:
                            tv.setBackgroundColor(Color.GREEN);
                            tv.setText(lettres2[5]);
                            break;
                    }
                    break;
                case 2:
                    tv.setBackgroundColor(Color.GREEN);
                    switch (list.size()) {
                        case 0:
                            tv.setText(lettres3[0]);
                            break;
                        case 1:
                            tv.setText(lettres3[1]);
                            break;
                        case 2:
                            tv.setText(lettres3[2]);
                            break;
                        case 3:
                            tv.setText(lettres3[3]);
                            break;
                        case 4:
                            tv.setText(lettres3[4]);
                            break;
                        case 5:
                            tv.setText(lettres3[5]);
                            break;
                    }
                    break;
            }
            int taille = min((width - 50)/ 6, 100);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(taille, taille);
            params.setMargins(2,2,2,2);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER);
            v.addView(tv);
            list.add(tv);
        }
        grilles.add(list);
    }
}