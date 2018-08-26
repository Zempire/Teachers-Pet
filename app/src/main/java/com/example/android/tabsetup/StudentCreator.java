package com.example.android.tabsetup;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.R.layout.simple_spinner_dropdown_item;

public class StudentCreator extends AppCompatActivity {

    TextView address;
    LinearLayout addressExpanded;
    Spinner spinner;

    String[] states = new String[]{
            "State", "NSW", "VIC", "QLD", "NT", "WA", "SA", "TAS", "ACT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        spinner = findViewById(R.id.stateSpinner);
        addItemsToSpinner(spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addItemsToSpinner(Spinner spinner) {
        List<String> stateList = new ArrayList<>(Arrays.asList(states));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, stateList);
        dataAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }
}
