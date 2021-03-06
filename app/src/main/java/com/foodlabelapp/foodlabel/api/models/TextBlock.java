package com.foodlabelapp.foodlabel.api.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel on 7/18/15.
 */
public class TextBlock {
    public String text;
    public int left;
    public int right;
    public int width;
    public int height;

    public TextBlock(String text) {
        this.text = text;
    }

    public List<String> getTextList() {
        return new ArrayList<>(Arrays.asList(text.split("\n")));
    }
}
