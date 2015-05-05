package com.rubberduck.flatchatassignment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rubberduck.flatchatassignment.R;

/**
 * Created by akshayt on 05/05/15.
 */
public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public CustomAdapter(Context context) {
        Log.d("CustomAdapter", "Check");
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("CustomAdapter", "Check" + i + 1);
        if (view == null) {
            view = mInflater.inflate(R.layout.listview_text_layout, viewGroup, false);

        }
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText("Check " + i+1);

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
