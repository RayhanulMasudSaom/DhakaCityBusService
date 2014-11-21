package com.example.newbusroute;

import java.io.Serializable;
import java.util.ArrayList;

public class Bus implements Serializable{
	public String busName;
	public ArrayList<String> stopage;
	Bus(String busName){
		this.busName=busName;
		stopage= new ArrayList<String>();
	}

}
