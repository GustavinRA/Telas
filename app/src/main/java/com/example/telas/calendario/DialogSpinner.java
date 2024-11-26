package com.example.telas.calendario;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.telas.R;

import java.util.List;

public class DialogSpinner extends DialogFragment {
    private List<String> treinos;
    private DialogListener listener;

    public DialogSpinner(List<String> treinos, DialogListener listener) {
        this.treinos = treinos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha um treino");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, treinos);

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onTrainingSelected(treinos.get(which)); // Passa o treino selecionado
            }
        });

        return builder.create();
    }

    public interface DialogListener {
        void onTrainingSelected(String training);
    }
}
