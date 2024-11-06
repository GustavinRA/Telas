package com.example.telas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ExerciciosAdapter extends RecyclerView.Adapter<ExerciciosAdapter.ViewHolder> {
    private final List<Exercicio> exerciseList;
    private final Context context;

    public ExerciciosAdapter(Context context, List<Exercicio> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exercicios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercicio exercise = exerciseList.get(position);
        holder.exerciseName.setText(exercise.getNome());
        holder.affectedMuscles.setText("Músculos afetados: " + exercise.getMusculosComoTexto());

        // Inicialmente, esconde o container de checkboxes
        holder.checkboxContainer.setVisibility(View.GONE);

        holder.editButton.setOnClickListener(v -> {
            // Alterna a visibilidade do container de checkboxes
            boolean isVisible = holder.checkboxContainer.getVisibility() == View.VISIBLE;
            holder.checkboxContainer.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            updateCheckboxes(exercise, holder);
        });

        // Configura os checkboxes com base no tag e define o estado
        for (int i = 0; i < holder.checkboxContainer.getChildCount(); i++) {
            View view = holder.checkboxContainer.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox checkbox = (CheckBox) view;
                String muscle = checkbox.getText().toString();

                // Define o estado do checkbox com base no modelo de dados
                checkbox.setChecked(exercise.getMusculosAfetados().contains(muscle));

                // Adiciona ou remove o músculo da lista quando o checkbox é marcado/desmarcado
                checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        exercise.addMusculo(muscle);
                    } else {
                        exercise.removeMusculo(muscle);
                    }
                    // Atualiza o texto dos músculos afetados
                    holder.affectedMuscles.setText("Músculos afetados: " + exercise.getMusculosComoTexto());
                });
            }
        }
    }

    // Atualiza o estado dos checkboxes com base nos dados do exercício
    private void updateCheckboxes(Exercicio exercise, ViewHolder holder) {
        for (int i = 0; i < holder.checkboxContainer.getChildCount(); i++) {
            View view = holder.checkboxContainer.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox checkbox = (CheckBox) view;
                String muscle = checkbox.getText().toString();
                checkbox.setChecked(exercise.getMusculosAfetados().contains(muscle));
            }
        }
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView affectedMuscles;
        Button editButton;
        GridLayout checkboxContainer;

        ViewHolder(View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercicio_nome);
            affectedMuscles = itemView.findViewById(R.id.musculoAfetadoId);
            editButton = itemView.findViewById(R.id.edit_button);
            checkboxContainer = itemView.findViewById(R.id.checkbox_container);
        }
    }
}


