package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Notes implements Parcelable {

    private String captionNotes;
    private String contextNotes;
    private String date;
    private int imagePosition;
    public List<Notes> notesList = new ArrayList<>();

    public Notes() {
        captionNotes = "";
        contextNotes = "";
        date = getCurrentDate();
        createListOfNotes();
    }

    public Notes(String captionNotes, String contextNotes, int imagePosition) {
        this.captionNotes = captionNotes;
        this.contextNotes = contextNotes;
        this.imagePosition = imagePosition;
        this.date = getCurrentDate();
    }

    protected Notes(Parcel in) {
        captionNotes = in.readString();
        contextNotes = in.readString();
        date = in.readString();
        imagePosition = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    private void createListOfNotes() {
        notesList.add(new Notes("Movies", "Movie is a recording of moving images that tells a story and that people watch on a screen or television : motion picture.", 0));
        notesList.add(new Notes("Music", "Music is the art of arranging sounds in time through the elements of melody, harmony, rhythm, and timbre.", 1));
        notesList.add(new Notes("Clothes", "- T-shirt\n- sweater\n- jacket\n- coat\n- jeans", 2));
        notesList.add(new Notes("Products", "- pear\n- tangerine\n- almonds\n- cauliflower\n- chocolate", 3));
        notesList.add(new Notes("Recipe", "Heat oven to 200C. Cook the vegetables in a casserole dish for 15 mins. Tip in the beans and tomatoes, season, and cook for another 10-15 mins until piping hot.", 4));
    }

    public Notes getCurrentNote(int position){
        return notesList.get(position);
    }

    public int getImagePosition() {
        return imagePosition;
    }

    public void setImagePosition(int imagePosition) {
        this.imagePosition = imagePosition;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(captionNotes);
        dest.writeString(contextNotes);
        dest.writeString(date);
    }


    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public String getCaptionNotes() {
        return captionNotes;
    }

    public String getContextNotes() {
        return contextNotes;
    }

    public String getDate() {
        return date;
    }

    public void setCaptionNotes(String captionNotes) {
        this.captionNotes = captionNotes;
    }

    public void setContextNotes(String contextNotes) {
        this.contextNotes = contextNotes;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
