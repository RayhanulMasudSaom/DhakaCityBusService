package com.example.busroute;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.widget.TextView;

public class Internet {
	
	public void fetchJSON(String start,String end){
		  final String urlString="https://infinite-woodland-5408.herokuapp.com?"+"start="+start+"&end="+end;
	      //String data="mydata";
		  Thread thread = new Thread(new Runnable(){
	         @Override
	         public void run() {
	         try {
	            URL url = new URL(urlString);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setReadTimeout(10000 /* milliseconds */);
	            conn.setConnectTimeout(15000 /* milliseconds */);
	            conn.setRequestMethod("GET");
	            conn.setDoInput(true);
	            // Starts the query
	            conn.connect();
	            InputStream stream = conn.getInputStream();

	            String data = convertStreamToString(stream);
	            //readAndParseJSON(data);
	        	 stream.close();
	        	 System.out.println(data);

	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	}

			private void readAndParseJSON(String data) {
				// TODO Auto-generated method stub
				//TextView tv;
				//tv= (TextView) findViewById(R.id.tv2);
			}

			private String convertStreamToString(java.io.InputStream is) {
				// TODO Auto-generated method stub
				java.util.Scanner s = new java.util.Scanner(is).useDelimiter(" ");
			      return s.hasNext() ? s.next() : "";
			     
			}
	      });

	       thread.start(); 		
	   }

}
