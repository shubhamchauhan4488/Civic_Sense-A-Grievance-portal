package com.example.shubhamchauhan.myapplication;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubhamchauhan.myapplication.Database.MyDatabase;
import com.example.shubhamchauhan.myapplication.Models.Login;

//import butterknife.ButterKnife;
//import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "MyDatabase4";

    TextView textView;
    EditText edtEmail;
    EditText edtPassword;
    CheckBox checkBox;
    Button loginButton;
    TextView txtSignUp;
    ImageView imageView;
    TextInputLayout inputLayoutEmail;
    TextInputLayout inputLayoutPassword;
    private MyDatabase database;
    private SharedPreferences sharedPreferences;
    Login login;
    private static final int REQUEST_SIGNUP = 0;


    public static void startIntent(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void startIntent(Context context, Bundle bundle) {
        Intent i = new Intent(context, LoginActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);

    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        setActionBar("Login");

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        loginButton = (Button) findViewById(R.id.loginButton);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);

        database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.startIntent(LoginActivity.this);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                login();

            }
//
        });
    }


    public boolean validate() {
        boolean valid = true;

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            edtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtPassword.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        return valid;
    }


    public void login() {

        login = database.loginDao().findByEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString());

        if (!validate()) {
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed

                        if (login == null) {
                            onLoginFailed();
                        } else {
                            onLoginSuccess();
                        }
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 2500);
    }


    public void onLoginSuccess() {


        if (checkBox.isChecked()) {
            SharedPreferences.Editor mEditor = sharedPreferences.edit();
            mEditor.putString("userEmail", edtEmail.getText().toString());
            mEditor.putString("userPassword", edtPassword.getText().toString());
            mEditor.apply();
            Bundle b = new Bundle();
            b.putString("name", login.getName());
//                        Log.d("LoginCheck",login.getName());
            b.putString("email", login.getEmail());
            b.putString("address", login.getAddress());
            b.putString("aboutMe", login.getAboutMe());
            b.putLong("phoneNo", login.getPhoneNo());
            MainActivity.startIntent(LoginActivity.this, b);
        } else {
            SharedPreferences.Editor mEditor = sharedPreferences.edit();
            mEditor.remove("userEmail");
            mEditor.remove("userPassword");
            mEditor.apply();
            Bundle b = new Bundle();
            b.putString("name", login.getName());
            b.putString("email", login.getEmail());
            b.putString("address", login.getAddress());
            b.putString("aboutMe", login.getAboutMe());
            b.putLong("phoneNo", login.getPhoneNo());
            MainActivity.startIntent(LoginActivity.this, b);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                Toast.makeText(this, "You have successfully Registered", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this, "Looks like You Are a New User.Sign Up!", Toast.LENGTH_SHORT).show();
        loginButton.setEnabled(true);

    }

    public void setActionBar(String heading) {
        // TODO Auto-generated method stub

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(heading);
        actionBar.show();

    }

    //Fetching the values from the shared preferences if "remember Me" was checked
    @Override
    protected void onResume() {
        sharedPreferences = getSharedPreferences("Plogin", MODE_PRIVATE);
        setSavedDetails();
        super.onResume();
    }

    private void setSavedDetails() {
        String email = sharedPreferences.getString("userEmail", null);
        String pwd = sharedPreferences.getString("userPassword", null);

        if (email != null && pwd != null) {
            edtEmail.setText(email);
            edtPassword.setText(pwd);
            checkBox.setChecked(true);
        }
    }

}

