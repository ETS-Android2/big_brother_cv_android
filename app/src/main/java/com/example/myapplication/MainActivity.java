package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtName, txtEmail, txtPassword, txtRePassword;
    private CheckBox chkbox1, chkbox2, chkbox3, chkbox4, chkbox5, chkbox6, chkbox7, chkbox8, chkbox9;
    private RadioButton radioMale, radioFemale, radioOther;
    private Button btnPower, btnReport, btnCamera;
    private Spinner notifierSpinner;
    private ImageView image;
    private RelativeLayout mainLayout;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.chkbox1 = findViewById(R.id.chkbox1);
        this.chkbox2 = findViewById(R.id.chkbox2);
        this.chkbox3 = findViewById(R.id.chkbox3);
        this.chkbox4 = findViewById(R.id.chkbox4);
        this.chkbox5 = findViewById(R.id.chkbox5);
        this.chkbox6 = findViewById(R.id.chkbox6);
        this.chkbox7 = findViewById(R.id.chkbox7);
        this.chkbox8 = findViewById(R.id.chkbox8);
        this.chkbox9 = findViewById(R.id.chkbox9);
        chkbox1.setVisibility(View.INVISIBLE);
        chkbox2.setVisibility(View.INVISIBLE);
        chkbox3.setVisibility(View.INVISIBLE);
        chkbox4.setVisibility(View.INVISIBLE);
        chkbox5.setVisibility(View.INVISIBLE);
        chkbox6.setVisibility(View.INVISIBLE);
        chkbox7.setVisibility(View.INVISIBLE);
        chkbox8.setVisibility(View.INVISIBLE);
        chkbox9.setVisibility(View.INVISIBLE);

//        this.radioMale = findViewById(R.id.radioMale);
//        this.radioFemale = findViewById(R.id.radioFemale);
//        this.radioOther = findViewById(R.id.radioOther);
        this.btnPower = findViewById(R.id.btnPower);
        this.btnReport = findViewById(R.id.btnReport);
        this.btnCamera = findViewById(R.id.btnCamera);
        this.notifierSpinner = findViewById(R.id.notifierSpinner);
        this.image = findViewById(R.id.image);
        this.mainLayout = findViewById(R.id.mainLayout);
        this.db = FirebaseDatabase.getInstance().getReference();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("newone");

//        System.out.println("START");

//        myRef.setValue("3");
        myRef.setValue("7");

        System.out.println("END");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference();
//        myRef.child("big-brother-cv-default-rtdb").child("power").setValue(19);
//        db.child("big-brother-cv-default-rtdb").child("power").push().setValue(20);
//        myRef.child("big-brother-cv-default-rtdb").child("power").setValue("21");
//        db.child("big-brother-cv-default-rtdb").child("power").push().setValue("22");

//                // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ProcessBuilder pb = new ProcessBuilder("E:\\Everything\\Python\\big_brother_cv\\main.py");
//                try {
//                    pb.start();
//                } catch (Exception e) {
//                    System.out.println(e.toString());
//                }
//                if (txtName.getText().toString().equals("")) {
//                    Toast.makeText(MainActivity.this, "Missing Name", Toast.LENGTH_SHORT).show();
//                } else if (txtEmail.getText().toString().equals("")) {
//                    Toast.makeText(MainActivity.this, "Missing Email", Toast.LENGTH_SHORT).show();
//                } else if (!txtEmail.getText().toString().contains("@")) {
//                    Toast.makeText(MainActivity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
//                } else if (txtPassword.getText().toString().equals("")) {
//                    Toast.makeText(MainActivity.this, "Missing Password", Toast.LENGTH_SHORT).show();
//                } else if (txtPassword.getText().toString().length() < 8 || txtPassword.getText().toString().length() > 12) {
//                    Toast.makeText(MainActivity.this, "Password should contain between 8 to 12 characters", Toast.LENGTH_SHORT).show();
//                } else if (!txtRePassword.getText().toString().equals(txtPassword.getText().toString())) {
//                    Toast.makeText(MainActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
//                } else if (!chkboxAgree.isChecked()) {
//                    Toast.makeText(MainActivity.this, "You have to agree to the terms", Toast.LENGTH_SHORT).show();
//                } else {
//                    //Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(mainLayout, "Registered Successfully!", Snackbar.LENGTH_INDEFINITE)
//                            .setAction("Dismiss", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//
//                                }
//                            })
//                            .show();
//                }
//            }
//        });


