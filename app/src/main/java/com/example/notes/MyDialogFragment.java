package com.example.notes;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class MyDialogFragment extends DialogFragment {

    public static final String CURRENT_NOTE = "CURRENT_NOTE";
    private Notes note;

    public static MyDialogFragment newInstance(Notes note) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, note);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(CURRENT_NOTE);
        } else {
            note = new Notes();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View editView = getLayoutInflater().inflate(R.layout.alertdialog_change_text, null);
        EditText editText = editView.findViewById(R.id.edit_text);
        editView.findViewById(R.id.apply_text).setOnClickListener(view1 -> {
            dismiss();
            Toast.makeText(requireContext(), editText.getText().toString(), Toast.LENGTH_SHORT).show();
            note.setCaptionNotes(editText.getText().toString());
        });
        return editView;
    }

}
