package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    public static final String PREFERENCES_NAME = "preferences";
    public static final int ThemeNotes = 0;
    public static final int MyThemePear = 1;
    public static final int MyThemeBlueberry = 2;
    private static final String THEME_NAME = "theme";
    private static final int REQUEST_CODE_SETTING_ACTIVITY = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadAppTheme());
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ListOfNotesFragment())
                    .commit();
        } else if (getSupportFragmentManager().getFragments().size() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getFragments().size() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private int loadAppTheme() {
        int codeTheme = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
                .getInt(THEME_NAME, ThemeNotes);
        return codeStyleToStyleId(codeTheme);
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case MyThemePear:
                return R.style.MyThemePear;
            case MyThemeBlueberry:
                return R.style.MyThemeBlueberry;
            default:
                return R.style.ThemeNotes;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SETTING_ACTIVITY && resultCode == RESULT_OK && data != null) {
            int themeCode = data.getIntExtra(THEME_NAME, ThemeNotes);
            codeStyleToStyleId(themeCode);
            recreate();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}