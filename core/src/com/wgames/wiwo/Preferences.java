package com.wgames.wiwo;

/**
 * Created by wietze on 2016-07-04.
 */
public class Preferences {
    static PreferenceHandler prefHandler;

    public Preferences(PreferenceHandler p){
        prefHandler = p;

    }

    public void openPref(){
        prefHandler.openPreferences();
    }
}
