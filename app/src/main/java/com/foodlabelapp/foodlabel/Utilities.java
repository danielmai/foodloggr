package com.foodlabelapp.foodlabel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by Daniel on 7/18/15.
 */
public class Utilities {

    public static Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        if (bmpOriginal == null) {
            return null;
        }
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public double poundToKilo(double weight, boolean isPound){
        if(isPound)
        {
            return weight * 2.2;
        }
        else {
            return weight / 2.2;
        }
    }

    public Float heightConverter(Float height, Boolean isFoot){
        if(isFoot){
            return height; // to m
        }
        else{
            return height;
        }
    }


    //Sedentary Boys(3-18+) = 1.00 Girls(3-18+):1.00 daily activities
    //LowActive Boys1.13 Girls1.16 Men1.11 Women1.12 + 30-60 min activities
    //Active: 1.26 1.31 1.25 1.27; 60+ moderate activity
    //VeryActive 1.42 1.56 1.48 1.45; 60 moderate + 60 vigorous or 120 moderate

    //BMR * light to none 1.2, light (1-3 per week) 1.375, moderate(3-5) *1.55, heavy (6-7) 1.725, very heavy (twice per dau extra heavy) *1.9,

    public void calculateRecommended(Integer age, Float weight, Float height, Boolean isPound){
        double menEER = 662 - (9.53 * age) + (poundToKilo(weight, isPound) * 15.91) + (539.6 * height);
        double womenEER = 354 - (6.91 * age) + (poundToKilo(weight, isPound) * 9.36) + (726 * height);

        double menBMR = 66 + (6.23 * poundToKilo(weight, isPound) + (12.7 * height) - (6.8 * age)

        double womenBMR = 655 + (4.35 * poundToKilo(weight, isPound) + (4.7 * height) -
    }

}

