package myfunnylove.locidnet.lullaby.ui.base;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import myfunnylove.locidnet.lullaby.M;

/**
 * Created by Michaelan on 12/11/2016.
 */

public class Preferences {


    private SharedPreferences sharedPreferences;
    private static Preferences instance;
    public static Preferences getInstance(){
        if (instance == null){
            instance = new Preferences();
        }
        return instance;
    }

    public Preferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(M.getInstance().getApplicationContext());

    }


    public static final int OFF = 0;
    public static final int QUARTER = 1;
    public static final int HALF =2;
    public static final int HOUR = 3;
    public static final int HALF_HOUR = 4;
    public static final int HOUR_2 = 5;


    private static final String TIMER = "timer";
    public void setTimerId(int how){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TIMER,how);
        editor.apply();
        editor.commit();

    }

    public int getTimerId(){
        return sharedPreferences.getInt(TIMER,0);
    }
}
