package com.example.android.tabsetup;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;
        import android.widget.Toast;

        import com.google.android.material.floatingactionbutton.FloatingActionButton;

        import java.util.ArrayList;
        import java.util.List;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.core.content.ContextCompat;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.room.Room;

public class StudentList extends Fragment implements SmartViewHolder.StudentListener {

    FloatingActionButton studentFab;
    RecyclerView recyclerView;
    SuperChillAdapter adapter;
    AppDatabase db;
    List<Student> students;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_student_tab, container, false);
        recyclerView = rootView.findViewById(R.id.studentRecycler);
        studentFab = rootView.findViewById(R.id.studentFab);

        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SuperChillAdapter(getLayoutInflater(), this);
        recyclerView.setAdapter(adapter);

        students = db.StudentDao().getAllStudents();
        adapter.updateItems(students);

        studentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StudentCreator.class);
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_student, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_order_name:
                students = db.StudentDao().getByName();
                adapter.updateItems(students);
                break;
            case R.id.action_order_id:
                students = db.StudentDao().getAllStudents();
                adapter.updateItems(students);
                break;
            default:
                break;
        }
        return true;
    }

    //Implemented Methods to be passed to the ViewHolder
    @Override
    public void deleteStudent(final Student item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String message = "Delete " + item.getFirstName() + " " + item.getLastName() + "?";
        builder.setMessage(message)
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.StudentExamDao().deleteStudent(item.getStudent_ID());
                        db.StudentDao().deleteStudent(item.getStudent_ID());
                        students = db.StudentDao().getAllStudents();
                        adapter.updateItems(students);
                    }
                }).setNegativeButton("CANCEL", null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void updateStudent(Student item) {
        Context context = getContext();
        Intent intent = new Intent(context, StudentUpdater.class);
        intent.putExtra("STUDENT_ID", Integer.toString(item.getStudent_ID()));
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    @Override
    public void openMaps(Student item) {
        Uri gmmIntentUri = Uri.parse("geo:33.8708,151.2073?q=" + item.getAddress());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
