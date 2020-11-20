package com.example.mentalhealthtracker.mindfulness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.mindfulness.db.MindfulnessActivity;

import java.util.List;

public class MindfulnessActivityAdapter extends RecyclerView.Adapter<MindfulnessActivityAdapter.MindfulnessActivityHolder> {
    private List<MindfulnessActivity> activities;
    private OnMindfulnessActivityClickListener sListener;

    public MindfulnessActivityAdapter(List<MindfulnessActivity> activities) {
        this.activities = activities;
    }

    @NonNull
    @Override
    public MindfulnessActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.mindfulness_card, parent, false
        );
        return new MindfulnessActivityHolder(v, sListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MindfulnessActivityHolder holder, int position) {
        MindfulnessActivity indexActivity = this.activities.get(position);
        holder.activityTitle.setText(indexActivity.getTitle());
        holder.activityDescription.setText(indexActivity.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.activities.size();
    }

    public void setOnClickListener(OnMindfulnessActivityClickListener listener) {
        this.sListener = listener;
    }

    public void setActivities(List<MindfulnessActivity> activities) {
        this.activities = activities;
        notifyDataSetChanged();
    }

    public interface OnMindfulnessActivityClickListener {
        void onMindfulnessActivityClicked(int position);
    }

    public static class MindfulnessActivityHolder extends RecyclerView.ViewHolder {
        public TextView activityTitle;
        public TextView activityDescription;

        public MindfulnessActivityHolder(@NonNull View itemView,
                                         OnMindfulnessActivityClickListener listener) {
            super(itemView);

            activityTitle = itemView.findViewById(R.id.mindfulnessActivityName);
            activityDescription = itemView.findViewById(R.id.mindfulnessActivityDescription);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onMindfulnessActivityClicked(position);
                    }
                }
            });
        }
    }
}
