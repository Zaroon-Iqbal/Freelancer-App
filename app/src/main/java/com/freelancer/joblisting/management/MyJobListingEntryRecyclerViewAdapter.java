package com.freelancer.joblisting.management;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.freelancer.databinding.JobListingEntryBinding;
import com.freelancer.joblisting.creation.model.JobListingModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link JobListingModel}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyJobListingEntryRecyclerViewAdapter extends RecyclerView.Adapter<MyJobListingEntryRecyclerViewAdapter.ViewHolder> {

    private final List<JobListingModel> mValues;

    public MyJobListingEntryRecyclerViewAdapter(List<JobListingModel> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(JobListingEntryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mImageView.setImageResource(R.drawable.logotransparent);
        holder.mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        holder.mIdView.setText(mValues.get(position).jobInfoModel.title);
        holder.mContentView.setText(mValues.get(position).jobInfoModel.category);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDatePostedView;
        public final ImageView mImageView;
        public JobListingModel mItem;

        public ViewHolder(JobListingEntryBinding binding) {
            super(binding.getRoot());
            mIdView = binding.header;
            mImageView = binding.imageView;
            mDatePostedView = binding.textView3;
            mContentView = binding.supportingText;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}