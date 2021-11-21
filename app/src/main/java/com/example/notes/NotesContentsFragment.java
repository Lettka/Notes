package com.example.notes;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotesContentsFragment extends Fragment {

    public static final String CURRENT_NOTE = "CURRENT_NOTE";

    final Calendar myCalendar = Calendar.getInstance();

    private Notes note;

    public NotesContentsFragment() {
        // Required empty public constructor
    }

    public static NotesContentsFragment newInstance(Notes note) {
        NotesContentsFragment fragment = new NotesContentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, note);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_contents, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPopUp(view);

        Button buttonBack = view.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

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

    private void initPopUp(View view) {
        ImageView imageView = view.findViewById(R.id.image_view);
        imageView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireActivity(), v);
            requireActivity().getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.action_popup_clear) {
                    imageView.setImageResource(0);
                    return true;
                } else if (id == R.id.action_popup_exit) {
                    requireActivity().onBackPressed();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    private void updateLabel(View view) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText(sdf.format(myCalendar.getTime()));
        note.setDate(String.valueOf(date.getText()));
    }


    private void initView(View view) {
        ImageView imageView = view.findViewById(R.id.image_view);
        TypedArray images = getResources().obtainTypedArray(R.array.img_for_notes);
        imageView.setImageResource(images.getResourceId(note.getImagePosition(), 0));
        TextView date = view.findViewById(R.id.date);
        date.setText(note.getDate());
        TextView caption = view.findViewById(R.id.caption);
        caption.setText(note.getCaptionNotes());
        MultiAutoCompleteTextView notesContext = view.findViewById(R.id.notesContext);
        notesContext.setText(note.getContextNotes());
        images.recycle();
    }
}