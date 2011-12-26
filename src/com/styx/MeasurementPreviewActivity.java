package com.styx;

import android.app.Activity;
import android.os.Bundle;

public class MeasurementPreviewActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
//        MeasurementPreview detail = new MeasurementPreview(this, extras.<Room>getParcelable("room"));
//        setContentView(detail);

//        TextView textview = new TextView(this);
//        textview.setText("This is the Map detail tab");
//        setContentView(textview);
//        setContentView(R.layout.mapdetail);
    }
}