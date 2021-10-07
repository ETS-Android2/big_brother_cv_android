package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThirdFragment extends AppCompatActivity {

    private Button btnChange;
    private EditText edtTxtName, edtTxtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_third);

        btnChange = findViewById(R.id.btnChange);
        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);

        // Changing name or email:
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nameRef = database.getReference("name");
        DatabaseReference emailRef = database.getReference("email");
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameRef.setValue(edtTxtName.getText().toString());
                emailRef.setValue(edtTxtEmail.getText().toString());
                Toast.makeText(ThirdFragment.this, "Name or Email changed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
