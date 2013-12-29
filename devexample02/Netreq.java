/*
 * Netreq.java
 */

package netreq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author guest
 */
public class Netreq {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Netreq nrq = new Netreq();
        String s = nrq.getData();
        System.out.printf("%s\n",s);
    }
    
    private String getData() {
        String site2 = "trello";
/* https://trello.com/c/LZ0GjL36/27-making-requests-to-the-api-i-e-api-trello-com */
        String card3 = "LZ0GjL36";
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
                    retsb.append("\n");
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

