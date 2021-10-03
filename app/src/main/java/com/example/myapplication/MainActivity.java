package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private CheckBox chkbox1, chkbox2, chkbox3, chkbox4, chkbox5, chkbox6, chkbox7, chkbox8, chkbox9;
    private Button btnPower, btnReport, btnLive, btnName;
    private Spinner notifierSpinner;
    private String temp = "";
    private TextView txtRecent;
    private EditText edtTxtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chkbox1 = findViewById(R.id.chkbox1);
        chkbox2 = findViewById(R.id.chkbox2);
        chkbox3 = findViewById(R.id.chkbox3);
        chkbox4 = findViewById(R.id.chkbox4);
        chkbox5 = findViewById(R.id.chkbox5);
        chkbox6 = findViewById(R.id.chkbox6);
        chkbox7 = findViewById(R.id.chkbox7);
        chkbox8 = findViewById(R.id.chkbox8);
        chkbox9 = findViewById(R.id.chkbox9);
        chkbox1.setVisibility(View.INVISIBLE);
        chkbox2.setVisibility(View.INVISIBLE);
        chkbox3.setVisibility(View.INVISIBLE);
        chkbox4.setVisibility(View.INVISIBLE);
        chkbox5.setVisibility(View.INVISIBLE);
        chkbox6.setVisibility(View.INVISIBLE);
        chkbox7.setVisibility(View.INVISIBLE);
        chkbox8.setVisibility(View.INVISIBLE);
        chkbox9.setVisibility(View.INVISIBLE);
        btnPower = findViewById(R.id.btnPower);
        btnReport = findViewById(R.id.btnReport);
        btnLive = findViewById(R.id.btnLive);
        btnName = findViewById(R.id.btnName);
        notifierSpinner = findViewById(R.id.notifierSpinner);
        txtRecent = findViewById(R.id.txtRecentFire);
        edtTxtName = findViewById(R.id.edtTxtName);

        // Reference to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference powerRef = database.getReference("power");

        // Spinner:
        ArrayList<String> notifierList = new ArrayList<>();
        notifierList.add("Choose");
        notifierList.add("food");
        notifierList.add("danger");

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


        // Initialize list
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < chkboxList.size(); i++) {
            list.add("0");
        }

        // Power
        View.OnClickListener powerClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnPower.getText().toString().equals("Off")) {
                    btnPower.setText(R.string.on);
                    Toast.makeText(MainActivity.this, btnPower.getText().toString(), Toast.LENGTH_SHORT).show();
                    btnPower.setBackgroundColor(getResources().getColor(R.color.green));
                    powerRef.setValue(1);
                } else if (btnPower.getText().toString().equals("On")) {
                    btnPower.setText(R.string.off);
                    Toast.makeText(MainActivity.this, btnPower.getText().toString(), Toast.LENGTH_SHORT).show();
                    btnPower.setBackgroundColor(getResources().getColor(R.color.red));
                    powerRef.setValue(0);
                    txtRecent.setText("");
                }
            }
        };
        btnPower.setOnClickListener(powerClick);

        // Changing name:
        DatabaseReference nameRef = database.getReference("name");
        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameRef.setValue(edtTxtName.getText().toString());
            }
        });

        // Selecting a category to notify in the Spinner:
        notifierSpinner.setAdapter(notifierAdapter);
        notifierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InputStream is = null;
                DatabaseReference path = null;
                switch (notifierList.get(i)) {
                    case "food":
                        is = MainActivity.this.getResources().openRawResource(R.raw.food);
                        path = database.getReference("data").child("categories").child("danger");
                        break;
                    case "danger":
                        is = MainActivity.this.getResources().openRawResource(R.raw.danger);
                        path = database.getReference("data").child("categories").child("food");
                        break;
                    default:
                        return;
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                ArrayList<String> strList = new ArrayList<>();
                try {
                    while ((line = br.readLine()) != null) {
                        strList.add(line);
                    }
                    int j;
                    path.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            for (DataSnapshot child: snapshot.getChildren()) {
                                if (!child.getKey().equals("change")) {
                                    list.add(child.getValue().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    for (j = 0; j < strList.size(); j++) {
                        chkboxList.get(j).setVisibility(View.VISIBLE);
                        chkboxList.get(j).setText(strList.get(j));
                        // Ask Firebase what is already checked.
                        chkboxList.get(j).setChecked(list.get(j).equals("1"));
                    }
                    while (j < chkboxList.size()) {
                        chkboxList.get(j).setVisibility(View.INVISIBLE);
                        j++;
                    }
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                    System.out.println(e.toString());
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
                DatabaseReference nameRef = database.getReference("data").child("categories").child(notifierSpinner.getSelectedItem().toString()).child(compoundButton.getText().toString());
                database.getReference("data").child("categories").child("change").setValue(1);
                if (b) {
                    nameRef.setValue(1);
                } else {
                    nameRef.setValue(0);
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
                Toast.makeText(MainActivity.this, "Loading form, please wait", Toast.LENGTH_SHORT).show();
                database.getReference("produce report").setValue(1);
                Intent intent = new Intent(MainActivity.this, FirstFragment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DatabaseReference moveToForm = database.getReference("produce report");
                moveToForm.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue().toString().equals("0")) {
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Didn't work :(");
                    }
                });
            }
        });

        // Live updates:
        btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Live updates began", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SecondFragment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        // Recent connections:
        DatabaseReference recentRef = database.getReference("data").child("live update");
        Query recentQuery = recentRef.orderByKey().limitToLast(3);
        recentQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sit: snapshot.getChildren()) {
                    if (!sit.getValue().toString().equals("pass")) {
                        temp = temp + sit.getValue().toString() + "\n";
                    }
                }
                txtRecent.setText(temp);
                temp = "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Didn't work :(");
            }
        });
        txtRecent.setText("");
    }
}