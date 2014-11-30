package com.example.busroute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/*import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;*/

import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;
import android.widget.Toast;

public class MyInternet {
	
	public String getInternetData(String start,String end) throws Exception{
		
		//JSONObject json=new JSONObject();
		BufferedReader in=null;
		String data=null;
		//String jsonData=null;
		String link="https://infinite-woodland-5408.herokuapp.com?"+"start="+start+"&end="+end;
		try{
			HttpClient client= new DefaultHttpClient();
			URI website = new URI (link);
			HttpGet request = new HttpGet();
			request.setURI(website);
			HttpResponse  response= client.execute(request);
			InputStream jsonStream = response.getEntity().getContent();
			in = new BufferedReader (new InputStreamReader(jsonStream));
			StringBuffer sb = new StringBuffer("");
			String l= "";
			String nl = System.getProperty("line.separator");
			while((l=in.readLine())!=null){
				sb.append(l+nl);
			}
			//while((line=in.readLine())!=null){
			//	builder.append(line);
			//}
			
			
			in.close();
			//ArrayList<String> busroute= new ArrayList<String>();
			//HashMap<String,String> busroute= new HashMap<String,String>();
			//jsonData= builder.toString();
			//Log.i("YouJsonData", jsonData);
			//json= new JSONObject(jsonData);
			/*JSONArray array= json.getJSONArray("BusRoute");
			for(int i=0;i<array.length();i++){
				JSONObject obj= (JSONObject)array.get(i);
				String bus = obj.getString("bus");
				String route= obj.getString("route");
				busroute.put(bus, route);
				//busroute.add(bus);
				//busroute.add(route); */
			//return json;
			data=sb.toString();
			return data;
		}finally{
			if(in!= null){
				try{
					in.close();
					return data;
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		
	}

}
