package com.james.musicplayer.activities;

import com.james.musicplayer.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArtistsListFragment  extends Fragment{
	
	private View mMainView;
	/*private TextView txt;
	private Button btn;*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment_artists_list, (ViewGroup)getActivity().findViewById(R.id.viewPager), false);
		
		/*txt = (TextView)mMainView.findViewById(R.id.tv1);
		btn = (Button)mMainView.findViewById(R.id.btn1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				txt.setText("Hello Viewpager\"");
			}
		});*/
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup grop = (ViewGroup) mMainView.getParent(); 
        if (grop != null) { 
        	grop.removeAllViewsInLayout(); 
        } 		
		return mMainView;
	}
}
