package com.james.musicplayer.activities;

import java.util.ArrayList;

import com.james.musicplayer.R;
import com.james.musicplayer.adapter.MyViewPagerAdapter;
import com.james.musicplayer.service.IMusicControlService;
import com.james.musicplayer.tools.FileScanner;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private ViewPager viewPager;// 页卡内容
	private ImageView ivMainTitleBottom;// 动画图片
	private TextView txtMusicsList, txtArtistsList, txtAlbumsList;// 标题

	// 各个页卡
	private MusicsListFragment mMusicsFrag;
	private ArtistsListFragment mArtistsFrag;
	private AlbumsListFragment mAlbumsFrag;

	private ArrayList<Fragment> fragmentList;// 页面列表

	// 头标页卡编号
	private final int MUSICS_INDEX = 0;
	private final int ARTISTS_INDEX = 1;
	private final int ALBUMS_INDEX = 2;

	private int offset = 0;// 动画图片偏移量
	private int currIndex = MUSICS_INDEX;// 当前页卡编号
	private int bmpW;// 动画图片宽度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MainActivity.this.getContentResolver();
		InitImageView();
		InitTextView();
		InitViewPager();
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		fragmentList = new ArrayList<Fragment>();
		mMusicsFrag = new MusicsListFragment();
		mArtistsFrag = new ArtistsListFragment();
		mAlbumsFrag = new AlbumsListFragment();

		fragmentList.add(mMusicsFrag);
		fragmentList.add(mArtistsFrag);
		fragmentList.add(mAlbumsFrag);

		viewPager.setAdapter(new MyViewPagerAdapter(
				getSupportFragmentManager(), fragmentList));
		viewPager.setCurrentItem(MUSICS_INDEX);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		txtMusicsList = (TextView) findViewById(R.id.txtMusicsList);
		txtArtistsList = (TextView) findViewById(R.id.txtArtistsList);
		txtAlbumsList = (TextView) findViewById(R.id.txtAlbumsList);

		// 设置头标点击监听
		txtMusicsList.setOnClickListener(new MyOnClickListener(
				this.MUSICS_INDEX));
		txtArtistsList.setOnClickListener(new MyOnClickListener(
				this.ARTISTS_INDEX));
		txtAlbumsList.setOnClickListener(new MyOnClickListener(
				this.ALBUMS_INDEX));
	}

	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		ivMainTitleBottom = (ImageView) findViewById(R.id.ivMainTitleBottom);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.main_title_bottom).getWidth();// 获取图片宽度

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		ivMainTitleBottom.setImageMatrix(matrix);// 设置动画初始位置
	}

	/**
	 * 头标点击监听
	 */
	private class MyOnClickListener implements OnClickListener {

		private int index = MainActivity.this.MUSICS_INDEX;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			ivMainTitleBottom.startAnimation(animation);
			Toast.makeText(MainActivity.this,
					"您选择了" + viewPager.getCurrentItem() + "页卡",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Intent intent;
		switch (id) {
		case R.id.menu_scan:
			/*intent = new Intent(this, MusicPlayActivity.class);
			startActivity(intent);*/
			new FileScanner(this).scanMp3File();
			break;
		case R.id.menu_quit:
			intent = new Intent(this, MusicPlayActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_settings:
			intent = new Intent(this, MusicPlayActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
