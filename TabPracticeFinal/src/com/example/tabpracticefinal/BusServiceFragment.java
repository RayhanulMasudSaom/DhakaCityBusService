package com.example.tabpracticefinal;




import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BusServiceFragment extends Fragment implements OnClickListener,OnItemClickListener {

	private ProgressDialog progressDialog;
	String returnedDataFromServer=null;
	String startLoc,endLoc;
	int selectedItem=-1;
	int state=1;
	View view;
	Button btnInter,btnIntra,btnSearch;
	TextView txtView1,txtView2;
	AutoCompleteTextView auto1,auto2;
	String []strLocationFrom= { "mirpur-12", "mirpur-11", "mirpur-10", "kajipara","sheowrapara","farmgate",
			"shahbagh","pressclub","stadium","ittefaq","jonopoth","pallabi","mirpur-11.5","boikalihotel",
			"t&t","rayshahebbazar","victoriapark","sadarghat","taltola","paltan","shaplachottor",
			"sayedabad","agargaon","newmarket","etimkhana","golchokkor","mirpur-1","nilkhet",
			"ajimpur","polashi","katabon","jattrabari"
	};
	String []strLocationTo= { "mirpur-12", "mirpur-11", "mirpur-10", "kajipara","sheowrapara","farmgate",
			"shahbagh","pressclub","stadium","ittefaq","jonopoth","pallabi","mirpur-11.5","boikalihotel",
			"t&t","rayshahebbazar","victoriapark","sadarghat","taltola","paltan","shaplachottor",
			"sayedabad","agargaon","newmarket","etimkhana","golchokkor","mirpur-1","nilkhet",
			"ajimpur","polashi","katabon","jattrabari"
	};
	ViewPager pager;
	LayoutInflater infla;
	ArrayAdapter<String> adapter1,adapter2;
	private ConnectivityManager connectivityManager;
	
	public BusServiceFragment(ConnectivityManager connectivityManager){
		this.connectivityManager=connectivityManager;
	}
	
	
	public View onCreateView(final LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view= inflater.inflate(R.layout.bus_service_layout, container, false);
		btnInter= (Button) view.findViewById(R.id.InterBusService);
		btnIntra= (Button) view.findViewById(R.id.IntraBusService);
		btnSearch=(Button) view.findViewById(R.id.btnSearch);
		txtView1= (TextView) view.findViewById(R.id.textView1);
		txtView2=(TextView) view.findViewById(R.id.textView2);
		auto1= (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView1);
		auto2=(AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView2);
		btnInter.setOnClickListener(this);
		btnIntra.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		auto1.setOnItemClickListener(this);
		auto2.setOnItemClickListener(this);
		pager= (ViewPager) getActivity().findViewById(R.id.ViewPager);
		state=1;
		adapter2= new ArrayAdapter<String>(this.getActivity().getApplicationContext(),R.layout.auto_complete_text_view_show_layout,R.id.autoTextView,strLocationTo);
		adapter1= new ArrayAdapter<String>(this.getActivity().getApplicationContext(),R.layout.auto_complete_text_view_show_layout,R.id.autoTextView,strLocationFrom);
		auto1.setAdapter(adapter1);
		auto1.setThreshold(2);
		auto2.setAdapter(adapter2);
		auto2.setThreshold(2);
		
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(btnInter.isPressed()==true){
			btnInter.setVisibility(v.GONE);
			btnIntra.setVisibility(v.GONE);
			txtView1.setVisibility(v.VISIBLE);
			txtView2.setVisibility(v.VISIBLE);
			auto1.setVisibility(v.VISIBLE);
			auto2.setVisibility(v.VISIBLE);
			btnSearch.setVisibility(v.VISIBLE);
			state=2;
		}
		else if(btnIntra.isPressed()==true){
			Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
		}
		
		else if(btnSearch.isPressed()==true){
			//Toast.makeText(getActivity(), startLoc+endLoc, Toast.LENGTH_SHORT).show();
			//returnedStringFromServer=getDataFromServer();
			//Toast.makeText(getActivity(), returnedStringFromServer, Toast.LENGTH_LONG).show();
			String stringUrl="http://infinite-woodland-5408.herokuapp.com/?start="+startLoc+"&end="+endLoc;
	        //ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	        
	        if (networkInfo != null && networkInfo.isConnected()) {
	            //new DownloadWebpageTask2().execute(stringUrl);
	        	new getBusNameFromServerAsync().execute(stringUrl);
	        	//Toast.makeText(getActivity(), returnedDataFromServer, Toast.LENGTH_SHORT).show();
	        } else {
	            //tv2.setText("No network connection available.");
	        	Toast.makeText(getActivity(), "No internet connection available", Toast.LENGTH_SHORT).show();
	            
	        }
		}
		
		
		
	}
	
	
	public void getBackButtonResponse(int flag){
			
			if(state==2){
				btnInter.setVisibility(view.VISIBLE);
				btnIntra.setVisibility(view.VISIBLE);
				txtView1.setVisibility(view.GONE);
				txtView2.setVisibility(view.GONE);
				auto1.setVisibility(view.GONE);
				auto2.setVisibility(view.GONE);
				btnSearch.setVisibility(view.GONE);
				state=1;
			}
			else if(state==1){
				getActivity().finish();
				
			}
	}
	
	/*public String getDataFromServer(){
		//String [] params= {auto1.getText().toString(),auto2.getText().toString()};
		//String link="https://infinite-woodland-5408.herokuapp.com?"+"start="+start+"&end="+end;
		FetchData fetchData= new FetchData();
		//Toast.makeText(getActivity(), startLoc+endLoc, Toast.LENGTH_SHORT).show();
		//auto1.setOnItemClickListener(this);
		fetchData.execute(startLoc,endLoc);
		
		//fetchData.doInBackground(startLoc,endLoc);
		String ret= fetchData.gettingData();
		return ret;
	}*/


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
			if(arg0.getAdapter()==adapter1)
				startLoc=(String) arg0.getItemAtPosition(arg2);
			//auto1.
			else
				endLoc=(String) arg0.getItemAtPosition(arg2);
	}
	
	private class getBusNameFromServerAsync extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
	        super.onPreExecute();
	        progressDialog = ProgressDialog.show(getActivity(), "Please Wait.....","Loading");
	    }
		
		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			
			try {
				//tv2.setText(urls[0]);
				//data=urls[0];
	            return downloadBusList(urls[0]);
	        } catch (IOException e) {
	            return null;
	        }
			//return null;
		}
		
		 @Override
	     protected void onPostExecute(String result) {
	         Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
	         progressDialog.dismiss();
	    }

	}
	
	private String downloadBusList(String myurl) throws IOException {
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
