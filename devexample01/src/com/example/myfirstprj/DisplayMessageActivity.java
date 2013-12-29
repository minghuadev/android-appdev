package com.example.myfirstprj;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.method.SingleLineTransformationMethod;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class DisplayMessageActivity extends Activity {
	
	private TextView _txtview = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
	    // Get the message from the intent
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

	    // Create the text view
	    if ( _txtview == null ) {
	        //_txtview = new TextView(this);
	    	_txtview = (TextView) findViewById(R.id.display_message);
	        _txtview.setTextSize(14);
	        _txtview.setHorizontalScrollBarEnabled(true);
	        _txtview.setVerticalScrollBarEnabled(true);
	        
	        //_txtview.setTransformationMethod(
	        //					SingleLineTransformationMethod.getInstance());
	        //this method does not work. instead set it in xml:
	        //      android:layout_width="fill_parent"
	        //      android:singleLine="true"
	        
	    }
	    _txtview.setText(message);

	    // Set the text view as the activity layout
	    //setContentView(_txtview);
		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_message, menu);
        return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
        case R.id.action_search:
            //openSearch();
        	if ( _txtview != null ) {
        		_txtview.setText( _txtview.getText() + " search ");
        	}
            return true;
        case R.id.action_settings:
            //openSettings();
        	if ( _txtview != null ) {
        		_txtview.setText( _txtview.getText() + "\n");
        	}
            return true;
        default:
        	break;
		}
		return super.onOptionsItemSelected(item);
	}

}
