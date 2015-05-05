package com.rubberduck.flatchatassignment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.rubberduck.flatchatassignment.database.MessagesDBHelper;
import com.rubberduck.flatchatassignment.database.MessagesContract.MessageEntry;
import com.rubberduck.flatchatassignment.fragment.MainFragment;
import com.rubberduck.flatchatassignment.fragment.MessageFragment;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements
        MainFragment.OnMainFragmentInteractionListener {

    private final static String TAG = "MainActivity";

    private final static String JSON_MSG_ID = "msg_id";
    private final static String JSON_MSG_TIMESTAMP = "timestamp";
    private final static String JSON_MSG_DATA = "msg_data";
    private final static String JSON_MSG_TYPE = "msg_type";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .build();

        ImageLoader.getInstance().init(config);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            MessageFragment messageFragment = new MessageFragment();
            MainFragment mainFragment = new MainFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mainFragment).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //addDummyData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addMessages(View view) {
        AddToDbAsync addToDbAsync = new AddToDbAsync(this);
        addToDbAsync.execute();
    }

    public void showMessages(View view) {
        Intent intent = new Intent(this, MessagesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMainFragmentInteraction(Uri uri) {

    }

    private class AddToDbAsync extends AsyncTask<Void, Void, Void> {
        private Context mContext;
        private boolean alreadyPresent = false;

        private AddToDbAsync(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = new MessagesDBHelper(mContext).getWritableDatabase();
            try {
                JSONObject jsonObject = new JSONObject("{\"chats\": [{\"timestamp\": \"default\", \"msg_id\": \"1\", \"msg_data\": \"Hello, how are you ?\", \"msg_type\": \"0\"}, {\"timestamp\": \"default\", \"msg_id\": \"2\", \"msg_data\": \"http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/3.jpg\", \"msg_type\": \"1\"}, {\"timestamp\": \"default\", \"msg_id\": \"3\", \"msg_data\": \"How is weather ?\", \"msg_type\": \"0\"}, {\"timestamp\": \"default\", \"msg_id\": \"4\", \"msg_data\": \"http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/3.jpg\", \"msg_type\": \"1\"}, {\"timestamp\": \"default\", \"msg_id\": \"5\", \"msg_data\": \"http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/3.jpg\", \"msg_type\": \"1\"}, {\"timestamp\": \"default\", \"msg_id\": \"6\", \"msg_data\": \"Tomorrow is sunday\", \"msg_type\": \"0\"}, {\"timestamp\": \"default\", \"msg_id\": \"7\", \"msg_data\": \"To define one such view, you need to specify it an Android Context. This is usually the Activity where the tabs will be displayed. Supposing that you initialize your tabs in an Activity, simply pass the Activity instance as a Context\", \"msg_type\": \"0\"}, {\"timestamp\": \"default\", \"msg_id\": \"8\", \"msg_data\": \"http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/3.jpg\", \"msg_type\": \"1\"}]}");
                JSONArray messages = jsonObject.getJSONArray("chats");

                ContentValues contentValues = new ContentValues();
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject object = messages.getJSONObject(i);

                    contentValues.put(MessageEntry.MESSAGE_ID, object.getString(JSON_MSG_ID));
                    contentValues.put(MessageEntry.MESSAGE_TYPE, object.getString(JSON_MSG_TYPE));
                    contentValues.put(MessageEntry.MESSAGE_DATA, object.getString(JSON_MSG_DATA));
                    contentValues.put(MessageEntry.MESSAGE_TIMESTAMP, object.getString(JSON_MSG_TIMESTAMP));

                    db.insertOrThrow(MessageEntry.TABLE_NAME, null, contentValues);
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(mContext, "Adding messages to database!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(mContext, "Messages already present!", Toast.LENGTH_SHORT).show();
                    }
                });
                alreadyPresent = true;
                Log.d(TAG, "Could not parse JSON and insert to DB");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!alreadyPresent) { 
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(mContext, "Done!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


}


