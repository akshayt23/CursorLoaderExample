package com.rubberduck.flatchatassignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.rubberduck.flatchatassignment.database.MessagesContract.MessageEntry;

/**
 * Created by akshayt on 04/05/15.
 */
public class MessagesDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "messages.db";

    private static final String INTEGER_PK = " INTEGER PRIMARY KEY";
    private static final String TEXT_TYPE = " TEXT";
    private static final String TEXT_TYPE_NOT_NULL = " TEXT NOT NULL";
    private static final String COMMA_SEP = ", ";

    private static final String CREATE_MESSAGES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + MessageEntry.TABLE_NAME + " (" +
                    MessageEntry.MESSAGE_ID + INTEGER_PK + COMMA_SEP +
                    MessageEntry.MESSAGE_TYPE + TEXT_TYPE_NOT_NULL + COMMA_SEP +
                    MessageEntry.MESSAGE_DATA + TEXT_TYPE_NOT_NULL + COMMA_SEP +
                    MessageEntry.MESSAGE_TIMESTAMP + TEXT_TYPE_NOT_NULL +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MessageEntry.TABLE_NAME;

    public void delete(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
    }

    public MessagesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MESSAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
