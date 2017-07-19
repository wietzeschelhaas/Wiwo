package com.wgames.wiwo;

import android.content.Context;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;



public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		initialize(new Wiwo(new DataBase(new DatabaseHelper(this.getBaseContext())),
				new Preferences(new Pref(this.getBaseContext()))), config);

	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("test");
	}
}
