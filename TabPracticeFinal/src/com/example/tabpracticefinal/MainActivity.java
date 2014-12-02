package com.example.tabpracticefinal;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends  FragmentActivity implements ActionBar.TabListener{
	
	ActionBar actionbar;
	ViewPager viewPage;
	FragmentPageAdapter ft;
	int l;
	int flag=1;
	ConnectivityManager connMgr;
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		//new MainActivity();
		viewPage.setCurrentItem(l);
		flag=0;
		ft.sendBackButtonResponse(flag,l);
		flag=1;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		viewPage= (ViewPager) findViewById(R.id.ViewPager);
		ft= new FragmentPageAdapter(getSupportFragmentManager(),connMgr);
		actionbar= getActionBar();
		viewPage.setAdapter(ft);
		
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionbar.addTab(actionbar.newTab().setText("Bus Service").setTabListener(this));
		actionbar.addTab(actionbar.newTab().setText("Blood Help").setTabListener(this));
		viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionbar.setSelectedNavigationItem(arg0);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPage.setCurrentItem(arg0.getPosition());
		l=arg0.getPosition();
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
		
	}
	
	public void ge(){
		
	}
	


	
}
