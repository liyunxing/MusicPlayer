package com.james.musicplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.james.musicplayer.bean.MusicInfo;

import java.util.List;

/**
 * <p>MusicListAdapter [V1.0.0]</p>
 * <p>classes : com.james.musicplayer.adapter.MusicListAdapter</p>
 * <p>谭建建 Create at 2014/11/28 0028 12:24</p>
 */
public class MusicListAdapter extends SimpleCursorAdapter {

    private Cursor musicCursor;
    private Context mContext;
    private int lvId;
    public MusicListAdapter(Context context, int layout, Cursor c, String[] from, int[] to,int flags) {
        super(context, layout, c, from, to,flags);
        mContext=context;
        lvId=layout;
        musicCursor=c;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){

        }
        return convertView;
    }

    class ViewHolder{

    }
}
