package com.example.newbusroute;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BusList extends Activity implements OnItemClickListener{

	ListView list;
	ArrayList<Bus> bus;
	String [] busnames;
	ArrayAdapter<String> adapter;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.available_bus_show);
		Intent intent= getIntent();
		bus= (ArrayList<Bus>) intent.getSerializableExtra("BusRoutes");
		busnames=new String[bus.size()];
		for(int i=0;i<bus.size();i++){
			busnames[i]=bus.get(i).busName;
		}
		list= (ListView) findViewById(R.id.listView1);
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,busnames);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		ArrayList<String> str= bus.get(position).stopage;
		str.add(bus.get(position).busName);
		Intent intent= new Intent(this,RouteOfTheBus.class);
		intent.putStringArrayListExtra("Routes", str);
		startActivity(intent);
	}
}
