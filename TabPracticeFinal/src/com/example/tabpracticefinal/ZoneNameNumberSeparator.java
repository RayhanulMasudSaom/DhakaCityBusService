package com.example.tabpracticefinal;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ZoneNameNumberSeparator {
	
String returnedDataWithAmbulances;
	
	ZoneNameNumberSeparator(String numberWithZoneName){
		returnedDataWithAmbulances=numberWithZoneName;
	}
	
	public ArrayList<BloodZoneInfo> separation() throws NoSuchElementException{
		
		StringTokenizer tokens = new StringTokenizer(returnedDataWithAmbulances,"\n");
		ArrayList<BloodZoneInfo> arrayListForEachZone = new ArrayList<BloodZoneInfo>();
		
		try{
			while(tokens.hasMoreTokens()){
			BloodZoneInfo zone = new BloodZoneInfo(tokens.nextToken());
			String str=tokens.nextToken();
			StringTokenizer nameAndNumberSeparatorTokenizer = new StringTokenizer(str,",");
				while(nameAndNumberSeparatorTokenizer.hasMoreTokens()){
				
					zone.nameRepresentatives.add(nameAndNumberSeparatorTokenizer.nextToken());
					zone.number.add(nameAndNumberSeparatorTokenizer.nextToken());
				}
			
			arrayListForEachZone.add(zone);
			}
		}catch(NoSuchElementException e){
			
		}
		
		return arrayListForEachZone;
	}


}
