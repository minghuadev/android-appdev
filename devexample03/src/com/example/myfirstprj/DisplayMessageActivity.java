package com.example.myfirstprj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.method.ScrollingMovementMethod;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class DisplayMessageActivity extends Activity {
	
	static final private String passcode = "password";
	static final private String cardcode = "LZ0GjL36";
	
	private TextView _txtview = null;
	private boolean  _allownet = false;
	private String   _setmsg  = null;
	private String   _txtwrap = null;
	private String   _fntsz   = null;
			

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Allow network blocking access... change api min from 8 to 9
		  StrictMode.ThreadPolicy policy = new StrictMode.
				  ThreadPolicy.Builder().permitAll().build();
				  StrictMode.setThreadPolicy(policy);
		
	    // Get the message from the intent
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	    _setmsg = message;

	    _txtwrap = intent.getStringExtra(MainActivity.EXTRA_WRAPTEXT);
	    						/* yes or no */
	    _fntsz = intent.getStringExtra(MainActivity.EXTRA_FONTSIZE);
	    						/* small, medium, large */
	    
	    boolean usexml = false;
	    if ( ! _txtwrap.equalsIgnoreCase("yes") ) {
	    	usexml = true;
		    setContentView(R.layout.activity_display_message);
	    }
	    
	    // Create the text view
	    if ( _txtview == null ) {
	    	if ( usexml ) {
	    	    _txtview = (TextView) findViewById(R.id.display_message);
	    	} else {
	            _txtview = new TextView(this);
	    	    // Set the text view as the activity layout
	    	    setContentView(_txtview);
	    	}
	        
	    }
        _txtview.setHorizontalScrollBarEnabled(true);
        _txtview.setVerticalScrollBarEnabled(true);
        _txtview.setMovementMethod(new ScrollingMovementMethod());

        if ( _fntsz.equalsIgnoreCase("small") ) {
            _txtview.setTextSize(12);
        } else if ( _fntsz.equalsIgnoreCase("medium") ) {
            _txtview.setTextSize(14);
        } else if ( _fntsz.equalsIgnoreCase("large") ) {
            _txtview.setTextSize(16);
        } else if ( _fntsz.equalsIgnoreCase("xlarge") ) {
            _txtview.setTextSize(20);
        } else {
            _txtview.setTextSize(24);
        }
        
	    _txtview.setText(message + " " + _txtwrap + " " + _fntsz);
	    if ( message.contains(passcode) ) {
	    	_allownet = true;
	    }

		
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
        	    String s1 = " search ";
        	    if ( _setmsg != null ) s1 = _setmsg + s1;
        		if ( _allownet ) {
            		_txtview.setText( s1);
	        		Netreq nrq = new Netreq();
	        		String result = nrq.getData();
	        		_txtview.setText( s1 + "\n\n" + result + "\n");
        		} else {
	        		_txtview.setText( _txtview.getText() + "\n\n" + 
	        							"No net access allowed" + "\n");
        		}
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
	
	
	public class Netreq {

	    /**
	     * @param args the command line arguments
	     */
	    public void main(String[] args) {
	        Netreq nrq = new Netreq();
	        String s = nrq.getData();
	        System.out.printf("%s\n",s);
	    }
	    
	    private String getData() {
	        String site2 = "trello";
	/* https://trello.com/c/LZ0GjL36/27-making-requests-to-the-api-i-e-api-trello-com */
	        String card3 = cardcode;
	        String site = "https://api." + site2 + ".com/1/cards/" + card3 + "/desc";
	        String rs = requestLine(site);
	        String s1 = "{\"_value\":\"";
	        String s2 = "\"}";
	        StringBuilder retsb = new StringBuilder();
	        if ( rs.toLowerCase().startsWith("error:") ) {
	            retsb.append(rs);
	        } else if (rs.startsWith(s1)  && rs.endsWith(s2)) {
	            String body2 = rs.substring( s1.length() );
	            String body = body2.substring(0, body2.length()-s2.length());
	            int len = body.length();
	            for (int i=0; i<len; i++) {
	                char c = body.charAt(i);
	                if ( c == '\\' && i < len-1 && body.charAt(i+1) == 'n' ) {
	                    retsb.append("\n\n");
	                    i++;
	                } else {
	                    retsb.append(c);
	                }
	            }
	        } else {
	            retsb.append("Error: Unknown string format: ").append(rs);
	        }
	        return retsb.toString();
	    }
	    
	    private String requestLine(String site) {
	        String dbgstr = null;
	        String retstr = "";
	        try {
	            URL url = new URL(site);
	            HttpURLConnection con = (HttpURLConnection) url.openConnection();
	            retstr = readStreamLines(con.getInputStream());
	        } catch (Exception e) {
	            e.printStackTrace();
	            dbgstr = "Error: url connection or stream exception\n";
	        }
	        if (dbgstr == null) {
	            return retstr;
	        } else {
	            return dbgstr;
	        }
	    }

	    private String readStreamLines(InputStream in) {
	        String dbgstr = null;
	        StringBuilder retsb = new StringBuilder();
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new InputStreamReader(in));
	            String line = "";
	            while ((line = reader.readLine()) != null) {
	                retsb.append(line); //System.out.println(line);
	            }
	            /* {"_value":"   7157  3 3    \n   7158  0 0"} */

	        } catch (IOException e) {
	            e.printStackTrace();
	            dbgstr = "Error: reader readline exception\n";
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    if ( dbgstr != null ) {
	                        dbgstr += "Error: reader close exception\n";
	                    } else {
	                        dbgstr = "Error: reader close exception\n";
	                    }
	                }
	            }
	        }
	        if ( dbgstr == null ) {
	            return retsb.toString();
	        } else {
	            return dbgstr;
	        }
	    } 
	}

}
