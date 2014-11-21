package com.example.newbusroute;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RouteOfTheBus extends Activity{
	
	ArrayList<String> routes;
	ListView list;
	String [] str;
	ArrayAdapter adapter;
	TextView tvroute;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.route_showing);
		tvroute= (TextView) findViewById(R.id.tvroute);
		list=(ListView) findViewById(R.id.stopage);
		Intent intent= getIntent();
		routes=intent.getStringArrayListExtra("Routes");
		tvroute.setText(routes.get(routes.size()-1));
		str=new String[routes.size()-1];
		for(int i=0;i<routes.size()-1;i++){
			str[i]=routes.get(i);
		}
		adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,str);
		list.setAdapter(adapter);
	}

}
