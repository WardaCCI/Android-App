package com.univ_amu.food_scanner.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import com.univ_amu.food_scanner.R;

public class NutriscoreView extends AppCompatTextView {
    public NutriscoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Modifier la taille du texte à 24sp
        setTextSize(24);
        // Modifier la gravité à Gravity.CENTER
        setGravity(Gravity.CENTER);
        // Modifier la couleur du texte à Color.WHITE
        setTextColor(Color.WHITE);
    }


    public void setNutriscore(String nutriscore) {
        if (nutriscore == null) return;
        // Afficher le nutriscore
        setText(nutriscore);
        // Modifier la couleur de fond en fonction du nutriscore
        setBackgroundColor(getContext().getResources().getColor(colorId(nutriscore),null));
    }

    private int colorId(String nutriscore) {

        switch (nutriscore.charAt(0)) {

            case 'A':

                return R.color.nutriscore_A;

            case 'B':

                return R.color.nutriscore_B;

            case 'C':

                return R.color.nutriscore_C;

            case 'D':

                return R.color.nutriscore_D;

            case 'E':

                return R.color.nutriscore_E;

            default:

                return R.color.unknown_nutriscore;

        }

    }
}
