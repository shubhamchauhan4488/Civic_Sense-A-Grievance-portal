package com.example.shubhamchauhan.myapplication;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.shubhamchauhan.myapplication.Database.MyDatabase;
import com.example.shubhamchauhan.myapplication.Models.Complaint;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission_group.CAMERA;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateComplaintFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CreateComplaintFragment extends Fragment {


    static final int REQUEST_IMAGE_CAPTURE = 1;

    static final int REQUEST_TAKE_PHOTO = 1;
    private int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private int PERMISSION_REQUEST_CODE = 1;
    private int DialogId = 0;
    final private String DATABASE_NAME = "MyDatabase6";
    final int REQUEST_CODE_PICKERPLACE = 2;


    private int month_x;
    private int day_x;
    private int year_x;
    private String lat;
    private String lon;
    MyDatabase database;
    Bitmap thumbnail;
    Uri uriPhoto;
    String fullPath;

    ImageButton attachBtn;
    Button submitBtn;
    Spinner spinner;
    EditText edtTitle;
    EditText edtDescription;
    EditText edtDate;
    EditText edtTime;
    EditText edtLocation;
    ImageButton locationBtn;
    CheckBox aknCheckBox;
    ImageView imageview;

    int complaintID;
    String category;
    String title;
    String description;
    String date;
    String time;
    byte[] image;
    String location;
    Boolean acknowledgeBool;
    String mCurrentPhotoPath;



    DatePickerDialog datepickerDialog;
    TimePickerDialog timePickerDialog;
    SimpleDateFormat dateFormatter;

//    public static void startIntent(Context context) {
//        context.startActivity(new Intent(context, getActivity.class));
//    }
//
//    public static void startIntent(Context context, Bundle bundle) {
//        Intent i = new Intent(context, getActivity());
//        i.putExtras(bundle);
//        context.startActivity(i);
//
//    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String ARG_PARAM1 = "param1";
    static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    String mParam1;
    String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateComplaintFragment newInstance(String param1, String param2) {
        CreateComplaintFragment fragment = new CreateComplaintFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_create_complaint, container, false);
        submitBtn = (Button) view.findViewById(R.id.submitBtn);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        attachBtn = (ImageButton) view.findViewById(R.id.attachBtn);
        edtTitle = (EditText) view.findViewById(R.id.edtTitle);
        edtDescription = (EditText) view.findViewById(R.id.edtDesciption);
        edtDate = (EditText) view.findViewById(R.id.edtDate);
        edtTime = (EditText) view.findViewById(R.id.edtTime);
        edtLocation = (EditText) view.findViewById(R.id.edtLocation);
        locationBtn = (ImageButton) view.findViewById(R.id.locationBtn);
        aknCheckBox = (CheckBox) view.findViewById(R.id.aknCheckbox);
        imageview = (ImageView)view.findViewById(R.id.imageView) ;


        //Adding spinner
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("Senatory");
        categoryList.add("Unauthorised Parking");
        categoryList.add("Theft");
        categoryList.add("Fire");
        categoryList.add("Road Maintenance");
        categoryList.add("Unauthorised Construction");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, categoryList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForPermission(Manifest.permission.CAMERA, PERMISSION_REQUEST_CODE);
            }
        });


        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission(ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE);

            }
        });


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        edtDate.setText(mDay + "/"
                + (mMonth + 1) + "/" + mYear);
        //ENTERING THE DATE
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // date picker dialog
                datepickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                edtDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datepickerDialog.show();
            }
        });


        int hour_x = c.get(Calendar.HOUR_OF_DAY);//Current Hour
        int min_x = c.get(Calendar.MINUTE);//Current Min
        edtTime.setText(hour_x + ":" + min_x);
        //ENTERING THE TIME
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour_x = c.get(Calendar.HOUR_OF_DAY);//Current Hour
                int min_x = c.get(Calendar.MINUTE);//Current Min
                //timePicker Dialog
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        edtTime.setText(i + ":" + i1);
                    }
                }, hour_x, min_x, false);
                timePickerDialog.show();

            }
        });

        FloatingActionButton floatingAddBtn = this.getActivity().findViewById(R.id.fab);
        floatingAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = Room.databaseBuilder(getActivity(), MyDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

                category = spinner.getSelectedItem().toString();
                title = edtTitle.getText().toString();//Title
                description = edtDescription.getText().toString();//Description
                date = edtDate.getText().toString();//Date
                time = edtTime.getText().toString();//Time


                BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageview.getDrawable());
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                byte[] byteArray = stream.toByteArray();
                image = byteArray;//Image

                location = edtLocation.getText().toString();//Location

                acknowledgeBool = aknCheckBox.isChecked();

                Random rand = new Random();

                int n = rand.nextInt(50) + 2;
                Complaint complaint = new Complaint(n, category, title, date, time, description, image, location, lat, lon, acknowledgeBool);
                if (acknowledgeBool == false) {

                    Snackbar.make(view, "Check the acknowledge checkbox in order to proceed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                if (category.isEmpty()||title.isEmpty()||date.isEmpty()||time.isEmpty()||description.isEmpty()||location.isEmpty())
                {
                    Snackbar.make(view, "All fields are Mandatory", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else
                {
                    database.complaintDAO().insert(complaint);
                    Snackbar.make(view, "Your complaint has been registered with us", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


//                LoginActivity.startIntent(CreateComplaintActivity.this);

            }
        });


        return view;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void selectedPlaceFromPlacePicker(Intent data) {
        Place place = PlacePicker.getPlace(getActivity(), data);

        String address = place.getAddress().toString();
        LatLng latlgn = place.getLatLng();
        lat = String.valueOf(latlgn.latitude);
        lon = String.valueOf(latlgn.longitude);

        edtLocation.setText(address);
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                uriPhoto = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                requestPermissions(new String[]{permission}, requestCode);


        }

            else {

                if (requestCode == 1) {

                    dispatchTakePictureIntent();
                }
                if (requestCode == 2) {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    try {
                        Intent intent = intentBuilder.build(getActivity());
                        startActivityForResult(intent, REQUEST_CODE_PICKERPLACE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                //Location

                case 1:
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        dispatchTakePictureIntent();
                    }else
                    {
                        Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    try {
                        Intent intent = intentBuilder.build(getActivity());
                        startActivityForResult(intent, REQUEST_CODE_PICKERPLACE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }

            Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            imageview.setImageURI(uriPhoto);}
        if (requestCode == REQUEST_CODE_PICKERPLACE && resultCode == RESULT_OK) {
            selectedPlaceFromPlacePicker(data);
        }
    }


    // Adding the image from the Camera

//    private void askForPermission(String permission, Integer requestCode) {
//        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
//                //This is called if user has denied the permission before
//                //In this case I am just asking the permission again
//                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
//            } else {
//
//                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
//
//            }
//        } else {
//
//            if (requestCode == 2) {
//                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
//                try {
//                    Intent intent = intentBuilder.build(getActivity());
//                    startActivityForResult(intent, REQUEST_CODE_PICKERPLACE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//              }
//            }
//        }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
//            switch (requestCode) {
//                //Location
//
//                case 2:
//                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
//                    try {
//                        Intent intent = intentBuilder.build(getActivity());
//                        startActivityForResult(intent, REQUEST_CODE_PICKERPLACE);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//            }
//            Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
//        }
//
//    }









}
