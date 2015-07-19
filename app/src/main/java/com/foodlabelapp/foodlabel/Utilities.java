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

//    private double poundToKilo(double weight, boolean isImperial){
//        if(isImperial)
//        {
//            return weight * 2.2;
//        }
//        else {
//            return weight / 2.2;
//        }
//    }
//
//    private double heightConverter(double height, double foot, double inch, Boolean isImperial){
//        if(isImperial){
//            height = foot * 0.3048 + inch * 0.0254;
//            return height;
//        }
//        else{
//            return height;
//        }
//    }


    //Sedentary Boys(3-18+) = 1.00 Girls(3-18+):1.00 daily activities
    //LowActive Boys1.13 Girls 1.16 Men 1.11 Women1.12 + 30-60 min activities
    //Active: 1.26 1.31 1.25 1.27; 60+ moderate activity
    //VeryActive 1.42 1.56 1.48 1.45; 60 moderate + 60 vigorous or 120 moderate

    //BMR * light to none 1.2, light (1-3 per week) 1.375, moderate(3-5) *1.55, heavy (6-7) 1.725, very heavy (twice per dau extra heavy) *1.9,


    //hash = gender * 10 + (age * 5) + activity

    //TODO: Serving Size? Minerals
    //4+ fat 65g Sodium 2400 mg, Carb 300g
    public static double calculateEER(Integer gender, Integer age, Integer activityRange, double weight, double height){

        Integer ageRange = 0;

        if(age >= 18)
            ageRange = 1;

        Integer hash = gender * 8 + (ageRange * 4) + activityRange;
        double PA = 0;
        switch(hash){
            case 0: PA = 1.0; break;
            case 1: PA = 1.13; break;
            case 2: PA = 1.26; break;
            case 3: PA = 1.42; break;
            case 4: PA = 1.0; break;
            case 5: PA = 1.11; break;
            case 6: PA = 1.25; break;
            case 7: PA = 1.48; break;
            case 8: PA = 1.0; break;
            case 9: PA = 1.16; break;
            case 10: PA = 1.31; break;
            case 11: PA = 1.56; break;
            case 12: PA = 1.0; break;
            case 13: PA = 1.12; break;
            case 14: PA = 1.27; break;
            case 15: PA = 1.45; break;
        }

//        height = heightConverter(height, foot, inch, isImperial);
        height /= 100;
        double menEER = 662 - (9.53 * age) + PA * ((weight * 15.91) + (539.6 * height));
        double womenEER = 354 - (6.91 * age) + PA * ((weight * 9.36) + (726 * height));

        double EER = 0;
        if(gender == 0){
             EER = menEER;
        }
        else{
            EER = womenEER;
        }


        return EER;

    }

    public static double calculateBMR(Integer gender, Integer age, Integer activityRange, double weight, double height) {
        Integer hash = activityRange;
        double PA = 0;
        switch (hash) {
            case 0:
                PA = 1.2;
                break;
            case 1:
                PA = 1.375;
                break;
            case 2:
                PA = 1.55;
                break;
            case 3:
                PA = 1.725;
                break;
            case 4:
                PA = 1.9;
                break;
        }

        double BMR = 0;
        if (gender == 0) {
            double menBMR = 66 + (6.23 * weight) + (12.7 * height) - (6.8 * age);
            BMR = menBMR * BMR;
        } else {
            double womenBMR = 655 + (4.35 * weight) + (4.7 * height);
            BMR = womenBMR * BMR;
        }
        return BMR;

    }
}

