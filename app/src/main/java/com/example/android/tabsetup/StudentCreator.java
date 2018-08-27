package com.example.android.tabsetup;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static android.R.layout.simple_spinner_dropdown_item;

public class StudentCreator extends AppCompatActivity {

    TextView address;
    AutoCompleteTextView stud_state, stud_course;
    LinearLayout addressExpanded;
    Spinner spinner;

    final String[] STATES = new String[]{
            "State", "NSW", "VIC", "QLD", "NT", "WA", "SA", "TAS", "ACT"};

    final String[] COURSES = new String[] {"Bachelor of Architecture",
            "Bachelor of Biomedical Science",
            "Bachelor of Business Administration",
            "Bachelor of Clinical Science",
            "Bachelor of Commerce",
            "Bachelor of Computer Applications",
            "Bachelor of Community Health",
            "Bachelor of Computer Information Systems",
            "Bachelor of Science in Construction Technology",
            "Bachelor of Criminal Justice",
            "Bachelor of Divinity",
            "Bachelor of Economics",
            "Bachelor of Education",
            "Bachelor of Engineering",
            "Bachelor of Fine Arts",
            "Bachelor of Letters",
            "Bachelor of Information Systems",
            "Bachelor of Management",
            "Bachelor of Music",
            "Bachelor of Pharmacy",
            "Bachelor of Philosophy",
            "Bachelor of Social Work",
            "Bachelor of Technology",
            "Bachelor of Accountancy",
            "Bachelor of Arts in American Studies",
            "Bachelor of Arts in American Indian Studies",
            "Bachelor of Arts in Applied Psychology",
            "Bachelor of Arts in Biology",
            "Bachelor of Arts in Anthropology",
            "Bachelor of Arts in Child Advocacy",
            "Bachelor of Arts in Clinical Psychology",
            "Bachelor of Arts in Forensic Psychology",
            "Bachelor of Arts in Organizational Psychology",
            "Bachelor of Science in Aerospace Engineering",
            "Bachelor of Science in Actuarial",
            "Bachelor of Science in Agriculture",
            "Bachelor of Science in Architecture",
            "Bachelor of Science in Architectural Engineering",
            "Bachelor of Science in Athletic Training",
            "Bachelor of Science in Biology",
            "Bachelor of Science in Biomedical Engineering",
            "Bachelor of Science in Bible",
            "Bachelor of Science in Business Administration",
            "Bachelor of Science in Business and Technology",
            "Bachelor of Science in Chemical Engineering",
            "Bachelor of Science in Chemistry",
            "Bachelor of Science in Civil Engineering",
            "Bachelor of Science in Clinical Laboratory Science",
            "Bachelor of Science in Cognitive Science",
            "Bachelor of Science in Computer Engineering",
            "Bachelor of Science in Computer Science",
            "Bachelor of Science in Construction Engineering",
            "Bachelor of Science in Construction Management",
            "Bachelor of Science in Criminal Justice",
            "Bachelor of Science in Criminology",
            "Bachelor of Science in Diagnostic Radiography",
            "Bachelor of Science in Education",
            "Bachelor of Science in Electrical Engineering",
            "Bachelor of Science in Engineering Physics",
            "Bachelor of Science in Engineering Science",
            "Bachelor of Science in Engineering Technology",
            "Bachelor of Science in English Literature",
            "Bachelor of Science in Environmental Engineering",
            "Bachelor of Science in Environmental Science",
            "Bachelor of Science in Environmental Studies",
            "Bachelor of Science in Food Science",
            "Bachelor of Science in Foreign Service",
            "Bachelor of Science in Forensic Science",
            "Bachelor of Science in Forestry",
            "Bachelor of Science in History",
            "Bachelor of Science in Hospitality Management",
            "Bachelor of Science in Human Resources Management",
            "Bachelor of Science in Industrial Engineering",
            "Bachelor of Science in Information Technology",
            "Bachelor of Science in Information Systems",
            "Bachelor of Science in Integrated Science",
            "Bachelor of Science in International Relations",
            "Bachelor of Science in Journalism",
            "Bachelor of Science in Legal Management",
            "Bachelor of Science in Management",
            "Bachelor of Science in Manufacturing Engineering",
            "Bachelor of Science in Marketing",
            "Bachelor of Science in Mathematics",
            "Bachelor of Science in Mechanical Engineering",
            "Bachelor of Science in Medical Technology",
            "Bachelor of Science in Metallurgical Engineering",
            "Bachelor of Science in Meteorology",
            "Bachelor of Science in Microbiology",
            "Bachelor of Science in Mining Engineering",
            "Bachelor of Science in Molecular Biology",
            "Bachelor of Science in Neuroscience",
            "Bachelor of Science in Nursing",
            "Bachelor of Science in Nutrition science",
            "Bachelor of Science in Software Engineering",
            "Bachelor of Science in Petroleum Engineering",
            "Bachelor of Science in Podiatry",
            "Bachelor of Science in Pharmacology",
            "Bachelor of Science in Pharmacy",
            "Bachelor of Science in Physical Therapy",
            "Bachelor of Science in Physics",
            "Bachelor of Science in Plant Science",
            "Bachelor of Science in Politics",
            "Bachelor of Science in Psychology",
            "Bachelor of Science in Public Safety",
            "Bachelor of Science in Physiology",
            "Bachelor of Science in Quantity Surveying Engineering",
            "Bachelor of Science in Radiologic Technology",
            "Bachelor of Science in Real-Time Interactive Simulation",
            "Bachelor of Science in Religion",
            "Bachelor of Science in Respiratory Therapy",
            "Bachelor of Science in Risk Management and Insurance",
            "Bachelor of Science in Science Education",
            "Bachelor of Science in Sports Management",
            "Bachelor of Science in Systems Engineering",
            "Bachelor of Music in Jazz Studies",
            "Bachelor of Music in Composition",
            "Bachelor of Music in Performance",
            "Bachelor of Music in Theory",
            "Bachelor of Music in Music Education",
            "Bachelor of Science in Veterinary Technology"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        spinner = findViewById(R.id.stateSpinner);
        address = findViewById(R.id.addressHeader);
        addressExpanded = findViewById(R.id.addressLayout);
        stud_state = findViewById(R.id.stud_state);
        stud_course = findViewById(R.id.course);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressExpanded.getVisibility() == view.VISIBLE)
                    addressExpanded.setVisibility(View.GONE);
                else
                    addressExpanded.setVisibility(view.VISIBLE);
            }
        });
        ArrayAdapter state_adapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, STATES);
        stud_state.setAdapter(state_adapter);

        ArrayAdapter course_adapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, COURSES);
        stud_course.setAdapter(course_adapter);

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
        List<String> stateList = new ArrayList<>(Arrays.asList(STATES));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, stateList);
        dataAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }
}
