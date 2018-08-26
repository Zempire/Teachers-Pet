package com.example.android.tabsetup;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StudentCreator extends AppCompatActivity {

    TextView address;
    LinearLayout addressExpanded;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        address = findViewById(R.id.addressHeader);
        addressExpanded = findViewById(R.id.addressLayout);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressExpanded.getVisibility() == view.VISIBLE)
                    addressExpanded.setVisibility(View.GONE);
                else
                    addressExpanded.setVisibility(view.VISIBLE);
            }
        });
    }
}
