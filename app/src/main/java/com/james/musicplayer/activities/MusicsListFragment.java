package com.james.musicplayer.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.james.musicplayer.R;
import com.james.musicplayer.bean.MusicInfo;
import com.james.musicplayer.db.DBInfo;
import com.james.musicplayer.service.IMusicControlService;
import com.james.musicplayer.tools.MusicListObserver;
import com.james.musicplayer.tools.QuerTools;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MusicsListFragment extends Fragment {

    private MusicListObserver mObserver;
    private ContentResolver mResolver;
    private IMusicControlService mMusicService;
    
    private View mMainView;
    private FragmentActivity mFragActivity;
    private ListView listMusics;
    private SimpleCursorAdapter mListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragActivity = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        mMainView = inflater.inflate(R.layout.fragment_musics_list,
                (ViewGroup) mFragActivity.findViewById(R.id.viewPager), false);
        
        listMusics = (ListView) mMainView.findViewById(R.id.listMusics);
        listMusics.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(mFragActivity,MusicPlayActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }            
        });
        
        //注册Observer
        mObserver = new MusicListObserver(getActivity(), mHandler);
        mResolver = getActivity().getContentResolver();
        mResolver.registerContentObserver(DBInfo.MUSIC_URI, true, mObserver);
        
        //执行数据库查询
        queryDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup grop = (ViewGroup) mMainView.getParent();
        if (grop != null) {
            grop.removeAllViewsInLayout();
        }
        return mMainView;
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            queryDB();
        }

    };

    protected void queryDB() {
        Cursor cursor = new QuerTools(mFragActivity).getMusicCursorFromDataBase();
                
        mListAdapter = new SimpleCursorAdapter(mFragActivity, R.layout.item_musics_list, cursor, new String[] {
                "_id", DBInfo.TITLE, DBInfo.ARTIST, DBInfo.ALBUM,DBInfo.DURATION }, new int[] { R.id.txtItemID, R.id.txtItemTitle,
                R.id.txtItemSinger, R.id.txtItemAlbum,R.id.txtItemDuration }, 0);
        
        listMusics.setAdapter(mListAdapter);
    }
}
