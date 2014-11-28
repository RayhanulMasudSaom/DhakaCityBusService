package com.example.tabpracticefinal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.widget.Button;

public class FragmentPageAdapter extends FragmentPagerAdapter {

	private BusServiceFragment bus;
	private BloodHelpFragment blood;
	
	public FragmentPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int key) {
		// TODO Auto-generated method stub
		
		switch(key){
			case 0:
				bus= new BusServiceFragment();
				return bus;
			case 1:
				blood=new BloodHelpFragment();
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
	
	public void sendBackButtonResponse(int flag){
		
		bus.getBackButtonResponse(flag);
	}

}
