package com.example.busroute;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RouteList extends Activity implements OnItemClickListener{
	
	
	String[] routeList= {"Uttara","Banani","Azimpur","Asadgate","Kamlapur","Shyamoli"};
	ListView lstView;
	ArrayAdapter<String> adapter;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.route);
		lstView= (ListView) findViewById(R.id.lstView);
		adapter=new ArrayAdapter<String> (this,R.layout.list,R.id.editText1,routeList);
		lstView.setAdapter(adapter);
		lstView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		Toast.makeText(this, "Selected: "+routeList[arg2],Toast.LENGTH_SHORT ).show();
	}
}
