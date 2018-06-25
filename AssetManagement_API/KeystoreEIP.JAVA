
import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
//import com.microsoft.sqlserver.jdbc.SQLServerException;



/**
 * A sample program that demonstrates how:
 * to view Data from the ADP web service
 */
public class KeystoreEIP {            
	public static void main(String[] args) {
	    KeystoreEIP ks = new KeystoreEIP();
	    
	    //set all parameters
	    String pathKeystore="D:/C2L/AppServer_AXA_AMFS/jboss-as-7.1.0.CR1b/standalone/configuration/server.keystore";
	    String endpoint ="https://LAQSMD99.applications.services.axa-tech.intraxa:8400/rest/id/invoke?biz=DataManagementR-id-v1";
	    String entity="";
        //only to get different messageID
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyhmmssa");
        String formattedDate = sdf.format(date);
        
	    try {
	    	
	    	
	    	final KeyManagerFactory kmf = ks.getKeyManagerFactory(pathKeystore);
	        SSLContext ctx = SSLContext.getInstance("SSL");
	        ctx.init(kmf.getKeyManagers(), trustAllCerts, new java.security.SecureRandom());  
	
	        SSLSocketFactory sslFactory = ctx.getSocketFactory();
	        HttpsURLConnection.setDefaultSSLSocketFactory(sslFactory);
	    
	        //ensure hit the correct endpoint
	        URL url = new URL(endpoint);
	        HttpsURLConnection conn = ks.getURLConnection(formattedDate, entity, url);
	        
	        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
	        writer.write(ks.getPayload());
	        writer.close();
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {jsonString.append(line);}
	        br.close();
	        conn.disconnect();
	        //write to file
	        System.out.println(jsonString.toString());
			//ks.printAll(jsonString.toString(),ks);
			
		    
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch(IOException ioe){
	        ioe.printStackTrace();
	    } catch(Exception e){
	         e.printStackTrace();
	    } 

    
    }
	private void printAll(String message,KeystoreEIP ks) throws Exception{
		String test = message;
		test = (test.substring(test.indexOf("[")+1,test.indexOf("]")).replaceAll("\"", "").trim());
	    StringTokenizer st = new StringTokenizer(test,",");
	    
	    while(st.hasMoreTokens()){
	    	System.out.println("|"+st.nextToken().toString().trim()+"|");
	    }
	}
	private KeyManagerFactory getKeyManagerFactory(String path) throws Exception{
        KeyStore ks = KeyStore.getInstance("JKS");
        
        java.io.FileInputStream fis = null;
        try {
            fis = new java.io.FileInputStream(path);
            ks.load(fis, "axa123".toCharArray());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        final char[] KEY_PASSWORD = "axa123".toCharArray();

        KeyManagerFactory kmf = 
            KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());

        kmf.init(ks, KEY_PASSWORD);          

        TrustManagerFactory tmf = 
            TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        
        return kmf;
	}
	static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers(){return null;}
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType){}
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType){}
            }
    };
	private HttpsURLConnection getURLConnection(String formattedDate,String entity,URL url) throws Exception{
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();    
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("content-type", "application/json");
        conn.setRequestProperty("x-axa-lob", "Health");
        conn.setRequestProperty("x-axa-contextheader-contextversion", "1.0");
        conn.setRequestProperty("accept","application/json");
        conn.setRequestProperty("x-axa-msgid","apps"+formattedDate);
        conn.setRequestProperty("x-axa-contextheader-customdata-sourcesystem","iConnect");
        conn.setRequestProperty("x-axa-initialmsgid","apps"+formattedDate);
        conn.setRequestProperty("x-axa-entity",entity);
        conn.setRequestProperty("x-axa-requesting-channel","iConnect");
        conn.setRequestProperty("x-axa-contextheader-customdata-targetsystem","NBDB");
        return conn;
	}
	private String getPayload(){
		return 
				"{\"Header\":{\"SecurityContext\":{"+
				"\"username\":\"SFDCEIP\","+
				"\"password\":\"SFDCEIP123!\"}},"+
				"\"Body\":{"+
				"\"transactionId\":\"214699C1CA\","+ 
				"\"operation\":\"CustomerSVOC\","+     
				"\"appID\":\"UW\","+
				"\"entity\":\"AFI\","+
				"\"service\":\"getUWData\","+     
				"\"IcAx_AXAAsiaCustomer_Type\":{"+
          "\"canBeIndividual\":{"+
              "\"genderCD\":\"M\","+
	      "\"fullNM\":\"Mohammad Yamin\","+
              "\"birthDT\":\"19720313\""+
           "}}}}";
	}


}