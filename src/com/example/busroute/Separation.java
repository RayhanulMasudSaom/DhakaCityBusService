package com.example.busroute;

import java.util.*;


public class Separation {
	
	public String StringDivide(){
		
		String returnedString= "Class 9"+ "\n"+"masud,kamrul,real"+"\n"+"Class 10"+"\n"
		+"waheed,monir,nirjhor,rubel,hera"+"\n"+"Class 11"+"\n"+"nayeem,sami,shakib,mushfiq,halim,moratza"+
				"\n"+"Class12"+"\n"+"rajib,imran"+"\n"+"Class13"+"\n"+"noman,jubair"+"\n"
		+"Class14"+"\n"+"real,monir"+"\n";
		
		String routeString= "Bikolpo"+"\n"+"Dhakeshwari,Atimkhana,Azimpur,NilKhet,New Market,Science Lab,Kolabagan,RaselSquare"+"\n"
		+"Falgun"+"\n"+"Shahbagh,BataSignal,Science Lab,Atimkhana,Dhakeshwari"+
				"\n"+"Metro"+"\n"+"City College,Science Lab,Azimpur,Atimkhana"+"\n"+
				"Winner"+"\n"+"Mohammadpur,Science Lab,Nilkhet,Azimpur,Atimkhana"+"\n"+
				"Dhamrai"+"\n"+"Kolabagan,Science Lab,New Market,Nilkhet,Azimpur,Atimkhana"+"\n";
		
		StringTokenizer tokens = new StringTokenizer(routeString,"\n");
		int n=tokens.countTokens();
		String l= Integer.toString(n);
		//return l;
		//int i=0;
		//ArrayList<ArrayList<String>> datum= new ArrayList<ArrayList<String>>(5);
		
		//ArrayList<String>[] lists = (ArrayList<String>[])new ArrayList[n/2];
		//int [] arr= new int [5];
		/*while (tokens.hasMoreTokens())
		{
			//System.out.println(tokens.nextToken() + "\n");
			lists[i].add(tokens.nextToken());
			StringTokenizer help= new StringTokenizer(tokens.nextToken(),",");
			while(help.hasMoreTokens()){
				lists[i].add(help.nextToken());
			}
			//lists[i].add(tokens.nextToken());
			i++;
			
		}
		l=Integer.toString(lists[0].size());
		//ArrayList<String>[] lists = (ArrayList<String>[])new ArrayList[10];
		return l;*/
		ArrayList<BRoute> route = new ArrayList<BRoute>();
		while(tokens.hasMoreTokens()){
			
			BRoute bus = new BRoute(tokens.nextToken());
			StringTokenizer help = new StringTokenizer(tokens.nextToken(),",");
			while(help.hasMoreTokens()){
				
				bus.routes.add(help.nextToken());
			}
			
			route.add(bus);
		}
		
		String farabi = "";
		
		
		for(int i=0;i<route.size()-1;i++){
			for(int j=i+1;j<route.size();j++){
				int k=route.get(i).routes.indexOf("Science Lab");
				int m= route.get(i).routes.indexOf("Atimkhana");
				int r= k-m;
				if(r<0)
					r=0-r;
				k=route.get(j).routes.indexOf("Science Lab");
				m=route.get(j).routes.indexOf("Atimkhana");
				int s= k-m;
				if(s<0)
					s=0-s;
						
				//if(route.get(i).routes.size()>route.get(j).routes.size()){
					//Object temp = route.get(i);
					//route.set(i, route.get(j));
					//route.set(j,(BRoute) temp);
				if(r>s){	
					Collections.swap(route,i,j);
				
				}
			}
		}
		
		for(int i=0;i<route.size();i++){
			int k= route.get(i).routes.size()-route.get(i).routes.indexOf("Science Lab");
			int m= route.get(i).routes.size()- route.get(i).routes.indexOf("Atimkhana");
			if(k<m){
				for(int j=0,r=route.get(i).routes.size()-1;j<route.get(i).routes.size()/2;j++,r--){
					Collections.swap(route.get(i).routes,j,r);
				}
			}
		}
		
		for(int i =0;i<route.size();i++){
			
			farabi+=route.get(i).busName+": ";
			for(int j=0;j<route.get(i).routes.size();j++){
				farabi+=route.get(i).routes.get(j)+" ";
			}
			farabi+="\n";
			
		}
		
		
		
		return farabi;
	}
	
	
	/*public void swaping( ArrayList list )
	{
	   Object temp = list.get( 4 ) ;  // Save "elk"
	   list.set( 4, list.get( 0 ) ) ; // Put "ape" at index 4
	   list.set( 0, temp ) ; // Put "elk" at index 0
	}*/

}


class BRoute{
	
	public String busName;
	public ArrayList<String> routes;
	
	BRoute(String name){
		
		busName = name;
		routes = new ArrayList<String>();
	}
}