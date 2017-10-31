package com.example.android.contact_share;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ROHAN on 17-10-2017.
 */

public class Contract {


    public static final String AUTHORITY = "com.example.android.contact_share";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "share";

    public static final class entry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String TABLE_NAME = "Contract";
        public static final String COLUMN_NAME = "Name_of_Person";
        public static final String COLUMN_NUMBER="Number_of_Person";

    }

}
