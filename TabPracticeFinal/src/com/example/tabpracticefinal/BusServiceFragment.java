package com.example.tabpracticefinal;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class BusServiceFragment extends Fragment implements OnClickListener {

	int state=1;
	View view;
	Button btnInter,btnIntra,btnSearch;
	TextView txtView1,txtView2;
	AutoCompleteTextView auto1,auto2;
	
	ViewPager pager;
	LayoutInflater infla;
	//FragmentPageAdapter ft;
	Button btn;
	public BusServiceFragment(){
		
	}
	
	
	public View onCreateView(final LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		/*final Button button = (Button) rootView.findViewById(R.id.button);
	    b1.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            // How to start a new activity form here?????
	        }
	    });*/
		view= inflater.inflate(R.layout.bus_service_layout, container, false);
		//ft= new FragmentPageAdapter(getSupportFragmentManager());
		btnInter= (Button) view.findViewById(R.id.InterBusService);
		btnIntra= (Button) view.findViewById(R.id.IntraBusService);
		btnSearch=(Button) view.findViewById(R.id.button1);
		txtView1= (TextView) view.findViewById(R.id.textView1);
		txtView2=(TextView) view.findViewById(R.id.textView2);
		auto1= (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView1);
		auto2=(AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView2);
		btnIntra.setOnClickListener(this);
		
		/*/btn= (Button) view.findViewById(R.id.InterBusService);
		//pager.setAdapter(ft);*/
		 pager= (ViewPager) getActivity().findViewById(R.id.ViewPager);
		 state=1;
		/*//v =view;
		infla=inflater;
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				
			}
		});*/
		return view;
	}
	
	/*Fragment mFragment = new third_fragment_view();
    FragmentTransaction ft = getFragmentManager().beginTransaction();
//  ft.replace(R.id.Maincontainer, mFragment);
    ft.replace(R.id.main_fragment, mFragment);
    ft.addToBackStack(null);
    ft.commit();*/
	
	/*public void selectFrag(View view) {
		         Fragment fr = null;
		         if(view == view.findViewById(R.id.InterBusService)) {
		             fr = new InputLoc();
		         }else {
		             //fr = new FragmentOne();
		         }
		         android.support.v4.app.FragmentManager fm = getFragmentManager();
		         FragmentTransaction fragmentTransaction = fm.beginTransaction();
		         fragmentTransaction.replace(R.id.fragment_place, fr);
		         fragmentTransaction.commit();
		    }

	*/
	/*public void inter(View v){
		Fragment mFragment= new InputLoc();
		FragmentTransaction ft= getFragmentManager().beginTransaction();
		ft.replace(R.id.MainContainer, mFragment);
		ft.addToBackStack(null);
		ft.commit();
	}*/
	
	/*public void inter(View v){
		
		pager.setCurrentItem(2);
		
	}*/
	
	/*public void inter(View v){
		
		Intent intent= new Intent(this,InputLoc.class);
	    getActivity().startActivityFromFragment(getParentFragment(), intent, 1);
		}*/
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		btnInter.setVisibility(v.GONE);
		btnIntra.setVisibility(v.GONE);
		txtView1.setVisibility(v.VISIBLE);
		txtView2.setVisibility(v.VISIBLE);
		auto1.setVisibility(v.VISIBLE);
		auto2.setVisibility(v.VISIBLE);
		btnSearch.setVisibility(v.VISIBLE);
		state=2;
		
	}
	
	/*public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	btnInter.setVisibility(view.VISIBLE);
			btnIntra.setVisibility(view.GONE);
			txtView1.setVisibility(view.VISIBLE);
			txtView2.setVisibility(view.VISIBLE);
			auto1.setVisibility(view.VISIBLE);
			auto2.setVisibility(view.VISIBLE);
			btnSearch.setVisibility(view.VISIBLE);
	        return true;
	    }
	    //return false;
	    return false;
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}*/
	
	/*public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	        //Log.i("MyTab Back", "In MyTab ");
	    	btnInter.setVisibility(view.VISIBLE);
			btnIntra.setVisibility(view.VISIBLE);
			txtView1.setVisibility(view.GONE);
			txtView2.setVisibility(view.GONE);
			auto1.setVisibility(view.GONE);
			auto2.setVisibility(view.GONE);
			btnSearch.setVisibility(view.GONE);
	        return true;
	    	
	    }
	    //return super.dispatchKeyEvent(event);
	    return false;
	}*/
	/*@Override
	public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		
		if(arg2.getKeyCode()== KeyEvent.KEYCODE_BACK){
			btnInter.setVisibility(arg0.VISIBLE);
			btnIntra.setVisibility(arg0.VISIBLE);
			txtView1.setVisibility(arg0.GONE);
			txtView2.setVisibility(arg0.GONE);
			auto1.setVisibility(arg0.GONE);
			auto2.setVisibility(arg0.GONE);
			btnSearch.setVisibility(arg0.GONE);
	        return true;
	    	
		}
		return false;
	}*/
	
	public void getBackButtonResponse(int flag){
			
			if(state==2){
				btnInter.setVisibility(view.VISIBLE);
				btnIntra.setVisibility(view.VISIBLE);
				txtView1.setVisibility(view.GONE);
				txtView2.setVisibility(view.GONE);
				auto1.setVisibility(view.GONE);
				auto2.setVisibility(view.GONE);
				btnSearch.setVisibility(view.GONE);
				state=1;
			}
			else if(state==1){
				getActivity().finish();
				
			}
	}
	
	
}
