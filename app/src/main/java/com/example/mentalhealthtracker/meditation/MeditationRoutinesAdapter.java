package com.example.mentalhealthtracker.meditation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.meditation.db.MeditationRoutine;

import java.util.List;

public class MeditationRoutinesAdapter extends RecyclerView.Adapter<MeditationRoutinesAdapter.MeditationRoutinesHolder> {
    private List<MeditationRoutine> routines;
    private MeditationRoutinesAdapter.OnMeditationRoutineClickListener sListener;

    public MeditationRoutinesAdapter(List<MeditationRoutine> routines) {
        this.routines = routines;
    }

    @NonNull
    @Override
    public MeditationRoutinesAdapter.MeditationRoutinesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.meditation_routine_card, parent, false
        );
        return new MeditationRoutinesAdapter.MeditationRoutinesHolder(v, sListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MeditationRoutinesAdapter.MeditationRoutinesHolder holder, int position) {
        MeditationRoutine indexedRoutine = this.routines.get(position);
        holder.routineTitle.setText(indexedRoutine.getRoutineName());
        holder.routineDescription.setText(indexedRoutine.getRoutineDescription());
    }

    @Override
    public int getItemCount() {
        return this.routines.size();
    }

    public void setOnClickListener(MeditationRoutinesAdapter.OnMeditationRoutineClickListener listener) {
        this.sListener = listener;
    }

    public void setRoutines(List<MeditationRoutine> routines) {
        this.routines = routines;
        notifyDataSetChanged();
    }

    public interface OnMeditationRoutineClickListener {
        void onMeditationRoutineClicked(int position);
    }

    public static class MeditationRoutinesHolder extends RecyclerView.ViewHolder {
        public TextView routineTitle;
        public TextView routineDescription;

        public MeditationRoutinesHolder(@NonNull View itemView,
                                         MeditationRoutinesAdapter.OnMeditationRoutineClickListener listener) {
            super(itemView);

            routineTitle = itemView.findViewById(R.id.meditationRoutineTitle);
            routineDescription = itemView.findViewById(R.id.meditationRoutineDescription);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onMeditationRoutineClicked(position);
                    }
                }
            });
        }
    }
}
