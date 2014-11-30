package com.example.tabpracticefinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

public class FetchData extends AsyncTask<String,Void,String>{
	
    //private ProgressDialog progressDialog;
    private String start,end,data=null;
    private String link;
    private String returnedData=null;
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog = ProgressDialog.show(null, "Downloading...", null);
    }
    
    protected String doInBackground(String ...params) {
		// TODO Auto-generated method stub
    	
		try {
			start=params[0];
			end= params[1];
			//link=params[2];
			data=doInBackgroundAssociateMethod(start,end);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
         //progressDialog.dismiss();

    }

    protected String doInBackgroundAssociateMethod(String starting, String ending) throws Exception{
    	BufferedReader in=null;
		//String data=null;
    	//String returnedData=null;
		String link="https://infinite-woodland-5408.herokuapp.com?"+"start="+starting+"&end="+ending;
		try{
			HttpClient client= new DefaultHttpClient();
			URI website = new URI (link);
			HttpGet request = new HttpGet();
			request.setURI(website);
			HttpResponse  response= client.execute(request);
			InputStream inputStream = response.getEntity().getContent();
			in = new BufferedReader (new InputStreamReader(inputStream));
			StringBuffer sb = new StringBuffer("");
			String l= "";
			
			String nl = System.getProperty("line.separator");
			while((l=in.readLine())!=null){
				sb.append(l+nl);
			}
			in.close();
			
			returnedData=sb.toString();
			return returnedData;
		}finally{
			if(in!= null){
				try{
					in.close();
					return returnedData;
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
    }
    
	public String gettingData(){
		return returnedData;
		
	}
}
