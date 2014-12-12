package com.example.tabpracticefinal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class DifferentBusRoutesSeparatorClass {
	
	String returnedDataWithAllBuses;
	String from;
	String to;
	DifferentBusRoutesSeparatorClass(String route,String from, String to){
		returnedDataWithAllBuses=route;
		this.from=from;
		this.to=to;
	}
	
	//separator the data from the returned string from the server
	
	public ArrayList<BusNameWithStopagesClass> separation() throws NoSuchElementException{
		
		StringTokenizer tokens = new StringTokenizer(returnedDataWithAllBuses,"\n");
		ArrayList<BusNameWithStopagesClass> arrayListForEachBus = new ArrayList<BusNameWithStopagesClass>();
		
		try{
			while(tokens.hasMoreTokens()){
			BusNameWithStopagesClass bus = new BusNameWithStopagesClass(tokens.nextToken());
			String str=tokens.nextToken();
			StringTokenizer busAndStopagesSeparatorTokenizer = new StringTokenizer(str);
				while(busAndStopagesSeparatorTokenizer.hasMoreTokens()){
				
					bus.stopage.add(busAndStopagesSeparatorTokenizer.nextToken());
				}
			
			arrayListForEachBus.add(bus);
			}
		}catch(NoSuchElementException e){
			
		}
		
		//sorting according to number of stopages between from to to locations
		//direction suppose we have set the direction from Azimpur to Mirpur
		//The data get from the server is that direction
		// But the user wants to go to mirpur to Azimpur
		// then here will re direct the data from mirpur to azimpur 
		for(int i=0;i<arrayListForEachBus.size()-1;i++){
			
			for(int j=i+1;j<arrayListForEachBus.size();j++){
				
				int k=arrayListForEachBus.get(i).stopage.indexOf(from);
				int m= arrayListForEachBus.get(i).stopage.indexOf(to);
				int r= k-m;
				if(r<0)
					r=0-r;
				k=arrayListForEachBus.get(j).stopage.indexOf(from);
				m=arrayListForEachBus.get(j).stopage.indexOf(to);
				int s= k-m;
				if(s<0)
					s=0-s;
				
				if(r>s){	
					Collections.swap(arrayListForEachBus,i,j);
				
				}
				
			}
			
		}
		
		//make it serial from to to location
		for(int i=0;i<arrayListForEachBus.size();i++){
			int k= arrayListForEachBus.get(i).stopage.size()-arrayListForEachBus.get(i).stopage.indexOf(from);
			int m= arrayListForEachBus.get(i).stopage.size()- arrayListForEachBus.get(i).stopage.indexOf(to);
			if(k<m){
				for(int j=0,r=arrayListForEachBus.get(i).stopage.size()-1;j<arrayListForEachBus.get(i).stopage.size()/2;j++,r--){
					Collections.swap(arrayListForEachBus.get(i).stopage,j,r);
				}
			}
		}
		
		return arrayListForEachBus;

	}
}
