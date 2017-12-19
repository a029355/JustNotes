package com.example.berti.justnotes;

import android.app.Activity;

/**
 * Created by berti on 19/12/2017.
 */

public class SetTheme {

    public static void setThemeToActivity(Activity activity, String theme){

        switch (theme){
            case "Green":
                activity.setTheme(R.style.Green);
            case "Default":
                activity.setTheme(R.style.AppTheme);
            default:
                activity.setTheme(R.style.Green);

        }
    }

}
