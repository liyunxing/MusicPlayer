/**
 * MyViewPagerAdapter.java [V1.0.0]
 * classes : com.james.musicplayer.adapter.MyViewPagerAdapter
 * 谭建建 Create at 2014-10-16 下午5:54:36
 */
package com.james.musicplayer.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 首页ViewPager适配器
 * com.james.musicplayer.adapter.MyViewPagerAdapter
 * @author 谭建建 
 * Create at 2014-10-16 上午 9:14:36
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments = new ArrayList<Fragment>();

	public MyViewPagerAdapter(FragmentManager fragmentManager,
			ArrayList<Fragment> fragments) {
		super(fragmentManager);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
