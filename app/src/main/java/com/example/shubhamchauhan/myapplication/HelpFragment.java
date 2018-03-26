package com.example.shubhamchauhan.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

    ArrayAdapter<String> arrayAdapter;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_help, container, false);
        ListView lstHelp = (ListView)view.findViewById(R.id.helpLst);

        ArrayList<String> helplineList = new ArrayList<String>();
        helplineList.add("City Service 311 : Non-Emergency City Services");
        helplineList.add("408-HelpLine(416-408-4357) : Suicuide, Family Voience, Distree Situation");
        helplineList.add("Emergency 911 : Fire, Water, Paramedics & Crime Emergency");
        helplineList.add("Ontario 211 : Ontario Social Service");

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,helplineList);
        lstHelp.setAdapter(arrayAdapter);


        return view;
    }

}
