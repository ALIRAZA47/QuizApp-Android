package com.example.quizyy.utils;

import com.example.quizyy.R;

import java.util.Random;

public class IconPicker {
    int[] icons = {R.drawable.ic_award_cup_trophy_icon,
            R.drawable.ic_brain_idea_mind_icon,
            R.drawable.ic_calendar_date_schedule_icon,
            R.drawable.ic_checklist_document_list_paper_icon,
            R.drawable.ic_idea_light_lightbulb_icon
    };

    public int getIcons() {
        Random random = new Random();
        int rndIndex = random.nextInt(icons.length);
        return icons[rndIndex];
    }
}
