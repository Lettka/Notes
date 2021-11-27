package com.example.notes;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment {

    public static final String PREFERENCES_NAME = "preferences";
    public static final int ThemeDefault = 0;
    public static final int MyThemePear = 1;
    public static final int MyThemeBlueberry = 2;
    public static final String NameThemeDefault = "ThemeDefault";
    public static final String NameMyThemePear = "MyThemePear";
    public static final String NameMyThemeBlueberry = "MyThemeBlueberry";
    private static final String THEME_NAME = "theme";


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem menuItem1 = menu.findItem(R.id.action_about);
        MenuItem menuItem2 = menu.findItem(R.id.action_settings);
        if (menuItem1 != null) {
            menuItem1.setVisible(true);
        } else if (menuItem2 != null) {
            menuItem2.setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String textBar;
        int currentTheme = requireActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getInt(THEME_NAME, ThemeDefault);
        if (currentTheme == MyThemePear) {
            ((RadioButton) view.findViewById(R.id.radio_button_pear)).setChecked(true);
            textBar = NameMyThemePear;
        } else if (currentTheme == MyThemeBlueberry) {
            ((RadioButton) view.findViewById(R.id.radio_button_blueberry)).setChecked(true);
            textBar = NameMyThemeBlueberry;
        } else {
            ((RadioButton) view.findViewById(R.id.radio_button_default)).setChecked(true);
            textBar = NameThemeDefault;
        }
        Snackbar bar = Snackbar.make(view, "Current theme - " + textBar, Snackbar.LENGTH_INDEFINITE);
        bar.setAction("ok", v -> bar.dismiss()).show();
        view.findViewById(R.id.radio_button_default).setOnClickListener(v -> {
            saveAppTheme(ThemeDefault);
            requireActivity().recreate();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();
        });
        view.findViewById(R.id.radio_button_blueberry).setOnClickListener(v -> {
            saveAppTheme(MyThemeBlueberry);
            requireActivity().recreate();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();
        });
        view.findViewById(R.id.radio_button_pear).setOnClickListener(v -> {
            saveAppTheme(MyThemePear);
            requireActivity().recreate();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();
        });
        view.findViewById(R.id.button_back_to_note).setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void saveAppTheme(int code) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(THEME_NAME, code)
                .apply();
    }
}