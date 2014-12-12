package com.example.tabpracticefinal;

import java.util.ArrayList;

public class AmbulanceInfo {
	
	public String ambulanceName;
	public ArrayList<String> number;
	AmbulanceInfo(String ambulanceName){
		this.ambulanceName=ambulanceName;
		this.number= new ArrayList<String>();
	}
}
