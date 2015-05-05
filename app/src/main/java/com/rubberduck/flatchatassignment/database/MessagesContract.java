package com.rubberduck.flatchatassignment.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by akshayt on 04/05/15.
 */
public final class MessagesContract {

    public static final class MessageEntry implements BaseColumns {

        public static final String TABLE_NAME = "messages";
        public static final String MESSAGE_ID = "_id";
        public static final String MESSAGE_TYPE = "type";
        public static final String MESSAGE_DATA = "data";
        public static final String MESSAGE_TIMESTAMP = "timestamp";

    }

}
