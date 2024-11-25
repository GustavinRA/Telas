package com.example.telas.bibliotecaTreinos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.R;
import com.example.telas.model.Workout;
import com.example.telas.model.WorkoutExercise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterTraining extends RecyclerView.Adapter<AdapterTraining.TrainingViewHolder> {
    private List<Workout> workouts;
    private Map<Integer, Boolean> expandedState = new HashMap<>();

    public AdapterTraining(List<Workout> workouts) {
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_treino, parent, false);
        return new TrainingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        Workout workout = workouts.get(position);
        holder.tvTrainingTitle.setText(workout.getNome());

        boolean isExpanded = expandedState.getOrDefault(position, false);
        holder.rvExercises.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        if (isExpanded) {
            AdapterExercise adapter = new AdapterExercise(workout.getExercises());
            holder.rvExercises.setAdapter(adapter);
            holder.rvExercises.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        }

        holder.itemView.setOnClickListener(v -> {
            expandedState.put(position, !isExpanded);
            notifyItemChanged(position);
        });

        holder.btnDelete.setOnClickListener(v -> {
            workouts.remove(position);  // Remove the workout from the list
            notifyItemRemoved(position);  // Update the RecyclerView
        });
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public static class TrainingViewHolder extends RecyclerView.ViewHolder {
        TextView tvTrainingTitle;
        RecyclerView rvExercises;
        ImageButton btnDelete;

        public TrainingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTrainingTitle = itemView.findViewById(R.id.tvTrainingTitle);
            rvExercises = itemView.findViewById(R.id.rvExercises);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
