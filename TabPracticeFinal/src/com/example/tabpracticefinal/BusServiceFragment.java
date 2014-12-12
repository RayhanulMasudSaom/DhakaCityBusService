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
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BusServiceFragment extends Fragment implements OnClickListener,OnItemClickListener {

	private DifferentBusRoutesSeparatorClass separator;
	private ArrayList<BusNameWithStopagesClass> busRoute;
	private ProgressDialog progressDialog;
	private String returnedDataFromServer=null,districtName=null;
	private String startLoc,endLoc,webCounterName=null;
	
	int selectedItem=-1;
	int state=1;
	private View view;
	private Button btnInter,btnIntra,btnSearch;
	private TextView txtViewFrom,txtViewTo,txtViewAvailableBus,txtViewDistrict,
			txtViewCounter,txtViewNoCounter;
	private AutoCompleteTextView auto1,auto2;
	private ListView busList,listViewDistrict,listViewCounter;
	
	
	private String []strLocationFrom= { "mirpur-12", "mirpur-11", "mirpur-10", "kajipara","sheowrapara","farmgate",
			"shahbagh","pressclub","stadium","ittefaq","jonopoth","pallabi","mirpur-11.5","boikalihotel",
			"t&t","rayshahebbazar","victoriapark","sadarghat","taltola","paltan","shaplachottor",
			"sayedabad","agargaon","newmarket","etimkhana","golchokkor","mirpur-1","nilkhet",
			"ajimpur","polashi","katabon","jattrabari"
	};
	private String []strLocationTo= { "mirpur-12", "mirpur-11", "mirpur-10", "kajipara","sheowrapara","farmgate",
			"shahbagh","pressclub","stadium","ittefaq","jonopoth","pallabi","mirpur-11.5","boikalihotel",
			"t&t","rayshahebbazar","victoriapark","sadarghat","taltola","paltan","shaplachottor",
			"sayedabad","agargaon","newmarket","etimkhana","golchokkor","mirpur-1","nilkhet",
			"ajimpur","polashi","katabon","jattrabari"
	};
	
	private String []strDistrict= {"Akkelpur","Barisal","Bogra","Bonpara","Boroghoria",
			"Chapainababganj","Chhatak","Chittagong","Cox's Bazar",
		    "Dinajpur","Godagari","Hili","Jaipurhat","Kachikata","Kansat","Kesorhut","Khepupara",
		    "Moulavibazar","Natore","Patuakhali","Pirganj","Puthia",
		    "Rahanpur","Rajabari","Rajshahi","Rangpur",	"Rupatoli","Shaistaganj","Shibganj","Sirajganj",
		    "Sunamganj","Tangail","Sylhet"};
	
	private String national="Chapainababganj Natore Puthia Rajshahi Sirajganj Tangail Kansat Godagari Bonpara "+
		    "Kachikata Rajabari Boroghoria Rahanpur Kesorhut";
	
	private String desh="Chapainababganj Chittagong Cox's Bazar Godagari Kansat Natore Rajshahi Sirajganj Tangail"+
		    "Kansat Puthia Shibganj Rajabari";
	
	private String shyamoli= "Sylhet Bogra Chapainababganj Dinajpur Jaipurhat Moulavibazar Natore Rajshahi Rangpur Sunamganj"+
		    "Sirajganj Chhatak Kansat Pirganj Shaistaganj Rahanpur Hili Akkelpur";
	
	private String sakura="Rupatoli Barisal Patuakhali Khepupara";
	
	
	private ViewPager pager;
	private LayoutInflater infla;
	private ArrayAdapter<String> adapterFrom,adapterTo,adapterDistrictList,
			adapterBusList,adapterStopageList,adapterCounterList;
	private ConnectivityManager connectivityManager;
	
	public BusServiceFragment(ConnectivityManager connectivityManager){
		this.connectivityManager=connectivityManager;
	}
	
	
	public View onCreateView(final LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view= inflater.inflate(R.layout.bus_service_layout, container, false);
		
		//all buttons 
		btnInter= (Button) view.findViewById(R.id.InterBusService);
		btnIntra= (Button) view.findViewById(R.id.IntraBusService);
		btnSearch=(Button) view.findViewById(R.id.btnSearch);
		
		//all textViews
		txtViewFrom= (TextView) view.findViewById(R.id.txtViewFrom);
		txtViewTo=(TextView) view.findViewById(R.id.txtViewTo);
		txtViewCounter=(TextView) view.findViewById(R.id.txtViewCounter);
		txtViewAvailableBus=(TextView) view.findViewById(R.id.txtViewAvailableBus);
		txtViewDistrict= (TextView) view.findViewById(R.id.txtViewDistrict);
		txtViewNoCounter=(TextView) view.findViewById(R.id.txtViewNoCounter);
		
		//autoTextView
		auto1= (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView1);
		auto2=(AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView2);
		
		//listViews
		busList=(ListView) view.findViewById(R.id.listBusView);
		listViewCounter=(ListView) view.findViewById(R.id.listViewCounter);
		listViewDistrict=(ListView) view.findViewById(R.id.listViewDistrict);
		
		btnInter.setOnClickListener(this);
		btnIntra.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		auto1.setOnItemClickListener(this);
		auto2.setOnItemClickListener(this);
		//listViewDistrict.setOnItemClickListener(this);
		
		//ViewPager
		pager= (ViewPager) getActivity().findViewById(R.id.ViewPager);
		
		state=1;
		
		//Adapters
		adapterTo= new ArrayAdapter<String>(this.getActivity().getApplicationContext(),R.layout.auto_complete_text_view_show_layout,R.id.autoTextView,strLocationTo);
		adapterFrom= new ArrayAdapter<String>(this.getActivity().getApplicationContext(),R.layout.auto_complete_text_view_show_layout,R.id.autoTextView,strLocationFrom);
		auto1.setAdapter(adapterFrom);
		auto1.setThreshold(1);
		auto2.setAdapter(adapterTo);
		auto2.setThreshold(1);
		
		return view;
	}
	
	//different buttons response and their respective does
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(btnInter.isPressed()==true){
			
			btnInter.setVisibility(View.GONE);
			btnIntra.setVisibility(View.GONE);
			txtViewFrom.setVisibility(View.VISIBLE);
			txtViewTo.setVisibility(View.VISIBLE);
			auto1.setVisibility(View.VISIBLE);
			auto2.setVisibility(View.VISIBLE);
			btnSearch.setVisibility(View.VISIBLE);
			txtViewAvailableBus.setVisibility(View.GONE);
			busList.setVisibility(View.GONE);
			state=2;
			
		}
		else if(btnIntra.isPressed()==true){
			
			showDistrict();
		}
		
		else if(btnSearch.isPressed()==true){
			
			int start=0,end=0;
			
			//checking whether the given locations both are in the list
			for(int i=0;i<strLocationFrom.length;i++){
				
				if(strLocationFrom[i]==startLoc){
					start=1;
					
				}
				
				if(strLocationFrom[i]==endLoc){
				
					end=1;
				}
				
				if(start==1 && end==1){
					break;
				}
				
			}
			
			if(end==0 && start==0){
				Toast.makeText(getActivity(), "Select Locations From Given List\n", Toast.LENGTH_LONG).show();
				auto1.setText("");
				auto2.setText("");
				startLoc=null;
				endLoc=null;
				return ;
			}
			
			else if(end==start && endLoc==startLoc){
			
				startLoc=null;
				endLoc=null;
				auto1.setText("");
				auto2.setText("");
				Toast.makeText(getActivity(), "Invalid Input\nYour Current Location and Destination cannot be same", Toast.LENGTH_SHORT).show();
				return ;
			}
			
			else if(end!=1 || start!=1)
			{
				startLoc=null;
				endLoc=null;
				auto1.setText("");
				auto2.setText("");
				Toast.makeText(getActivity(), "Give location to 'Both' fields", Toast.LENGTH_SHORT).show();
				return ;
			}	
			
			
	        //ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	        
	        if (networkInfo != null && networkInfo.isConnected() && startLoc!=null && endLoc!=null) {
	        	new getBusNameFromServerAsync().execute("http://infinite-woodland-5408.herokuapp.com/?start="+startLoc+"&end="+endLoc);
	        	
	        } else {
	        	auto1.setText("");
				auto2.setText("");
	        	startLoc=null;
	        	endLoc=null;
	        	Toast.makeText(getActivity(), "No internet connection available", Toast.LENGTH_SHORT).show();
	            
	        }
		}
		
	}
	
	//when back button pressed, this will make everything to their previous state
	
	public void getBackButtonResponse(int flag){
			
			if(state==2){
				btnInter.setVisibility(View.VISIBLE);
				btnIntra.setVisibility(View.VISIBLE);
				txtViewFrom.setVisibility(View.GONE);
				txtViewTo.setVisibility(View.GONE);
				auto1.setVisibility(View.GONE);
				auto2.setVisibility(View.GONE);
				btnSearch.setVisibility(View.GONE);
				txtViewAvailableBus.setVisibility(View.GONE);
				busList.setVisibility(View.GONE);
				endLoc=null;
				startLoc=null;
				auto1.setText("");
				auto2.setText("");
				state=1;
			}
			else if(state==1){
				getActivity().finish();
				
			}
			else if(state==3){
				btnInter.setVisibility(View.GONE);
				btnIntra.setVisibility(View.GONE);
				txtViewFrom.setVisibility(View.VISIBLE);
				txtViewTo.setVisibility(View.VISIBLE);
				auto1.setVisibility(View.VISIBLE);
				auto2.setVisibility(View.VISIBLE);
				btnSearch.setVisibility(View.VISIBLE);
				txtViewAvailableBus.setVisibility(View.GONE);
				busList.setVisibility(View.GONE);
				
				state=2;
			}
			else if(state==5){
				listViewDistrict.setVisibility(View.GONE);
				txtViewDistrict.setVisibility(View.GONE);
				btnInter.setVisibility(View.VISIBLE);
				btnIntra.setVisibility(View.VISIBLE);
				state=1;
			}
			else if(state==4){
				showAvailableBusMethod(busRoute);
				state=3;
			}
			else if(state==6){
				state=5;
				//listViewDistrict.setVisibility(view.G);
				listViewCounter.setVisibility(View.GONE);
				txtViewCounter.setVisibility(View.GONE);
				txtViewNoCounter.setVisibility(View.GONE);
				showDistrict();
			}
	}
	
	
	//getting the item index or element from the different list adapters 
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub
		
			if(arg0.getAdapter()==adapterFrom)
				startLoc=(String) arg0.getItemAtPosition(index);
			
			else if(arg0.getAdapter()==adapterTo)
				endLoc=(String) arg0.getItemAtPosition(index);
			
			else if(arg0.getAdapter()==adapterDistrictList){
				districtName=(String) arg0.getItemAtPosition(index);
				showBusCounterLink();
			}
			else if(arg0.getAdapter()==adapterCounterList){
				webCounterName=(String) arg0.getItemAtPosition(index);
				goToWebsite();
			}
			else if(arg0.getAdapter()==adapterBusList){
				showRouteOfTheBus(index);
			}
			
	}
	
	//Asynchronous method for downloading the data
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
	            return downloadBusList(urls[0]);
	        } catch (IOException e) {
	            return null;
	        }
		}
		
		 @Override
	     protected void onPostExecute(String result) {
			 progressDialog.dismiss();
			 if(result==null){
				 startLoc=null;
				 endLoc=null;
				 auto1.setText("");
				 auto2.setText("");
				 Toast.makeText(getActivity(), "No available Bus Found", Toast.LENGTH_SHORT).show();
			 }
			 else if(result.contains("erro")){
				 startLoc=null;
				 endLoc=null;
				 
				 auto1.setText("");
				 auto2.setText("");
				 Toast.makeText(getActivity(), "Server Busy", Toast.LENGTH_SHORT).show();
			 }
			 else{
				 
				 //Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
				 returnedDataFromServer=result;
				 separator= new DifferentBusRoutesSeparatorClass(returnedDataFromServer,startLoc,endLoc);
				 startLoc=null;
				 endLoc=null;
				 
				 auto1.setText("");
				 auto2.setText("");
				 
		         busRoute= new ArrayList<BusNameWithStopagesClass>();
				 busRoute=separator.separation();
				 showAvailableBusMethod(busRoute);
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
	
	//getting the strings  from inputstream buffer from the returned data of the server
	private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}
	
	private void showAvailableBusMethod(ArrayList<BusNameWithStopagesClass> bus){
		
		String [] busNames;
		
		//making invisible parts
		btnInter.setVisibility(View.GONE);
		btnIntra.setVisibility(View.GONE);
		txtViewFrom.setVisibility(View.GONE);
		txtViewTo.setVisibility(View.GONE);
		auto1.setVisibility(View.GONE);
		auto2.setVisibility(View.GONE);
		btnSearch.setVisibility(View.GONE);
		
		//making visible parts
		txtViewAvailableBus.setText("Available Bus");
		txtViewAvailableBus.setVisibility(View.VISIBLE);
		busList.setVisibility(View.VISIBLE);
		
		state=3;
		
		busNames=new String[bus.size()];
		for(int i=0;i<bus.size();i++){
			busNames[i]=bus.get(i).busName;
		}
		
		adapterBusList =new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,busNames);
		busList.setAdapter(adapterBusList);
		//Toast.makeText(getActivity(), bus.size(), Toast.LENGTH_SHORT);
		busList.setOnItemClickListener(this);
		
	}
	
	//showing the district names to go from dhaka using online buying ticket facilities
	private void showDistrict(){
		
		//this is the state number
		state=5;
		
		//Invisible Part
		btnInter.setVisibility(View.GONE);
		btnIntra.setVisibility(View.GONE);
		txtViewFrom.setVisibility(View.GONE);
		txtViewTo.setVisibility(View.GONE);
		auto1.setVisibility(View.GONE);
		auto2.setVisibility(View.GONE);
		btnSearch.setVisibility(View.GONE);
		txtViewAvailableBus.setVisibility(View.GONE);
		busList.setVisibility(View.GONE);
		
		//Visible Part
		txtViewDistrict.setVisibility(View.VISIBLE);
		listViewDistrict.setVisibility(View.VISIBLE);
		
		
		adapterDistrictList= new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,strDistrict);
		
		listViewDistrict.setAdapter(adapterDistrictList);
		listViewDistrict.setOnItemClickListener(this);
		
	}
	
	
	//showing the route of the bus of the specific bus paribahan available
	
	private void showRouteOfTheBus(int selectedBus){
		
		//this is the state number
		state=4;
        
		txtViewAvailableBus.setText(busRoute.get(selectedBus).busName);
		
		ArrayList<String> stopageNamesArrayList= new ArrayList<String>();
		stopageNamesArrayList=busRoute.get(selectedBus).stopage;
		
		//getting the stopage names across the specific bus paribahan
		String[] stopageNames=new String[stopageNamesArrayList.size()];
		for(int i=0;i<stopageNamesArrayList.size();i++){
			stopageNames[i]=stopageNamesArrayList.get(i);
		}
		
		adapterStopageList =new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,stopageNames);
		busList.setAdapter(adapterStopageList);
		
	}
	
	//this method is to show the avabilable bus paribahan to  get ticket online from their website using
	//their rules
	
	private void showBusCounterLink(){
		
		//this is the state number
		state=6;
		String[] counterNames;
		
		//making invisible the districts name 
		listViewDistrict.setVisibility(View.GONE);
		txtViewDistrict.setVisibility(View.GONE);
		
		//making visible the counter list available to buy ticket
		txtViewCounter.setText("Dhaka to "+districtName);
		txtViewCounter.setVisibility(View.VISIBLE);
		
		ArrayList<String> counterList= new ArrayList<String>();
		if(national.contains(districtName)){
			counterList.add("National Travels");
		}
		if(desh.contains(districtName)){
			counterList.add("Desh Travels");
		}
		if(sakura.contains(districtName)){
			counterList.add("Sakura Paribahan");
		}
		if(shyamoli.contains(districtName)){
			counterList.add("Shyamoli Paribahan");
		}
		
		if(counterList.size()!=0){
			listViewCounter.setVisibility(view.VISIBLE); 
		
			counterNames= new String[counterList.size()];
		
			for(int i=0;i<counterList.size();i++){
				counterNames[i]=counterList.get(i);
			}
		
			adapterCounterList= new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,counterNames);
		
			listViewCounter.setAdapter(adapterCounterList);
			listViewCounter.setOnItemClickListener(this);
		}
		else{
			
			txtViewNoCounter.setVisibility(View.VISIBLE);
		}

	}

	
	//after selecting a bus paribahan, this method is to go to their website using net connection
	
	private void goToWebsite(){
		 NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	        
	     if (networkInfo != null && networkInfo.isConnected()) {
	            
		
	    	 if(webCounterName=="Shyamoli Paribahan"){
	    		 Uri uriUrl = Uri.parse("http://www.shyamoliparibahanbd.com");
	    	     Intent launchWebsite = new Intent(Intent.ACTION_VIEW, uriUrl);
	    	     startActivity(launchWebsite);
	    		 //Intent intent= new Intent(Intent.ACTION_VIEW,);
			
			}
			else if(webCounterName=="Desh Travels"){
				 Uri uriUrl = Uri.parse("http://www.deshtravelsbd.com");
	    	     Intent launchWebsite = new Intent(Intent.ACTION_VIEW, uriUrl);
	    	     startActivity(launchWebsite);
			}
			else if(webCounterName=="National Travels"){
				 Uri uriUrl = Uri.parse("http://www.nationaltravelsbd.com");
	    	     Intent launchWebsite = new Intent(Intent.ACTION_VIEW, uriUrl);
	    	     startActivity(launchWebsite);
			}
			else if(webCounterName=="Sakura Paribahan"){
				 Uri uriUrl = Uri.parse("http://www.sakuraparibahanbd.com");
	    	     Intent launchWebsite = new Intent(Intent.ACTION_VIEW, uriUrl);
	    	     startActivity(launchWebsite);
			}
	     }
	     else{
	    	 Toast.makeText(getActivity(), "Internet Connection is not available", Toast.LENGTH_SHORT).show();
	     }
		webCounterName=null;
	}
}
