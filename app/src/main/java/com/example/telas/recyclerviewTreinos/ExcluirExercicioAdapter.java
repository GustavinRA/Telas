    package com.example.telas.recyclerviewTreinos;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageButton;
    import androidx.recyclerview.widget.RecyclerView;
    import java.util.List;
    import com.example.telas.R;

    public class ExcluirExercicioAdapter extends RecyclerView.Adapter<ExcluirExercicioAdapter.ViewHolder> {
        private List<Exercicio> exercicios;
        private Context context;
        private OnExercicioRemovedListener listener;

        public ExcluirExercicioAdapter(List<Exercicio> exercicios, Context context, OnExercicioRemovedListener listener) {
            this.exercicios = exercicios;
            this.context = context;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.excluir_exercicio, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Exercicio exercicio = exercicios.get(position);

            holder.removerExercicio.setOnClickListener(v -> {
                exercicios.remove(position);
                notifyItemRemoved(position);
                if (listener != null) {
                    listener.onExercicioRemoved(exercicio);
                }
            });
        }

        @Override
        public int getItemCount() {

            return exercicios.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageButton removerExercicio;

            public ViewHolder(View itemView) {
                super(itemView);
                removerExercicio = itemView.findViewById(R.id.removerExercicio);
            }
        }

        public interface OnExercicioRemovedListener {
            void onExercicioRemoved(Exercicio exercicio);
        }
    }

