package com.styx;

import com.styx.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FavMapActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        textview.setText("This is the Fav Map tab");
        setContentView(textview);
        //setContentView(R.layout.favmap);
    }
}