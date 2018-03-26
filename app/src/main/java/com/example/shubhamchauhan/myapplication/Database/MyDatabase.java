package com.example.shubhamchauhan.myapplication.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.shubhamchauhan.myapplication.Models.Complaint;
import com.example.shubhamchauhan.myapplication.Models.Login;

/**
 * Created by shubhamchauhan on 03/12/17.
 */


@Database(entities = {Complaint.class, Login.class}, version = 2)
//@TypeConverters({DateTypeConverter.class, TimeTypeConverter.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract ComplaintDAO complaintDAO();

    public abstract LoginDAO loginDao();

}



