package com.example.busroute;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class MyInternet {
	
	public String getInternetData(String start,String end) throws Exception{
		BufferedReader in=null;
		String data=null;
		String link="https://infinite-woodland-5408.herokuapp.com?"+"start="+start+"&end="+end;
		try{
			HttpClient client= new DefaultHttpClient();
			URI website = new URI (link);
			HttpGet request = new HttpGet();
			request.setURI(website);
			HttpResponse  response= client.execute(request);
			in = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String l= "";
			String nl = System.getProperty("line.separator");
			while((l=in.readLine())!=null){
				sb.append(l+nl);
			}
			in.close();
			data= sb.toString();
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
