package com.freelancer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.databinding.JobListingListContentBinding;
import com.freelancer.placeholder.JobListingPlaceholderContent.JobListingItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link JobListingItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyJobListingRecyclerViewAdapter extends RecyclerView.Adapter<MyJobListingRecyclerViewAdapter.ViewHolder> {

    private final List<JobListingItem> mValues;

    public MyJobListingRecyclerViewAdapter(List<JobListingItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(JobListingListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public JobListingItem mItem;

        public ViewHolder(JobListingListContentBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}