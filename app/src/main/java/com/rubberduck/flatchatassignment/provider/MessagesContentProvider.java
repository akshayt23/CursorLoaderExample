package com.rubberduck.flatchatassignment.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.rubberduck.flatchatassignment.database.MessagesContract.MessageEntry;
import com.rubberduck.flatchatassignment.database.MessagesDBHelper;

import java.net.URI;

/**
 * Created by akshayt on 04/05/15.
 */
public class MessagesContentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.rubberduck.flatchatassignment.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/messages";
    static final Uri CONTENT_URI = Uri.parse(URL);

    private MessagesDBHelper dbHelper;
    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "Messages.db";

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "messages", 1);
        uriMatcher.addURI(PROVIDER_NAME, "messages/#", 2);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new MessagesDBHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(MessageEntry.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case 1:
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = MessageEntry.MESSAGE_ID + " ASC";
                break;
            case 2:
                selection = selection + MessageEntry.MESSAGE_ID + " = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
