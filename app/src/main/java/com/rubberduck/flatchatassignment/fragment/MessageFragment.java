package com.rubberduck.flatchatassignment.fragment;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.SimpleCursorAdapter;


import com.rubberduck.flatchatassignment.Message;
import com.rubberduck.flatchatassignment.R;
import com.rubberduck.flatchatassignment.adapter.CustomAdapter;
import com.rubberduck.flatchatassignment.adapter.MyCursorAdapter;
import com.rubberduck.flatchatassignment.database.MessagesContract.MessageEntry;

import java.util.ArrayList;

public class MessageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnMessageFragmentInteractionListener mListener;

    private AbsListView mListView;
    //private SimpleCursorAdapter mAdapter;
    //private CustomAdapter mAdapter;
    private MyCursorAdapter mAdapter;
    ArrayList<Message> messages;
    private static final int MESSAGES_LOADER = 0;

    // TODO: Rename and change types of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new MyCursorAdapter(getActivity(), null, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        getLoaderManager().initLoader(MESSAGES_LOADER, null, this);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMessageFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMessageFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onMessageFragmentInteraction(String id);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {MessageEntry._ID, MessageEntry.MESSAGE_DATA,
                MessageEntry.MESSAGE_TYPE,MessageEntry.MESSAGE_TIMESTAMP};

        switch(i) {
            case MESSAGES_LOADER:
                return new CursorLoader(
                        getActivity(),
                        Uri.parse("content://com.rubberduck.flatchatassignment.provider/messages"),
                        projection,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        //if(mAdapter!=null && cursor!=null)
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        if(mAdapter!=null)
            mAdapter.swapCursor(null);
    }
}
