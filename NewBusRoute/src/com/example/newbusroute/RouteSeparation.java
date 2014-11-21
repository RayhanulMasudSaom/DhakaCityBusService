package com.example.newbusroute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import com.example.newbusroute.Bus;

public class RouteSeparation {
	
	String givenRoute;
	String from;
	String to;
	RouteSeparation(String route,String from, String to){
		givenRoute=route;
		this.from=from;
		this.to=to;
	}
	public ArrayList<Bus> separation(){
		
		StringTokenizer tokens = new StringTokenizer(givenRoute,"\n");
		ArrayList<Bus> route = new ArrayList<Bus>();
		while(tokens.hasMoreTokens()){
			
			Bus bus = new Bus(tokens.nextToken());
			StringTokenizer help = new StringTokenizer(tokens.nextToken(),",");
			while(help.hasMoreTokens()){
				
				bus.stopage.add(help.nextToken());
			}
			
			route.add(bus);
		}
		
		String fullRoutes = "";
		
		//sorting according to number of stopages between from to to locations
		for(int i=0;i<route.size()-1;i++){
			for(int j=i+1;j<route.size();j++){
				
				int k=route.get(i).stopage.indexOf(from);
				int m= route.get(i).stopage.indexOf(to);
				int r= k-m;
				if(r<0)
					r=0-r;
				k=route.get(j).stopage.indexOf(from);
				m=route.get(j).stopage.indexOf(to);
				int s= k-m;
				if(s<0)
					s=0-s;
				
				if(r>s){	
					Collections.swap(route,i,j);
				
				}
			}
		}
		
		//make it serial from to to location
		for(int i=0;i<route.size();i++){
			int k= route.get(i).stopage.size()-route.get(i).stopage.indexOf("Science Lab");
			int m= route.get(i).stopage.size()- route.get(i).stopage.indexOf("Atimkhana");
			if(k<m){
				for(int j=0,r=route.get(i).stopage.size()-1;j<route.get(i).stopage.size()/2;j++,r--){
					Collections.swap(route.get(i).stopage,j,r);
				}
			}
		}
		
		return route;
	}

}
