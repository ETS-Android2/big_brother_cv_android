package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FirstFragment extends AppCompatActivity {

    private TextView txtTime, txtImportant, txtSituations, txtInTotal, txtRecent;
    private String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);

        txtTime = findViewById(R.id.txtTimeFire);
        txtImportant = findViewById(R.id.txtImportantFire);
        txtSituations = findViewById(R.id.txtSituationsFire);
        txtInTotal = findViewById(R.id.txtInTotalFire);
        txtRecent = findViewById(R.id.txtRecentFire);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference formsRef = database.getReference("data").child("forms");

        // Report time:
        DatabaseReference timeRef = formsRef.child("report time");
        timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtTime.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Didn't work :(");
            }
        });

        // Report important events:
        DatabaseReference importantRef = formsRef.child("important events");
        importantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sit: snapshot.getChildren()) {
                    if (!sit.getValue().toString().equals("pass")) {
                        temp = temp + sit.getValue().toString() + "\n";
                    }
                }
                txtImportant.setText(temp);
                temp = "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Didn't work :(");
            }
        });

        // Report situations:
        DatabaseReference situationsRef = formsRef.child("situations");
        situationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sit: snapshot.getChildren()) {
                    if (!sit.getValue().toString().equals("pass")) {
                        temp = temp + sit.getValue().toString() + "\n\n";
                    }
                }
                txtSituations.setText(temp);
                temp = "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Didn't work :(");
            }
        });

        // Report in total:
        DatabaseReference inTotalRef = formsRef.child("in total");
        inTotalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sit: snapshot.getChildren()) {
                    if (!sit.getValue().toString().equals("pass") && !sit.getValue().toString().contains("entered")) {
                        temp = temp + sit.getValue().toString() + "\n\n";
                    }
                }
                txtInTotal.setText(temp);
                temp = "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Didn't work :(");
            }
        });
    }
}