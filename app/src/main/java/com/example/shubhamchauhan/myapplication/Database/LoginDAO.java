package com.example.shubhamchauhan.myapplication.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.shubhamchauhan.myapplication.Models.Complaint;
import com.example.shubhamchauhan.myapplication.Models.Login;

import java.util.List;

/**
 * Created by shubhamchauhan on 03/12/17.
 */
@Dao
public interface LoginDAO {

    @Query("SELECT * FROM login")
    public List<Login> get();

    @Query("SELECT * FROM login WHERE email LIKE :email LIMIT 1")
    public Login findByEmail(String email);

    @Query("SELECT * FROM login WHERE email LIKE :email AND password LIKE :password LIMIT 1")
    public Login findByEmailAndPassword(String email, String password);

    @Insert
    public void insert(Login login);

}
