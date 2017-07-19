package com.wgames.wiwo;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;

/**
 * Created by wietze on 2016-07-04.
 */
public class Pref implements PreferenceHandler{

    Context myContext;

    public Pref(Context context){
        myContext = context;
    }
    @Override
    public void openPreferences() {
        // to start the activity later
    }
}
