package com.example.tabpracticefinal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BloodHelpFragment extends Fragment implements OnClickListener,OnItemClickListener{

	int state=1;
	TextView txtViewBadhonZones;
	ListView listViewBadhonZones;
	Button btnBadhonZones,btnBloodBanks;
	
	View view;
	ArrayAdapter<String> adapterBadhonZones;
	String [] strBadhonZones={ "AnondoMohon College(Mymensingh)",
			"Bangladesh University of Engineering Technology (BUET)",
			"Bangladesh Agricultural University","Begum Rokeya University",
			"Bogura Govt.Azizul Haque College","BrojoMohon College(Barisal)",
			"Dhaka College(DC)","Dhaka University(DU)",
			"Jahangirnagar University(JU)","Jogonnath University",
			"Khulna University","Rajshahi University(RU)",
			"Rangpur Govt. College","Sher-e-Bangla Agricultural University","Sylthet Agricultural University",		
	};
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.blood_help_layout, container, false);
		txtViewBadhonZones= (TextView) view.findViewById(R.id.txtViewBadhonZones);
		listViewBadhonZones= (ListView) view.findViewById(R.id.listViewBadhonZones);
		btnBadhonZones= (Button) view.findViewById(R.id.btnBadhonZones);
		btnBloodBanks= (Button) view.findViewById(R.id.btnBloodBanks);
		btnBadhonZones.setOnClickListener(this);
		
		
		return view;
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		if(btnBadhonZones.isPressed()){
			btnBadhonZones.setVisibility(view.GONE);
			btnBloodBanks.setVisibility(view.GONE);
			listViewBadhonZones.setVisibility(view.VISIBLE);
			txtViewBadhonZones.setVisibility(view.VISIBLE);
			adapterBadhonZones= new ArrayAdapter<String>(this.getActivity(),R.layout.available_bus_name_show,
					R.id.txtBusName,strBadhonZones);
			listViewBadhonZones.setAdapter(adapterBadhonZones);
			state=2;
			listViewBadhonZones.setOnItemClickListener(this);
		}
		
	}
	
	public void getBackButtonResponse(int flag){
		
		if(state==2){
			btnBadhonZones.setVisibility(view.VISIBLE);
			btnBloodBanks.setVisibility(view.VISIBLE);
			listViewBadhonZones.setVisibility(view.GONE);
			txtViewBadhonZones.setVisibility(view.GONE);
			state=1;
		}
		
		else if(state==1){
			getActivity().finish();
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), strBadhonZones[arg2], Toast.LENGTH_LONG).show();
	}
	
	
	

}
