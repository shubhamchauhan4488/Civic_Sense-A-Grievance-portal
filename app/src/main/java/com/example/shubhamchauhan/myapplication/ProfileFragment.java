package com.example.shubhamchauhan.myapplication;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubhamchauhan.myapplication.Database.MyDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    final private String DATABASE_NAME = "MyDatabase6";
    private MyDatabase database;
    SharedPreferences sharedPreferences;
    public ProfileFragment() {
        // Required empty public constructor
    }

    TextView txtName;
    TextView txtEmail;
    TextView txtPhoneNo;
    TextView txtAddress;
    TextView txtAboutMe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        savedInstanceState  = getArguments();


        txtName = (TextView)view.findViewById(R.id.txtName);
        txtEmail = (TextView)view.findViewById(R.id.txtEmail);
        txtPhoneNo = (TextView)view.findViewById(R.id.txtPhoneNo);
        txtAddress = (TextView)view.findViewById(R.id.txtAddress);
        txtAboutMe = (TextView)view.findViewById(R.id.txtAboutMe);


//        database = Room.databaseBuilder(getActivity(), MyDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
//        String emailToLook = sharedPreferences.getString("userEmail", null);
//
//        Log.d("ProfiileEmailCheck", savedInstanceState.getString("email"));
        if (savedInstanceState != null) {

//            Login login = database.loginDao().findByEmail(emailToLook);

            txtName.setText(savedInstanceState.getString("name"));
            txtEmail.setText(savedInstanceState.getString("email"));
            txtPhoneNo.setText(String.valueOf(savedInstanceState.getLong("phoneNo")));
            txtAddress.setText(savedInstanceState.getString("address"));
            txtAboutMe.setText(savedInstanceState.getString("aboutMe"));
        }else
        {
            Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
        }

        return view ;

    }

}
