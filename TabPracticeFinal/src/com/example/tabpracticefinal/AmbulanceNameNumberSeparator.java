package com.example.tabpracticefinal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class AmbulanceNameNumberSeparator {
	
	String returnedDataWithAmbulances;
	
	AmbulanceNameNumberSeparator(String numberWithAmbulanceName){
		returnedDataWithAmbulances=numberWithAmbulanceName;
	}
	public ArrayList<AmbulanceInfo> separation() throws NoSuchElementException{
		
		StringTokenizer tokens = new StringTokenizer(returnedDataWithAmbulances,"\n");
		ArrayList<AmbulanceInfo> arrayListForEachAmbulance = new ArrayList<AmbulanceInfo>();
		
		try{
			while(tokens.hasMoreTokens()){
			AmbulanceInfo ambulance = new AmbulanceInfo(tokens.nextToken());
			String str=tokens.nextToken();
			StringTokenizer nameAndNumberSeparatorTokenizer = new StringTokenizer(str,",");
				while(nameAndNumberSeparatorTokenizer.hasMoreTokens()){
				
					ambulance.number.add(nameAndNumberSeparatorTokenizer.nextToken());
				}
			
			arrayListForEachAmbulance.add(ambulance);
			}
		}catch(NoSuchElementException e){
			
		}
		
		return arrayListForEachAmbulance;
	}

}
