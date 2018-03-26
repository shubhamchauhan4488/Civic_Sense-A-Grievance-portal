package com.example.shubhamchauhan.myapplication;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shubhamchauhan.myapplication.Adapter.MyComplaintAdapter;
import com.example.shubhamchauhan.myapplication.Database.MyDatabase;
import com.example.shubhamchauhan.myapplication.Models.Complaint;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyComplaintsFragment extends Fragment implements CreateComplaintFragment.OnFragmentInteractionListener {

    private ListView lstMyComplaints;
    private MyComplaintAdapter dataAdapter;
    final private String DATABASE_NAME = "MyDatabase6";
    private MyDatabase database;
    RecyclerView myRecyclerView;
    ArrayList<Complaint> complaintList ;

    public MyComplaintsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Registered Complaints");

        View view = inflater.inflate(R.layout.fragment_my_complaints, container, false);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewComplaints);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.setLayoutManager(linearLayoutManager);

        database = Room.databaseBuilder(getActivity(), MyDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        complaintList = (ArrayList)database.complaintDAO().get();
        dataAdapter = new MyComplaintAdapter(complaintList);
        myRecyclerView.setAdapter(dataAdapter);




        Log.d("log4", "onCreateView: ");
//        lstMyComplaints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Complaint complaint = complaintList.get(i);
////                Intent intent  = new Intent(getActivity(), ScrollingActivity.class);
////                intent.putExtra("title", complaint.getTitle());
////                startActivity(intent);
//
    return  view;

    }

//    @Override
//    public void onPause() {
//
//        super.onPause();
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
