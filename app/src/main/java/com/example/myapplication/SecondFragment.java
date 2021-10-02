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
import com.google.firebase.database.ValueEventListener;


public class SecondFragment extends AppCompatActivity {

    private TextView txtLive;
    private String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);

        txtLive = findViewById(R.id.txtLiveFire);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference liveRef = database.getReference("data").child("live update");

        // Report situations:
        temp = "";
        liveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sit: snapshot.getChildren()) {
                    if (!sit.getValue().toString().equals("pass")) {
                        temp = temp + sit.getValue().toString() + "\n";
                    }
                }
                txtLive.setText(temp);
                temp = "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Didn't work :(");
            }
        });
    }
}