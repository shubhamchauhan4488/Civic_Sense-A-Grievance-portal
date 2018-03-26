package com.example.shubhamchauhan.myapplication;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shubhamchauhan.myapplication.Database.MyDatabase;
import com.example.shubhamchauhan.myapplication.Models.Complaint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ScrollingActivity extends AppCompatActivity{

    TextView txtComplaintDescription;
    TextView txtDate;
    TextView txtTime;
    TextView txtAddress;
    ImageView image;
    ImageButton  imgBtn;
    private GoogleMap mMap;
    String lat;
    String lon;


    final private String DATABASE_NAME = "MyDatabase6";
    private MyDatabase database;

    public static void startIntent(Context context) {
        context.startActivity(new Intent(context, ScrollingActivity.class));
    }

    public static void startIntent(Context context, Bundle bundle) {
        Intent i = new Intent(context, ScrollingActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionBar("");
        if(getActionBar()!= null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
            txtComplaintDescription = (TextView)findViewById(R.id.txtComplaintDescription) ;
            txtDate = (TextView)findViewById(R.id.txtDate) ;
            txtTime  = (TextView)findViewById(R.id.txtTime) ;
            txtAddress = (TextView)findViewById(R.id.txtAddress) ;
            image = (ImageView)findViewById(R.id.image);
            imgBtn = (ImageButton)findViewById(R.id.imgBtn) ;
            Toolbar toolbar1 = (Toolbar)findViewById(R.id.toolbar);

            database = Room.databaseBuilder(this, MyDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        Bundle b = getIntent().getExtras();
        Complaint complaint = database.complaintDAO().findByTitle(b.getString("title"));

        Bitmap bmp = BitmapFactory.decodeByteArray(complaint.getImage(), 0, complaint.getImage().length);
        //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200    , 300, false));
        image.setImageBitmap(bmp);

            toolbar1.setTitle(complaint.getTitle());
            txtComplaintDescription.setText(complaint.getDescription());
            txtTime.setText("Time Captured : "+ complaint.getTime());
            txtDate.setText("Date Captured : " + complaint.getDate())  ;
            txtAddress.setText(complaint.getLocation());
            lat = complaint.getLatitude();
            lon = complaint.getLongitude();

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("latitude", lat);
                b.putString("longitude", lon);
                MapsActivity.startIntent(ScrollingActivity.this, b);

            }
        });


    }

    public void setActionBar(String heading) {
        // TODO Auto-generated method stub

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(heading);
        actionBar.show();

    }



}
