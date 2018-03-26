package com.example.shubhamchauhan.myapplication.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.shubhamchauhan.myapplication.Models.Complaint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubhamchauhan on 03/12/17.
 */

@Dao
public interface ComplaintDAO {

    @Query("SELECT * FROM complaint")
    public List<Complaint> get();

    @Query("SELECT * FROM complaint WHERE title LIKE :title LIMIT 1")
    public Complaint findByTitle(String title);

    @Insert
    public void insert(Complaint complaint);

    @Insert
    public void insertAll(ArrayList<Complaint> complaints);

    @Update
    public void update(Complaint complaint);

    @Delete
    public void delete(Complaint complaint);


}
