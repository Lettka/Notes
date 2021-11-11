package com.example.notes;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ListOfNotesFragment extends Fragment {

    public static final String CURRENT_POSITION = "CURRENT_POSITION";
    public static final String LIST_OF_NOTES = "LIST_OF_NOTES";
    private int currentPosition = -1;
    private Notes notes;

    public ListOfNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_POSITION, -1);
            notes = savedInstanceState.getParcelable(LIST_OF_NOTES);
        } else {
            notes = new Notes();
        }
        initView(view);
        updateBackground();
    }

    private void initView(View view) {
        LinearLayout linearLayout = view.findViewById(R.id.notes_container);
        List<Notes> notesList = notes.notesList;
        for (int i = 0; i < notesList.size(); i++) {

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 2);

            LinearLayout linearLayoutNotes = new LinearLayout(getContext());
            linearLayoutNotes.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutNotes.setPaddingRelative(15, 10, 15, 25);

            LinearLayout notesContext = new LinearLayout(getContext());
            notesContext.setOrientation(LinearLayout.VERTICAL);
            notesContext.setLayoutParams(params2);

            //caption
            TextView textCaption = new TextView(getContext());
            textCaption.setText(notesList.get(i).getCaptionNotes());
            textCaption.setTextSize(30);
            textCaption.setTextColor(Color.BLACK);
            textCaption.setTypeface(Typeface.DEFAULT_BOLD);

            //context
            TextView textContext = new TextView(getContext());
            textContext.setText(notesList.get(i).getContextNotes());
            textContext.setTextSize(15);
            textContext.setTextColor(Color.GRAY);
            textContext.setMaxLines(1);
            textContext.setEllipsize(TextUtils.TruncateAt.END);

            //date
            TextView textDate = new TextView(getContext());
            textDate.setText(notesList.get(i).getDate());
            textDate.setId(i);
            textDate.setTextSize(20);
            textDate.setTextColor(Color.GRAY);
            textDate.setLayoutParams(params1);
            textDate.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
            textDate.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));

            notesContext.addView(textCaption);
            notesContext.addView(textContext);

            linearLayoutNotes.addView(notesContext);
            linearLayoutNotes.addView(textDate);

            final int position = i;
            linearLayoutNotes.setOnClickListener(v -> {
                currentPosition = position;
                openNotes(position);
                updateBackground();
            });
            linearLayout.addView(linearLayoutNotes);
        }
        if (currentPosition != -1) {
            openNotes(currentPosition);
        }
    }

    private void updateBackground() {
        List<Notes> notesList = notes.notesList;
        LinearLayout linearLayout = getView().findViewById(R.id.notes_container);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            if (currentPosition == i) {
                linearLayout.getChildAt(i).setBackgroundColor(Color.LTGRAY);
            }
        }
        for (int i = 0; i < notesList.size(); i++) {
            ((TextView)getView().findViewById(i)).setText(notesList.get(i).getDate());
        }
    }

    private void openNotes(int position) {
        if (isLand()) {
            openNotesLand(position);
        } else {
            openNotesPort(position);
        }

    }

    private void openNotesPort(int position) {
        NotesContentsFragment fragment = NotesContentsFragment.newInstance(position, notes);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void openNotesLand(int position) {
        NotesContentsFragment fragment = NotesContentsFragment.newInstance(position, notes);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container2, fragment)
                .commit();
    }

    private boolean isLand() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_POSITION, currentPosition);
        outState.putParcelable(LIST_OF_NOTES, notes);
    }
}