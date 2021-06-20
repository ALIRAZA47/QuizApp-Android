package com.example.quizyy.utils;

import java.util.Random;
import java.util.RandomAccess;

public class ColorPicker {
    String[] colors = {
            "#3E89DF",
            "#3685BC",
            "#D36280",
            "#E44F55",
            "#FA8056",
            "#818BCA",
            "#70659F",
            "#51BAB3",
            "#4FB66C",
            "#E3AD17",
            "#627991",
            "#EF8EAD",
            "#B5BFC6"
    };

    public String getColors() {
        Random random = new Random();
        int rndIndex = random.nextInt(colors.length);
        return colors[rndIndex];
    }
}
