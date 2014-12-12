package com.example.tabpracticefinal;

import java.util.ArrayList;

public class BloodZoneInfo {
	
	public ArrayList<String> nameRepresentatives;
	public ArrayList<String> number;
	public String zoneName;
	
	BloodZoneInfo(String zoneName){
		this.zoneName=zoneName;
		this.number= new ArrayList<String>();
		this.nameRepresentatives= new ArrayList<String>();
	}

}
