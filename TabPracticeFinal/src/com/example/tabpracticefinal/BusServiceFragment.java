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

	DifferentBusRoutesSeparatorClass separator;
	ArrayList<BusNameWithStopagesClass> busRoute;
	private ProgressDialog progressDialog;
	String returnedDataFromServer=null;
	String startLoc,endLoc;
	int selectedItem=-1;
	int state=1;
	View view;
	Button btnInter,btnIntra,btnSearch;
	TextView txtView1,txtView2,txtViewAvailableBus,txtViewDistrict;
	AutoCompleteTextView auto1,auto2;
	ListView busList,listViewDistrict,listViewCounter;
	
	
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
	
	String []strDistrict= {"Akkelpur","Barisal","Bogra","Bonpara","Boroghoria",
			"Chapainababhanj","Chhatak","Chittagong","Cox's Barzar",
		    "Dinajpur","Godagari","Hili","Jaipurhat","Kachikata","Kansat","Kesorhut","Khepupara",
		    "Moulavibazar","Natore","Patuakhali","Pirganj","Puthia",
		    "Rahanpur","Rajabari","Rajshahi","Rangpur",	"Rupatoli","Shaistaganj","Shibganj","Sirajganj",
		    "Sunamganj","Tangail","Sylhet"};
	String national="Chapainababganj Natore Puthia Rajshahi Sirajganj";
	String ak="Sylhet";
	
	ViewPager pager;
	LayoutInflater infla;
	ArrayAdapter<String> adapter1,adapter2,adapterDistrictList,adapterBusList,adapterStopageList,adapterCounterList;
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
		busList=(ListView) view.findViewById(R.id.listBusView);
		listViewCounter=(ListView) view.findViewById(R.id.listViewCounter);
		listViewDistrict=(ListView) view.findViewById(R.id.listViewDistrict);
		txtViewAvailableBus=(TextView) view.findViewById(R.id.txtViewAvailableBus);
		txtViewDistrict= (TextView) view.findViewById(R.id.txtViewDistrict);
		btnInter.setOnClickListener(this);
		btnIntra.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		auto1.setOnItemClickListener(this);
		auto2.setOnItemClickListener(this);
		//listViewDistrict.setOnItemClickListener(this);
		
		
		pager= (ViewPager) getActivity().findViewById(R.id.ViewPager);
		state=1;
		adapter2= new ArrayAdapter<String>(this.getActivity().getApplicationContext(),R.layout.auto_complete_text_view_show_layout,R.id.autoTextView,strLocationTo);
		adapter1= new ArrayAdapter<String>(this.getActivity().getApplicationContext(),R.layout.auto_complete_text_view_show_layout,R.id.autoTextView,strLocationFrom);
		auto1.setAdapter(adapter1);
		auto1.setThreshold(1);
		auto2.setAdapter(adapter2);
		auto2.setThreshold(1);
		
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
			txtViewAvailableBus.setVisibility(v.GONE);
			busList.setVisibility(v.GONE);
			state=2;
		}
		else if(btnIntra.isPressed()==true){
			//Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
			showDistrict();
		}
		
		else if(btnSearch.isPressed()==true){
			//Toast.makeText(getActivity(), startLoc+endLoc, Toast.LENGTH_SHORT).show();
			//returnedStringFromServer=getDataFromServer();
			//Toast.makeText(getActivity(), returnedStringFromServer, Toast.LENGTH_LONG).show();
			/*int flag=0;*/
			int start=0,end=0;
			
				
			for(int i=0;i<strLocationFrom.length;i++){
				if(strLocationFrom[i]==startLoc)
				{
					start=1;
					//flag++;
					
				}
				if(strLocationFrom[i]==endLoc)
				{
					end=1;
					//flag++;
				}
				if(start==1 && end==1)
					break;
			}
			
			if(end==0 && start==0){
				Toast.makeText(getActivity(), "Select Locations From Given List\n", Toast.LENGTH_LONG).show();
				//startLoc=null;
				//endLoc=null;
				auto1.setText("");
				auto2.setText("");
				startLoc=null;
				endLoc=null;
				return ;
			}
			
			else if(end==start && endLoc==startLoc)
			{
				//startLoc=null;
				//endLoc=null;
				startLoc=null;
				endLoc=null;
				auto1.setText("");
				auto2.setText("");
				Toast.makeText(getActivity(), "Invalid Input\nYour Current Location and Destination cannot be same", Toast.LENGTH_LONG).show();
				return ;
			}
			
			else if(start==1 && end==0)
			{
				//startLoc=null;
				endLoc=null;
				//auto1.setText("");
				auto2.setText("");
				Toast.makeText(getActivity(), "Give your Current Location to 'To' field", Toast.LENGTH_LONG).show();
				return ;
			}	
			else if(end==1 && start==0){
				startLoc=null;
				//endLoc=null;
				auto1.setText("");
				//auto2.setText("");
				Toast.makeText(getActivity(), "Give your Destination to 'From' field", Toast.LENGTH_LONG).show();
				//startLoc=null;
				//endLoc=null;
				return ;
			}
			
			//String stringUrl="http://infinite-woodland-5408.herokuapp.com/?start="+"farmgate"+"&end="+"shahbagh";
	        //ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	        
	        if (networkInfo != null && networkInfo.isConnected() && startLoc!=null && endLoc!=null) {
	            //new DownloadWebpageTask2().execute(stringUrl);
	        	new getBusNameFromServerAsync().execute("http://infinite-woodland-5408.herokuapp.com/?start="+startLoc+"&end="+endLoc);
	        	
				//flag=0;
				/*startLoc=null;
				endLoc=null;
				auto1.setText("");
				auto2.setText("");
				*/
				//Toast.makeText(getActivity(), text, duration)
	        	//Toast.makeText(getActivity(), returnedDataFromServer, Toast.LENGTH_SHORT).show();
	        } else {
	            //tv2.setText("No network connection available.");
	        	startLoc=null;
	        	endLoc=null;
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
				txtViewAvailableBus.setVisibility(view.GONE);
				busList.setVisibility(view.GONE);
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
				btnInter.setVisibility(view.GONE);
				btnIntra.setVisibility(view.GONE);
				txtView1.setVisibility(view.VISIBLE);
				txtView2.setVisibility(view.VISIBLE);
				auto1.setVisibility(view.VISIBLE);
				auto2.setVisibility(view.VISIBLE);
				btnSearch.setVisibility(view.VISIBLE);
				txtViewAvailableBus.setVisibility(view.GONE);
				busList.setVisibility(view.GONE);
				
				state=2;
			}
			else if(state==5){
				listViewDistrict.setVisibility(view.GONE);
				txtViewDistrict.setVisibility(view.GONE);
				btnInter.setVisibility(view.VISIBLE);
				btnIntra.setVisibility(view.VISIBLE);
				state=1;
			}
			else if(state==4){
				showAvailableBusMethod(busRoute);
				state=3;
			}
			else if(state==6){
				state=5;
				showDistrict();
			}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
			if(arg0.getAdapter()==adapter1)
				startLoc=(String) arg0.getItemAtPosition(arg2);
			//auto1.
			else if(arg0.getAdapter()==adapter2)
				endLoc=(String) arg0.getItemAtPosition(arg2);
			else if(arg0.getAdapter()==adapterDistrictList){
				Toast.makeText(getActivity(), (String) arg0.getItemAtPosition(arg2), Toast.LENGTH_LONG).show();
				showBusCounterLink();
			}
			
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
		}
		
		 @Override
	     protected void onPostExecute(String result) {
			 progressDialog.dismiss();
			 if(result==null){
				 startLoc=null;
				 endLoc=null;
				 
				 auto1.setText("");
				 auto2.setText("");
				 Toast.makeText(getActivity(), "No available Bus Found", Toast.LENGTH_LONG).show();
			 }
			 else if(result.contains("erro")){
				 startLoc=null;
				 endLoc=null;
				 
				 auto1.setText("");
				 auto2.setText("");
				 Toast.makeText(getActivity(), "Server Busy", Toast.LENGTH_LONG).show();
			 }
			 else{
				 
				 Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
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
			 //DifferentBusRoutesSeparatorClass separator= new DifferentBusRoutesSeparatorClass(result,startLoc,endLoc);
			 //ArrayList<BusNameWithStopagesClass> busRoute= new ArrayList<BusNameWithStopagesClass>();
			 //busRoute=separator.separation();
			 //showAvailableBusMethod(busRoute);
			//busRoute=null;
			 //busRoute=null;
				
	         //Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
	         //
	    }

	}
	
	private String downloadBusList(String myurl) throws IOException{
	    InputStream is = null;
	    // Only display the first 500 characters of the retrieved
	    // web page content.
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
	
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}
	
	public void showAvailableBusMethod(ArrayList<BusNameWithStopagesClass> bus){
		
		//ListView list;
		//ArrayList<BusNameWithStopagesClass> bus;
		String [] busNames;
		
		
		btnInter.setVisibility(view.GONE);
		btnIntra.setVisibility(view.GONE);
		txtView1.setVisibility(view.GONE);
		txtView2.setVisibility(view.GONE);
		auto1.setVisibility(view.GONE);
		auto2.setVisibility(view.GONE);
		btnSearch.setVisibility(view.GONE);
		txtViewAvailableBus.setText("Available Bus");
		txtViewAvailableBus.setVisibility(view.VISIBLE);
		busList.setVisibility(view.VISIBLE);
		
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
	
	public void showDistrict(){
		
		btnInter.setVisibility(view.GONE);
		btnIntra.setVisibility(view.GONE);
		txtView1.setVisibility(view.GONE);
		txtView2.setVisibility(view.GONE);
		auto1.setVisibility(view.GONE);
		auto2.setVisibility(view.GONE);
		btnSearch.setVisibility(view.GONE);
		txtViewAvailableBus.setVisibility(view.GONE);
		busList.setVisibility(view.GONE);
		
		txtViewDistrict.setVisibility(view.VISIBLE);
		listViewDistrict.setVisibility(view.VISIBLE);
		
		state=5;
		
		adapterDistrictList= new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,strDistrict);
		
		listViewDistrict.setAdapter(adapterDistrictList);
		listViewDistrict.setOnItemClickListener(this);
		
	}
	
	
	
	public void showRouteOfTheBus(int selectedBus){
		
        ArrayList<String> stopageNamesArrayList;
		
		
		/*btnInter.setVisibility(view.GONE);
		btnIntra.setVisibility(view.GONE);
		txtView1.setVisibility(view.GONE);
		txtView2.setVisibility(view.GONE);
		auto1.setVisibility(view.GONE);
		auto2.setVisibility(view.GONE);
		btnSearch.setVisibility(view.GONE);*/
		txtViewAvailableBus.setText(busRoute.get(selectedBus).busName);
		//txtViewAvailableBus.setVisibility(view.VISIBLE);
		//busList.setVisibility(view.VISIBLE);
		
		state=4;
		
		stopageNamesArrayList=busRoute.get(selectedBus).stopage;
		String[] stopageNames=new String[stopageNamesArrayList.size()];
		for(int i=0;i<stopageNamesArrayList.size();i++){
			stopageNames[i]=stopageNamesArrayList.get(i);
		}
		
		adapterStopageList =new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,stopageNames);
		busList.setAdapter(adapterBusList);
		//Toast.makeText(getActivity(), bus.size(), Toast.LENGTH_SHORT);
		//busList.setOnItemClickListener(this);
	}
	
	public void showBusCounterLink(){
		String districtName="Rajshahi";
		String[] counterNames;
		listViewDistrict.setVisibility(view.GONE);
		listViewCounter.setVisibility(view.VISIBLE);
		//txtViewDistrict.setText("Dhaka to "+districtName);
		ArrayList<String> counterList=null;
		if(national.contains(districtName)){
			counterList.add("National Travels");
			Toast.makeText(getActivity(), "yess", Toast.LENGTH_SHORT).show();
		}
		if(ak.contains(districtName)){
			counterList.add("AK Travels");
		}
		 
		counterNames= new String[counterList.size()];
		for(int i=0;i<counterList.size();i++){
			counterNames[i]=counterList.get(i);
		}
		state=6;
		adapterCounterList= new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,R.id.txtBusName,counterNames);
		
		listViewCounter.setAdapter(adapterCounterList);
		listViewCounter.setOnItemClickListener(this);
		//listViewDistrict.setOnItemClickListener(this);
		//listViewDistrict.setOnItemSelectedListener(this);
	}


	/*@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if(arg0.getAdapter()==adapterBusList){
			showRouteOfTheBus(arg2);
		}
		else if(arg0.getAdapter()==adapterDistrictList){
			Toast.makeText(getActivity(), (String) arg0.getItemAtPosition(arg2), Toast.LENGTH_LONG).show();
			showBusCounterLink((String)arg0.getItemAtPosition(arg2));
		}
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}*/
}
