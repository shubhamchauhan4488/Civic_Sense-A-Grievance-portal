package com.example.shubhamchauhan.myapplication;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shubhamchauhan.myapplication.Database.MyDatabase;
import com.example.shubhamchauhan.myapplication.Models.Login;

import java.util.Random;

public class SignUpActivity extends AppCompatActivity {

    EditText edtName;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPass;
    EditText edtAddress;
    EditText edtPhoneNo;
    EditText edtAbout;
    Button signUpBtn;

    String name;
    String email;
    String password;
    String confirmPass;
    String address;
    int phoneNo;
    String aboutMe;


    private static final String DATABASE_NAME = "MyDatabase4";
    private MyDatabase database;

    public static void startIntent(Context context) {
        context.startActivity(new Intent(context, SignUpActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSignUp);
        setSupportActionBar(toolbar);
        setActionBar("Register Yourself");

        edtName = (EditText) findViewById(R.id.txtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPass);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPhoneNo = (EditText) findViewById(R.id.edtPhoneNo);
        edtAbout = (EditText) findViewById(R.id.edtAboutMe);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        //For maintainnig the session

        database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    public void signup() {

            final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                    R.style.Theme_AppCompat_DayNight_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
            name = edtName.getText().toString();
            email = edtEmail.getText().toString();
            password = edtPassword.getText().toString();
            confirmPass = edtConfirmPass.getText().toString();
            address = edtAddress.getText().toString();
            phoneNo = Integer.parseInt(edtPhoneNo.getText().toString());
            aboutMe = edtAbout.getText().toString();


            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onSignupSuccess or onSignupFailed
                            // depending on success
                            if (!validate()) {
                                onSignupFailed();
                            }else {
                                onSignupSuccess();
                            }
                            // onSignupFailed();
                            progressDialog.dismiss();
                        }
                    }, 2000);

        }


    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign Up Failed. Correct the erros above!", Toast.LENGTH_LONG).show();

    }

    public void onSignupSuccess() {

        setResult(RESULT_OK, null);

        Random rand = new Random();
        int n = rand.nextInt(500) + 2;
        Login login = new Login(n, name, email, password, address, phoneNo, aboutMe);

        database.loginDao().insert(login);
        Toast.makeText(this, "You Have successfully registered with us. Login Now!", Toast.LENGTH_SHORT).show();
        LoginActivity.startIntent(SignUpActivity.this);
        finish();
    }

    public boolean validate() {
        boolean valid = true;

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String aboutMe = edtAbout.getText().toString();
        String confirmPass = edtConfirmPass.getText().toString();
        String address = edtAddress.getText().toString();
        String name = edtName.getText().toString();
        int phoneNo = Integer.parseInt(edtPhoneNo.getText().toString());

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("enter a valid email address");
            valid = false;
        } else {
            edtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        if (aboutMe.length() < 10) {
            edtAbout.setError("Minimum 10 words");
            valid = false;
        } else {
            edtAbout.setError(null);
        }
        if (!confirmPass.equals(password)) {
            edtConfirmPass.setError("Passwords Don't match");
            valid = false;
        } else {
            edtConfirmPass.setError(null);
        }
        if(email.isEmpty() || name.isEmpty()||phoneNo == 0|| aboutMe.isEmpty()||address.isEmpty())
        {
            Toast.makeText(this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
        }
        return valid;
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
