package com.example.android.contact_share;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private SQLiteDatabase mdb;


    //private
    private Cursor mData;
   // private Cursor toStore;
    private int i=0;
    private int indexName;
    private int indexNumber;
    private adapter mAdapter;
    private RecyclerView mRecycle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                 //   mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRecycle=(RecyclerView)this.findViewById(R.id.rec);
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        DBHelper dhelper=new DBHelper(this);
        mdb=dhelper.getWritableDatabase();
        final Cursor cursor=getAllName();
        mAdapter = new adapter(this, cursor);
        mRecycle.setAdapter(mAdapter);



        new Contactfetch().execute();
        add_guest();
    }


    public class Contactfetch extends AsyncTask<Void, Void, Cursor> {

        // Invoked on a background thread
        @Override
        protected Cursor doInBackground(Void... params) {
            // Make the query to get the data

            // Get the content resolver
            ContentResolver resolver = getContentResolver();

            // Call the query method on the resolver with the correct Uri from the contract class
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};

            Cursor cursor = resolver.query(uri, projection, null, null, null);

             indexName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            return cursor;
        }


        // Invoked on UI thread
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            // Set the data for MainActivity
            mData = cursor;
        }
    }

    private Cursor getAllName() {
        return mdb.query(
                Contract.entry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null

        );

    }

    private long add_guest() {
        ContentValues cv = new ContentValues();
        if (mData.move(i)) {


            String name = mData.getString(indexName);
            String number = mData.getString(indexNumber);

            cv.put(Contract.entry.COLUMN_NAME, name);
            cv.put(Contract.entry.COLUMN_NUMBER, number);
            i++;



        }
        return mdb.insert(Contract.entry.TABLE_NAME, null, cv);
    }




}
