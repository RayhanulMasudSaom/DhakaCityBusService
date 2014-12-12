package com.example.tabpracticefinal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.widget.Button;

public class FragmentPageAdapter extends FragmentPagerAdapter {

	private BusServiceFragment bus;
	private BloodHelpFragment blood;
	private ConnectivityManager connMgr;
	
	public FragmentPageAdapter(FragmentManager fm,ConnectivityManager connMgr) {
		super(fm);
		this.connMgr=connMgr;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int key) {
		// TODO Auto-generated method stub
		
		switch(key){
			case 0:
				bus= new BusServiceFragment(connMgr);
				return bus;
			case 1:
				blood=new BloodHelpFragment(connMgr);
				return blood;
				
	        default:
	        	break;
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	public void sendBackButtonResponse(int flag,int l){
		if(l==0)
			bus.getBackButtonResponse(flag);
		else if(l==1)
			blood.getBackButtonResponse(flag);
	}

}
