package com.example.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private OnDialogListener dialogListener;

    public static MyBottomSheetDialogFragment newInstance() {
        return new MyBottomSheetDialogFragment();
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View editView = getLayoutInflater().inflate(R.layout.alertdialog_change_text, container, false);
        TextView textView = editView.findViewById(R.id.edit_caption_text);
        textView.setText(dialogListener.getCaption());
        EditText editText = editView.findViewById(R.id.edit_text);
        editView.findViewById(R.id.apply_text).setOnClickListener(view1 -> {
            dismiss();
            if (dialogListener != null) {
                dialogListener.onDialogApply();
                dialogListener.setCaption(editText.getText().toString());
            }
        });
        return editView;
    }

}
