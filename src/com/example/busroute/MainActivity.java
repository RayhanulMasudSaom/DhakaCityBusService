package com.example.busroute;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

//import android.R;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	String urlstr="https://infinite-woodland-5408.herokuapp.com";
	TextView tv2;
	EditText start,end;
	Separation sep;
	String data;
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv2=(TextView) findViewById(R.id.tv2);
		start=(EditText) findViewById(R.id.start);
		end=(EditText) findViewById(R.id.end);
		//sep=new Separation();
		//int l= sep.StringDivide();
		//System.out.println(l);
	    //tv2.setText(sep.StringDivide());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*public void getData(View v){
		String source= start.getText().toString();
		String des= end.getText().toString();
		String returned="";
		//tv2.setText(returned);
		/*try{
			MyInternet  internet =new MyInternet();
		    returned =internet.getInternetData(source,des);
			//returned="masud";
			tv2.setText(returned);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		ConnectivityManager connMgr = (ConnectivityManager) 
	            getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	        if (networkInfo != null && networkInfo.isConnected()) {
	            //new DownloadWebpageTask().execute(stringUrl);
	        	try{
	    			MyInternet  internet =new MyInternet();
	    		    returned =internet.getInternetData(source,des);
	    			//returned="masud";
	    			tv2.setText(returned);
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}
	        } else {
	            tv2.setText("No network connection available.");
	        }
		//sep= new Separation();
		//String newStr= sep.StringDivide();
		//tv2.setText(newStr);
		/*JSONArray array= json.getJSONArray("BusRoute");
		for(int i=0;i<array.length();i++){
			JSONObject obj= (JSONObject)array.get(i);
			String bus = obj.getString("bus");
			String route= obj.getString("route");
			//busroute.put(bus, route);
			
		}*/
		//String source= start.getText().toString();
		//String des= end.getText().toString();
		/*MyInternet  internet =new MyInternet();
		JSONObject obj= (JSONObject)internet.getInternetData(source,des);
		String data= obj.toString();
		tv2.setText("");
		System.out.println(data);
		//Internet internet= new Internet();
		//internet.fetchJSON(source, des);
	}*/
	
	
	public void getData(View v){
		//String stringUrl = "infinite-woodland-5408.herokuapp.com/?"+"start=farmgate"+"&end=shahbagh";
		String stringUrl="http://infinite-woodland-5408.herokuapp.com/?start=farmgate&end=shahbagh";
        ConnectivityManager connMgr = (ConnectivityManager) 
            getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask2().execute(stringUrl);
        } else {
            tv2.setText("No network connection available.");
        }
	}
	public void search(View v){
		
		Intent  intent = new Intent(this,RouteList.class);
		//intent.putExtra("id", html);
		startActivity(intent);
	}
	
	private class DownloadWebpageTask2 extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			
			try {
				//tv2.setText(urls[0]);
				data=urls[0];
	            return downloadUrl(urls[0]);
	        } catch (IOException e) {
	            return data;
	        }
			//return null;
		}
		
		 @Override
	     protected void onPostExecute(String result) {
			 progressDialog.dismiss();
			 tv2.setText(result);
	         
	    }

	}
	
	private String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	    // Only display the first 500 characters of the retrieved
	    // web page content.
	    int len = 500;
	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        //Log.d(DEBUG_TAG, "The response is: " + response);
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = readIt(is, len);
	        return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}


}

