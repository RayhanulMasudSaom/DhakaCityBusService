package com.example.busroute;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	String urlstr="https://infinite-woodland-5408.herokuapp.com";
	TextView tv2;
	EditText start,end;
	Separation sep;
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv2=(TextView) findViewById(R.id.tv2);
		start=(EditText) findViewById(R.id.start);
		end=(EditText) findViewById(R.id.end);
		sep=new Separation();
		//int l= sep.StringDivide();
		//System.out.println(l);
	    tv2.setText(sep.StringDivide());
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
	
	public void getData(View v) throws Exception{
		String source= start.getText().toString();
		String des= end.getText().toString();
		MyInternet  internet =new MyInternet();
		String returned =internet.getInternetData(source,des);
		//tv2.setText(returned);
		sep= new Separation();
		String newStr= sep.StringDivide();
		tv2.setText(newStr);
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
		System.out.println(data);*/
		//Internet internet= new Internet();
		//internet.fetchJSON(source, des);
	}
	public void search(View v){
		
		Intent  intent = new Intent(this,RouteList.class);
		//intent.putExtra("id", html);
		startActivity(intent);
	}
}
