package com.example.android.tabsetup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends RecyclerView.Adapter {
    private List<File> items;
    private LayoutInflater inflater;
    private GalleryViewHolder.GalleryListener galleryListener;
    GalleryActivity galleryActivity;
    GalleryViewHolder gh;

    //For controlling expansion of just 1 ViewHolder.
    private int mExpandedPosition = -1;
    private int previousExpandPosition = -1;


    public GalleryAdapter(LayoutInflater inflater, GalleryViewHolder.GalleryListener galleryListener, GalleryActivity galleryActivity) {
        this.inflater = inflater;
        this.galleryListener = galleryListener;
        this.galleryActivity = galleryActivity;
        items = new ArrayList<>();
    }

    public void updateItems(final List<File> newItems) {
        final List<File> oldItems = new ArrayList<>(this.items);
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
        View v = inflater.inflate(R.layout.gallery_row, parent, false);
        return new GalleryViewHolder(v, galleryListener, galleryActivity);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        gh = (GalleryViewHolder) holder;
        gh.setItem(items.get(position));
        final boolean isExpanded = position==mExpandedPosition;
//        gh.optionsContainer.setVisibility(isExpanded?View.VISIBLE:View.GONE);
//        gh.toggleTaskInfo.setChecked(isExpanded?true:false);
//
//        if (isExpanded)
//            previousExpandPosition = position;
//        gh.toggleTaskInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mExpandedPosition = isExpanded ? -1:position;
//                notifyItemChanged(previousExpandPosition);
//                notifyItemChanged(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}