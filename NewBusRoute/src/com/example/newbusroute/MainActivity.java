package com.example.newbusroute;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	EditText from;
	EditText to;
	Button btn;
	int count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		from=(EditText) findViewById(R.id.edTxtFrom);
		to= (EditText) findViewById(R.id.edTxtTo);
		btn=(Button) findViewById(R.id.Searching);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void searching(View v){
		String routeString= "Bikolpo"+"\n"+"Dhakeshwari,Atimkhana,Azimpur,NilKhet,New Market,Science Lab,Kolabagan,RaselSquare"+"\n"
				+"Falgun"+"\n"+"Shahbagh,BataSignal,Science Lab,Atimkhana,Dhakeshwari"+
						"\n"+"Metro"+"\n"+"City College,Science Lab,Azimpur,Atimkhana"+"\n"+
						"Winner"+"\n"+"Mohammadpur,Science Lab,Nilkhet,Azimpur,Atimkhana"+"\n"+
						"Dhamrai"+"\n"+"Kolabagan,Science Lab,New Market,Nilkhet,Azimpur,Atimkhana"+"\n";
		
		String fromLocation=from.getText().toString();
		String toLocation= to.getText().toString();
		if(fromLocation.length()!=0 && toLocation.length()!=0){
			RouteSeparation sep= new RouteSeparation(routeString,fromLocation,toLocation);
			ArrayList<Bus> busTrack= new ArrayList<Bus>();
			busTrack=sep.separation();
			Intent intent=new Intent(this,BusList.class);
			intent.putExtra("BusRoutes", busTrack);
			startActivity(intent);
		}
	}
}
