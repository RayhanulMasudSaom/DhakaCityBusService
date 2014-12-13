package com.example.tabpracticefinal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BloodHelpFragment extends Fragment implements OnClickListener,OnItemClickListener{

	private int state=1;
	private TextView txtViewBadhonZones,txtViewAvailableAmbulance;
	private ListView listViewBadhonZones,listAmbulanceView;
	private Button btnBadhonZones,btnAmbulances;
	private ProgressDialog progressDialog;
	private String returnedDataFromServer;
	private View view;
	
	
	private ArrayAdapter<String> adapterBadhonZones;
	private ArrayAdapter<String> adapterAmbulanceList,adapterAmbulanceNumberList;
	
	private ArrayList<AmbulanceInfo> ambulanceInfo;
	private ArrayList<BloodZoneInfo> zoneInfo;
	
	private String [] strBadhonZones={ "AnondoMohon College(Mymensingh)",
			"Bangladesh University of Engineering Technology (BUET)",
			"Bangladesh Agricultural University","Begum Rokeya University",
			"Bogura Govt.Azizul Haque College","BrojoMohon College(Barisal)",
			"Dhaka College(DC)","Dhaka University(DU)",
			"Jahangirnagar University(JU)","Jogonnath University",
			"Khulna University","Rajshahi University(RU)",
			"Rangpur Govt. College","Sher-e-Bangla Agricultural University","Sylthet Agricultural University",		
	};
	
	
	private ConnectivityManager connectivityManager;
	
	public BloodHelpFragment(ConnectivityManager connectivityManager){
		this.connectivityManager=connectivityManager;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.blood_help_layout, container, false);
		txtViewBadhonZones= (TextView) view.findViewById(R.id.txtViewBadhonZones);
		txtViewAvailableAmbulance=(TextView) view.findViewById(R.id.txtViewAvailableAmbulance);
		
		listViewBadhonZones= (ListView) view.findViewById(R.id.listViewBadhonZones);
		listAmbulanceView=(ListView) view.findViewById(R.id.listAmbulanceView);
		
		btnBadhonZones= (Button) view.findViewById(R.id.btnBadhonZones);
		btnAmbulances= (Button) view.findViewById(R.id.btnAmbulances);
		
		btnBadhonZones.setOnClickListener(this);
		btnAmbulances.setOnClickListener(this);
		
		return view;
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		/*if(btnBadhonZones.isPressed()){
			btnBadhonZones.setVisibility(view.GONE);
			btnAmbulances.setVisibility(view.GONE);
			listViewBadhonZones.setVisibility(view.VISIBLE);
			txtViewBadhonZones.setVisibility(view.VISIBLE);
			adapterBadhonZones= new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,
					R.id.txtBusName,strBadhonZones);
			listViewBadhonZones.setAdapter(adapterBadhonZones);
			state=2;
			listViewBadhonZones.setOnItemClickListener(this);
		}*/
		
		if(btnBadhonZones.isPressed()){
			//checking the Internet Connection Availability
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	        
	        if (networkInfo != null && networkInfo.isConnected()) {
	        	new getBadhonDataFromServerAsync().execute("http://infinite-woodland-5408.herokuapp.com/badhon");
	        	
	        } else {
	        	Toast.makeText(getActivity(), "No internet connection available", Toast.LENGTH_SHORT).show();
	            
	        }
			
		}
		
		else if(btnAmbulances.isPressed()){
			//checking the Internet Connection Availability
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	        
	        if (networkInfo != null && networkInfo.isConnected()) {
	        	new getAmbulanceDataFromServerAsync().execute("http://infinite-woodland-5408.herokuapp.com/ambulance");
	        	
	        } else {
	        	Toast.makeText(getActivity(), "No internet connection available", Toast.LENGTH_SHORT).show();
	            
	        }
		}
	}
	
	public void getBackButtonResponse(int flag){
		
		if(state==2){
			btnBadhonZones.setVisibility(view.VISIBLE);
			btnAmbulances.setVisibility(view.VISIBLE);
			listViewBadhonZones.setVisibility(view.GONE);
			txtViewBadhonZones.setVisibility(view.GONE);
			state=1;
		}
		
		else if(state==1){
			getActivity().finish();
		}
		
		else if(state==4){
			state=1;
			btnBadhonZones.setVisibility(view.VISIBLE);
			btnAmbulances.setVisibility(view.VISIBLE);
			listAmbulanceView.setVisibility(view.GONE);
			txtViewAvailableAmbulance.setVisibility(view.GONE);
		}
		else if(state==5){
			state=4;
			showAmbulanceName(ambulanceInfo);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), strBadhonZones[index], Toast.LENGTH_LONG).show();
		if(arg0.getAdapter()==adapterBadhonZones){
			
		}
		else if(arg0.getAdapter()==adapterAmbulanceList){
			showNumberOfTheAmbulance(index);
		}
		else if(arg0.getAdapter()==adapterBadhonZones){
			showContactsOfTheZone(index);
		}
		
	}
	
	
	private void showContactsOfTheZone(int index) {
		// TODO Auto-generated method stub
		
	}


	private class getAmbulanceDataFromServerAsync extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
	        super.onPreExecute();
	        progressDialog = ProgressDialog.show(getActivity(), "Please Wait.....","Loading");
	    }
		
		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			
			try {
	            return downloadBusList(urls[0]);
	        } catch (IOException e) {
	            return null;
	        }
		}
		
		 @Override
	     protected void onPostExecute(String result) {
			 progressDialog.dismiss();
			 
			 if(result.contains("erro")){
				 Toast.makeText(getActivity(), "Server Busy", Toast.LENGTH_SHORT).show();
			 }
			 else if (result !=null){
				 
				 //Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
				 returnedDataFromServer=result;
				 AmbulanceNameNumberSeparator separator= new AmbulanceNameNumberSeparator(returnedDataFromServer);
				 
		         ambulanceInfo= new ArrayList<AmbulanceInfo>();
				 ambulanceInfo=separator.separation();
				 showAmbulanceName(ambulanceInfo);
			 }
			 else if(result==null){
				 Toast.makeText(getActivity(), "Server Problem", Toast.LENGTH_SHORT).show();
			 }
	    }

	}
	
	//the connection making method with the server
	
	private String downloadBusList(String myurl) throws IOException{
	    InputStream is = null;
	    int len = 500;
	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds*/ );
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
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
	
	//getting the strings  from inputstream buffer from the returned data of the server
	private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}
	
	public void showAmbulanceName(ArrayList<AmbulanceInfo> ambulance){
		
		String [] ambulanceNames;
		
		//making invisible parts
		btnBadhonZones.setVisibility(View.GONE);
		btnAmbulances.setVisibility(View.GONE);
		listViewBadhonZones.setVisibility(view.GONE);
		txtViewBadhonZones.setVisibility(view.GONE);
		
		//making visible parts
		txtViewAvailableAmbulance.setText("Available Ambulance");
		txtViewAvailableAmbulance.setVisibility(View.VISIBLE);
		listAmbulanceView.setVisibility(View.VISIBLE);
		
		//the state of the tab fragment
		state=4;
		
		ambulanceNames=new String[ambulance.size()];
		for(int i=0;i<ambulance.size();i++){
			ambulanceNames[i]=ambulance.get(i).ambulanceName;
		}
		
		adapterAmbulanceList =new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,ambulanceNames);
		listAmbulanceView.setAdapter(adapterAmbulanceList);
		//Toast.makeText(getActivity(), bus.size(), Toast.LENGTH_SHORT);
		listAmbulanceView.setOnItemClickListener(this);
		
	}
	
	public void showNumberOfTheAmbulance(int selectedAmbulanceIndex){
		
		state=5;
        
		txtViewAvailableAmbulance.setText("Contact Numbers");
		
		ArrayList<String> numbersArrayList= new ArrayList<String>();
		numbersArrayList=ambulanceInfo.get(selectedAmbulanceIndex).number;
		
		//getting the stopage names across the specific bus paribahan
		String[] numbers=new String[numbersArrayList.size()];
		for(int i=0;i<numbersArrayList.size();i++){
			numbers[i]=numbersArrayList.get(i);
		}
		
		adapterAmbulanceNumberList =new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,numbers);
		listAmbulanceView.setAdapter(adapterAmbulanceNumberList);
	}
	
	private class getBadhonDataFromServerAsync extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
	        super.onPreExecute();
	        progressDialog = ProgressDialog.show(getActivity(), "Please Wait.....","Loading");
	    }
		
		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			
			try {
	            return downloadBusList(urls[0]);
	        } catch (IOException e) {
	            return null;
	        }
		}
		
		 @Override
	     protected void onPostExecute(String result) {
			 progressDialog.dismiss();
			 
			 if(result.contains("erro")){
				 Toast.makeText(getActivity(), "Server Busy", Toast.LENGTH_SHORT).show();
			 }
			 else if (result !=null){
				 
				 //Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
				 returnedDataFromServer=result;
				 ZoneNameNumberSeparator separator= new ZoneNameNumberSeparator(returnedDataFromServer);
				 
		         zoneInfo= new ArrayList<BloodZoneInfo>();
				 zoneInfo=separator.separation();
				 showZoneName(zoneInfo);
			 }
			 else if(result==null){
				 Toast.makeText(getActivity(), "Server Problem", Toast.LENGTH_SHORT).show();
			 }
	    }

	}
	
	public void showZoneName(ArrayList<BloodZoneInfo> zone){
		
		String [] zoneNames;
		
		//making invisible parts
		btnBadhonZones.setVisibility(View.GONE);
		btnAmbulances.setVisibility(View.GONE);
		listAmbulanceView.setVisibility(View.GONE);
		txtViewAvailableAmbulance.setVisibility(View.GONE);
		
		//making visible parts
		txtViewBadhonZones.setText("Available Ambulance");
		txtViewAvailableAmbulance.setVisibility(View.VISIBLE);
		listViewBadhonZones.setVisibility(View.VISIBLE);
		
		//the state of the tab fragment
		state=2;
		
		zoneNames=new String[zone.size()];
		for(int i=0;i<zone.size();i++){
			zoneNames[i]=zone.get(i).zoneName;
		}
		
		adapterBadhonZones =new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,zoneNames);
		listViewBadhonZones.setAdapter(adapterAmbulanceList);
		//Toast.makeText(getActivity(), bus.size(), Toast.LENGTH_SHORT);
		listViewBadhonZones.setOnItemClickListener(this);
	}
	

}
