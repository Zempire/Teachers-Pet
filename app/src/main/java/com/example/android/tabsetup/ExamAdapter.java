package com.example.android.tabsetup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ExamAdapter extends RecyclerView.Adapter {

    public int mExpandedPosition = -1;
    public int previousExpandPosition = -1;
    private List<Exam> items;
    private LayoutInflater inflater;
    private ExamViewHolder.ExamListener examListener;
    ExamList examList;
    ExamViewHolder eh;

    AppDatabase db;
    List<StudentResult> results;


    public ExamAdapter(LayoutInflater inflater, ExamViewHolder.ExamListener examListener, ExamList examList) {
        this.inflater = inflater;
        this.examListener = examListener;
        this.examList = examList;
        items = new ArrayList<>();
    }

    public void updateItems(final List<Exam> newItems) {
        final List<Exam> oldItems = new ArrayList<>(this.items);
        this.items.clear();
        if (newItems != null) {
            this.items.addAll(newItems);
        }
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldItems.size();
            }

            @Override
            public int getNewListSize() {
                return items.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
            }
        }).dispatchUpdatesTo(this);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.exam_row, parent, false);
        return new ExamViewHolder(v, examListener, examList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        eh = (ExamViewHolder) holder;
        eh.setItem(items.get(position));
        eh.isExpanded = position==mExpandedPosition;
        eh.optionsContainer.setVisibility(eh.isExpanded?View.VISIBLE:View.GONE);
        eh.toggleExamInfo.setRotation(eh.isExpanded?180:0);

        if (eh.isExpanded)
            previousExpandPosition = position;

        eh.multiSelectBox.setChecked(false);
        eh.examContainer.setBackgroundResource(R.color.cardBackground);
        db = Room.databaseBuilder(eh.examName.getContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();
        results = db.StudentExamDao().getResults(eh.item.getExam_ID());
        StudentResultAdapter adapter = new StudentResultAdapter(results);
        eh.recyclerView.setLayoutManager(new LinearLayoutManager(eh.examName.getContext()));
        eh.recyclerView.setAdapter(adapter);


        if (!examList.is_in_action_mode) {
            eh.toggleExamInfo.setVisibility(View.VISIBLE);
        } else {
            eh.toggleExamInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}