//

        // Power:
        btnPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, btnPower.getText().toString(), Toast.LENGTH_SHORT).show();
                if (btnPower.getText().toString().equals("Off")) {
                    btnPower.setText(R.string.on);
                    btnPower.setBackgroundColor(getResources().getColor(R.color.green));
                    // Write a message to the database
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference();
//                    myRef.child("power").setValue(19);
                    db.child("power").setValue(1);
                } else if (btnPower.getText().toString().equals("On")) {
                    btnPower.setText(R.string.off);
                    btnPower.setBackgroundColor(getResources().getColor(R.color.red));
                    // Write a message to the database
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference("big-brother-cv-default-rtdb");
//                    myRef.child("power").setValue(27);
                    db.child("power").setValue(0);
                }
            }
        });

        // Spinner:
        ArrayList<String> notifierList = new ArrayList<>();
        notifierList.add("Choose");
        notifierList.add("Foods");
        notifierList.add("Danger");

        ArrayAdapter<String> notifierAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                notifierList
        );

        ArrayList<CheckBox> chkboxList = new ArrayList<>();
        chkboxList.add(chkbox1);
        chkboxList.add(chkbox2);
        chkboxList.add(chkbox3);
        chkboxList.add(chkbox4);
        chkboxList.add(chkbox5);
        chkboxList.add(chkbox6);
        chkboxList.add(chkbox7);
        chkboxList.add(chkbox8);
        chkboxList.add(chkbox9);

        DatabaseReference catPath = db.child("data").child("categories");

        // Selecting a category to notify in the Spinner:
        notifierSpinner.setAdapter(notifierAdapter);
        notifierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InputStream is = null;
                DatabaseReference path = null;
                switch (notifierList.get(i)) {
                    case "Foods":
                        is = MainActivity.this.getResources().openRawResource(R.raw.food);
                        path = catPath.child("food");
                        break;
                    case "Danger":
                        is = MainActivity.this.getResources().openRawResource(R.raw.danger);
                        path = catPath.child("danger");
                        break;
                    default:
                        return;
                }
                System.out.println("ERROR1");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                ArrayList<String> strList = new ArrayList<>();
                try {
                    while ((line = br.readLine()) != null) {
                        strList.add(line);
                    }
                    int j;
                    System.out.println("ERROR2");
                    for (j = 0; j < strList.size(); j++) {
                        chkboxList.get(j).setVisibility(View.VISIBLE);
                        chkboxList.get(j).setText(strList.get(j));
                        chkboxList.get(j).setChecked(path.child(strList.get(j)).get().toString().equals("1")); // Ask Firebase what is already checked.
                    }
                    System.out.println("ERROR3");
                    while (j < chkboxList.size()) {
                        chkboxList.get(j).setVisibility(View.INVISIBLE);
                        j++;
                    }
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Check notifiers in Firebase:
        CompoundButton.OnCheckedChangeListener chkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseReference cat = catPath.child(notifierSpinner.getSelectedItem().toString());
                if (b) {
                    cat.setValue(0);
                    System.out.println("1");
                } else {
                    cat.setValue(1);
                    System.out.println("0");
                }
            }
        };
        chkbox1.setOnCheckedChangeListener(chkListener);
        chkbox2.setOnCheckedChangeListener(chkListener);
        chkbox3.setOnCheckedChangeListener(chkListener);
        chkbox4.setOnCheckedChangeListener(chkListener);
        chkbox5.setOnCheckedChangeListener(chkListener);
        chkbox6.setOnCheckedChangeListener(chkListener);
        chkbox7.setOnCheckedChangeListener(chkListener);
        chkbox8.setOnCheckedChangeListener(chkListener);
        chkbox9.setOnCheckedChangeListener(chkListener);


        // Produce report:
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference path = db.child("data").child("forms");
                Toast.makeText(MainActivity.this, "Form sent to Email", Toast.LENGTH_SHORT).show();
            }
        });

    }
}