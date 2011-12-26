package com.styx.main;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

import com.styx.R;
import com.styx.R.drawable;
import com.styx.R.id;
import com.styx.R.layout;
import com.styx.R.menu;
import com.styx.calibrate.CalibrateActivity;
import com.styx.configuration.ConfigurationActivity;
import com.styx.detail.DetailActivity;
import com.styx.lib.Config;
import com.styx.measurement.MeasurementActivity;
import com.styx.widgets.Compas;

public class MainActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Config.setupInstance(getApplicationContext());

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, MeasurementActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("new")
				.setIndicator("New Measurement",
						res.getDrawable(R.drawable.ic_menu_wizard))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, DetailActivity.class);

		spec = tabHost
				.newTabSpec("detail")
				.setIndicator("Details",
						res.getDrawable(R.drawable.ic_menu_puzzle))
				.setContent(intent);
		tabHost.addTab(spec);

		if (Config.getHeight() == 0 || Config.getUprightAngle() == 0)
			tabHost.setCurrentTab(3);
		else
			tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.calibrate:
	        newCalibrate();
	        return true;
	    case R.id.configure:
	    	newConfigure();
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}


	private void newConfigure() {
		Intent intent = new Intent().setClass(this, ConfigurationActivity.class);
		startActivity(intent);
	}


	private void newCalibrate() {
		Intent intent = new Intent().setClass(this, CalibrateActivity.class);
		startActivity(intent);
	}
}