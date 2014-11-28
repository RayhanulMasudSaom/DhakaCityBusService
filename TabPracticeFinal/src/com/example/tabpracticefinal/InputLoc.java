package com.example.tabpracticefinal;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InputLoc extends Fragment {
	
	public InputLoc(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.input_location_layout, container,false);
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/*public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_location_layout);
	}*/
	

}
