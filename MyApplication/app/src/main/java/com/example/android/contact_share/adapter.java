package com.example.android.contact_share;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ROHAN on 17-10-2017.
 */

public class adapter extends RecyclerView.Adapter<adapter.Viewholder> {

    private Context mContext;
    private Cursor mcursor;



    public adapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mcursor=cursor;

    }



    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        if(!mcursor.moveToPosition(position))
            return;
        String name1=mcursor.getString(mcursor.getColumnIndex(Contract.entry.COLUMN_NAME));
        holder.name.setText(name1);
        String number1=mcursor.getString(mcursor.getColumnIndex(Contract.entry.COLUMN_NUMBER));
        holder.number.setText(number1);

    }

    @Override
    public int getItemCount() {
        return mcursor.getCount();
    }


    class Viewholder extends RecyclerView.ViewHolder{
        TextView name;
        TextView number;

        public Viewholder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.number);
        }
    }

}
