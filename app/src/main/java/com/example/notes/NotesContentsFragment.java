package com.example.notes;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotesContentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "ARG_POSITION";
    public static final String LIST_OF_NOTES = "LIST_OF_NOTES";

    final Calendar myCalendar = Calendar.getInstance();

    // TODO: Rename and change types of parameters
    private int position = -1;
    private Notes note;

    public NotesContentsFragment() {
        // Required empty public constructor
    }

    public static NotesContentsFragment newInstance(int position, Notes note) {
        NotesContentsFragment fragment = new NotesContentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putParcelable(LIST_OF_NOTES, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
            note = getArguments().getParcelable(LIST_OF_NOTES);
        } else {
            note = new Notes();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_contents, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatePickerDialog.OnDateSetListener datePicker = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(view);
        };


        view.findViewById(R.id.date).setOnClickListener(v -> {
            new DatePickerDialog(getContext(), datePicker, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        initView(view);
    }

    private void updateLabel(View view) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText(sdf.format(myCalendar.getTime()));
        note.notesList.get(position).setDate(String.valueOf(date.getText()));
    }


    private void initView(View view) {
        ImageView imageView = view.findViewById(R.id.image_view);
        TypedArray images = getResources().obtainTypedArray(R.array.img_for_notes);
        imageView.setImageResource(images.getResourceId(position, 0));
        TextView date = view.findViewById(R.id.date);
        date.setText(note.notesList.get(position).getDate());
        TextView caption = view.findViewById(R.id.caption);
        caption.setText(note.notesList.get(position).getCaptionNotes());
        MultiAutoCompleteTextView notesContext = view.findViewById(R.id.notesContext);
        notesContext.setText(note.notesList.get(position).getContextNotes());
        images.recycle();
    }
}