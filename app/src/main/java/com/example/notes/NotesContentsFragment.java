package com.example.notes;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

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
        buttonBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());

        Button buttonOpenImg = view.findViewById(R.id.button_open_img);
        buttonOpenImg.setOnClickListener(view1 -> {
            ImageFragment imageFragment = ImageFragment.newInstance(note.getImagePosition());
            getChildFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.fragment_container2, imageFragment)
                    .commit();
        });

        DatePickerDialog.OnDateSetListener datePicker = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(view);
        };


        view.findViewById(R.id.date).setOnClickListener(v ->
                new DatePickerDialog(getContext(), datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        view.findViewById(R.id.caption).setOnClickListener(v -> {
            View editView = getLayoutInflater().inflate(R.layout.alertdialog_change_text, null);
            EditText editText = editView.findViewById(R.id.edit_text);
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
            dialog.setCancelable(true)
                    .setView(editView)
                    .setTitle("Edit caption")
                    .setMessage(note.getCaptionNotes());
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
            editView.findViewById(R.id.apply_text).setOnClickListener(view1 -> {
                note.setCaptionNotes(editText.getText().toString());
                TextView caption = view.findViewById(R.id.caption);
                caption.setText(note.getCaptionNotes());
                alertDialog.dismiss();
            });

        });

        view.findViewById(R.id.set_caption).setOnClickListener(v -> {
            MyDialogFragment dialogFragment = MyDialogFragment.newInstance(note);
            dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
            TextView caption = view.findViewById(R.id.caption);
            caption.setText(note.getCaptionNotes());
        });

        OnDialogListener dialogListener = new OnDialogListener() {
            @Override
            public void onDialogApply() {
                Toast.makeText(getContext(), "new caption is apply", Toast.LENGTH_SHORT).show();
            }

            @Override
            public String getCaption() {
                return note.getCaptionNotes();
            }

            @Override
            public void setCaption(String name) {
                note.setCaptionNotes(name);
                TextView caption = view.findViewById(R.id.caption);
                caption.setText(note.getCaptionNotes());
            }
        };

        view.findViewById(R.id.show_bottom_sheet).setOnClickListener(v -> {
            MyBottomSheetDialogFragment dialogFragment = MyBottomSheetDialogFragment.newInstance();
            dialogFragment.setOnDialogListener(dialogListener);
            dialogFragment.show(getChildFragmentManager(), "dialog_fragment");
        });

        view.findViewById(R.id.show_notification).setOnClickListener(v -> {

            Notification push = new NotificationCompat.Builder(requireContext(), "33")
                    .setSmallIcon(R.drawable.ic_checked_checkbox)
                    .setContentTitle(note.getCaptionNotes())
                    .setContentText(note.getContextNotes())
                    .setChannelId("33")
                    .build();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel("33", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager =
                        (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            NotificationManagerCompat.from(requireContext()).notify(33, push);
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
        TextView date = view.findViewById(R.id.date);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_NOTE, note);
    }
}