package com.rubberduck.flatchatassignment.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.rubberduck.flatchatassignment.Message;
import com.rubberduck.flatchatassignment.R;
import com.rubberduck.flatchatassignment.database.MessagesContract.MessageEntry;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by akshayt on 05/05/15.
 */
public class MyCursorAdapter extends CursorAdapter {

    private static final String TAG = "MyCursorAdapter";

    ImageView imageView;
    TextView textView;
    ProgressBar spinner;

    public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Log.d("MCA", "Type = " + cursor.getString(cursor.getColumnIndex(MessageEntry.MESSAGE_TYPE)));
        if (cursor.getString(cursor.getColumnIndex(MessageEntry.MESSAGE_TYPE)).equals("0"))
            return LayoutInflater.from(context).inflate(R.layout.listview_text_layout, viewGroup, false);
        else
            return LayoutInflater.from(context).inflate(R.layout.listview_image_layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor.getString(cursor.getColumnIndex(MessageEntry.MESSAGE_TYPE)).equals("0")) {
            textView = (TextView) view.findViewById(R.id.text_view);
            if (textView != null)
                textView.setText(cursor.getString(cursor.getColumnIndex(MessageEntry.MESSAGE_DATA)));
        } else {
            imageView = (ImageView) view.findViewById(R.id.image_view);
            spinner = (ProgressBar) view.findViewById(R.id.loading);
            if (imageView != null && cursor != null)
                downloadImage(imageView, spinner, cursor.getString(cursor.getColumnIndex(MessageEntry.MESSAGE_DATA)));
            //imageView.setImageBitmap(
                    //new ImageLoader().execute(cursor.getString(cursor.getColumnIndex(MessageEntry.MESSAGE_DATA)));
                    //getImage(cursor.getString(cursor.getColumnIndex(MessageEntry.MESSAGE_DATA))));

        }
    }

    private void downloadImage(ImageView imageView, final ProgressBar spinner, String url) {
        com.nostra13.universalimageloader.core.ImageLoader imageLoader =
                com.nostra13.universalimageloader.core.ImageLoader.getInstance();

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();
                //.showImageForEmptyUri(R.mipmap.ic_launcher)
                //.showImageOnFail(R.mipmap.ic_launcher)
                //.showImageOnLoading(R.mipmap.ic_launcher).build();
        //imageLoader.displayImage(url, imageView, options);
        imageLoader.displayImage(url, imageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                spinner.setVisibility(View.GONE);
            }

        });
    }

    /*private class ImageLoader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                Log.d(TAG, "URL = " + strings[0]);
                Bitmap bitmap = getBitmapFromURL(strings[0]);
                return bitmap;
                //InputStream input = new URL(strings[0]).openStream();
                //return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.d(TAG, "Download failed!");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //super.onPostExecute(bitmap);
            if (bitmap != null) {
                //if (this != null) {
                  //  notifyDataSetChanged();
                //}
                if (bitmap != null &&  imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }

        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                Log.d(TAG, "Check");
                e.printStackTrace();
                return null;
            }
        }
    }*/

}
