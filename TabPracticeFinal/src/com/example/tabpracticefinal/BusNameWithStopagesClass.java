package com.example.tabpracticefinal;

import java.io.Serializable;
import java.util.ArrayList;

public class BusNameWithStopagesClass{
	public String busName;
	public ArrayList<String> stopage;
	BusNameWithStopagesClass(String busName){
		this.busName=busName;
		this.stopage= new ArrayList<String>();
	}

}
