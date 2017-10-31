package com.example.android.contact_share;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ROHAN on 27-10-2017.
 */

public class HomeFragment extends Fragment {

    public HomeFragment()
    {

    }


    private TextView mTextMessage;
    private SQLiteDatabase mdb;


    //private
    private Cursor mData;
    Cursor cursor;
    // private Cursor toStore;
    private int i=1;
    private int indexName;
    private int indexNumber;
    private adapter mAdapter;
    private RecyclerView mRecycle;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mRecycle=(RecyclerView)rootView.findViewById(R.id.rec);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        DBHelper dhelper=new DBHelper(getActivity());
        mdb=dhelper.getWritableDatabase();
        final Cursor cursor=getAllName();
        mAdapter = new adapter(getActivity(), cursor);
        mRecycle.setAdapter(mAdapter);


        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    1);
        }
        else
            new Contactfetch().execute();
       // new Contactfetch().execute();//Asyntask

        return rootView;

    }

    public class Contactfetch extends AsyncTask<Void, Void, Cursor> {

        // Invoked on a background thread
        @Override
        protected Cursor doInBackground(Void... params) {
            // Make the query to get the data

                // Get the content resolver


                ContentResolver resolver = getActivity().getContentResolver();

                // Call the query method on the resolver with the correct Uri from the contract class
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER};

                cursor = resolver.query(uri,null,null,null,null);


                indexName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
          //  Log.e("hi i am rohan", DatabaseUtils.dumpCursorToString(cursor));

            return cursor;

        }


        // Invoked on UI thread
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            // Set the data for MainActivity
            int j=1;
            mData = cursor;
            do {
                add_guest();
            }while(mData.moveToNext());
            Log.i("hi i am rohan",DatabaseUtils.dumpCursorToString(mData));
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



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    new Contactfetch().execute();

                } else {

                    Toast.makeText(getContext(), "Make sure you give permission", Toast.LENGTH_SHORT).show();
                    // permission denied, Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }}



}



