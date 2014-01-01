package com.example.myfirstprj;

import java.io.File;
import java.io.FileInputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static String EXTRA_WRAPTEXT = "com.example.myfirstapp.WRAPTEXT";
	public final static String EXTRA_FONTSIZE = "com.example.myfirstapp.FONTSIZE";
	public final static String APP_DATA_FILE_NAME = "myappfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		String filename = APP_DATA_FILE_NAME;
		try {
    	  File file = new File(getFilesDir(), filename);
    	  if ( file.exists() ) {
    		  file.delete();
    		  TextView tv = (TextView)findViewById(R.id.main_debug_message);
    		  tv.setText("app file deleted");
    	  }
		} catch (Exception e) {
		  e.printStackTrace();
		}	        		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }    
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	
    	CheckBox chkbox = (CheckBox) findViewById(R.id.checkbox_textwrap);
    	String wrapmsg = chkbox.isChecked()?"yes":"no";
    	intent.putExtra(EXTRA_WRAPTEXT, wrapmsg);

    	RadioButton fntbtn1 = (RadioButton) findViewById(R.id.radiobutton_small);
    	RadioButton fntbtn2 = (RadioButton) findViewById(R.id.radiobutton_medium);
    	RadioButton fntbtn3 = (RadioButton) findViewById(R.id.radiobutton_large);
    	RadioButton fntbtn4 = (RadioButton) findViewById(R.id.radiobutton_xlarge);
    	String fntmsg = fntbtn1.isChecked()?"small":
    					fntbtn2.isChecked()?"medium":
    						fntbtn3.isChecked()?"large":
    						fntbtn4.isChecked()?"xlarge":"xxlarge";
    	intent.putExtra(EXTRA_FONTSIZE, fntmsg);
    	
    	startActivity(intent);
    }
    
}
