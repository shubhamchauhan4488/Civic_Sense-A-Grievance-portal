package com.example.shubhamchauhan.myapplication.Adapter;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubhamchauhan.myapplication.Database.MyDatabase;
import com.example.shubhamchauhan.myapplication.Models.Complaint;
import com.example.shubhamchauhan.myapplication.Models.CustomImageView;
import com.example.shubhamchauhan.myapplication.R;
import com.example.shubhamchauhan.myapplication.ScrollingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubhamchauhan on 30/11/17.
 */

public class MyComplaintAdapter extends RecyclerView.Adapter<MyComplaintAdapter.MyViewHolder> {

    private ArrayList<Complaint> complaintArrayList;

    public MyComplaintAdapter(ArrayList<Complaint> complaintArrayList) {
        this.complaintArrayList = complaintArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_complaint, parent, false);

        return new MyViewHolder(parent.getContext(), itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Complaint complaint = complaintArrayList.get(position);
        Bitmap bmp = BitmapFactory.decodeByteArray(complaint.getImage(), 0, complaint.getImage().length);
        holder.img.setImageBitmap(bmp);
        // Populate the data into the template view using the data object
        holder.txtCategory.setText(complaint.getCategory());
        Log.d("log3", complaint.getCategory());
        holder.txtTitle.setText(complaint.getTitle());
        holder.emailBtn.setTag(complaint);
        holder.myComplaint = complaint;
        Log.d("log4", "inside adapter: ");
    }

    @Override
    public int getItemCount() {
        return complaintArrayList.size();
    }


    //View Holder inner class declaration
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Complaint myComplaint;
        Context context;
        TextView txtCategory;
        TextView txtTitle;
        ImageView img;
        ImageButton emailBtn;
        ImageButton deleteBtn;
        Intent chooser =null;
        final private String DATABASE_NAME = "MyDatabase6";
        private MyDatabase database;

        //View holder constructor
        public MyViewHolder(final Context context, View itemView) {
            super(itemView);
            Log.d("log4", "inside adapter: ");
            //    Lookup view for data population
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            img = (ImageView) itemView.findViewById(R.id.image);
            emailBtn = (ImageButton) itemView.findViewById(R.id.emailBtn);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.deleteBtn);
            this.context = context;

            emailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("mailto:"));
                    intent.setType("message/rfc822");
                    String[] to = {myComplaint.getCategory()+"dept"+"@gmail.com"};
                    intent.putExtra(Intent.EXTRA_EMAIL, to );
                    intent.putExtra(Intent.EXTRA_SUBJECT, txtTitle.getText().toString()  );

                    chooser = intent.createChooser(intent, "Send Email");
                    context.startActivity(chooser);

                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    database = Room.databaseBuilder(context, MyDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
                                    database.complaintDAO().delete(myComplaint);
                                    complaintArrayList.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Complaint Deleted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle b = new Bundle();
                    b.putString("title", myComplaint.getTitle());
                    //Log.d("log", complaint.getTitle());
                    ScrollingActivity.startIntent(context, b);
                }
            });

        }
    }

}
