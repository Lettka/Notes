package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

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

        initToolbar();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ListOfNotesFragment())
                    .commit();
        } else if (getSupportFragmentManager().getFragments().size() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_about) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .add(R.id.fragment_container, new AboutFragment())
                        .commit();
                drawerLayout.closeDrawers();
                return true;
            } else if (id == R.id.action_settings) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .add(R.id.fragment_container, new SettingsFragment())
                        .commit();
                drawerLayout.closeDrawers();
                return true;
            } else if (id == R.id.action_exit) {
                new AlertDialog.Builder(this)
                        .setCancelable(true)
                        .setTitle("Exit")
                        .setMessage("Do you want to close app?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            finish();
                            Toast.makeText(this, "Bye!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No",(dialogInterface, i) -> {
                            Toast.makeText(this, "Let's write one more note", Toast.LENGTH_SHORT).show();
                        })
                        .show();
                return true;
            }
            return false;
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("")
                    .add(R.id.fragment_container, new AboutFragment())
                    .commit();
            return true;
        } else if (id == R.id.action_settings) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("")
                    .add(R.id.fragment_container, new SettingsFragment())
                    .commit();
            return true;
        } else if (id == R.id.action_exit) {
            new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setTitle("Exit")
                    .setMessage("Do you want to close app?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        finish();
                        Toast.makeText(this, "Bye!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No",(dialogInterface, i) -> {
                        Toast.makeText(this, "Let's write one more note", Toast.LENGTH_SHORT).show();
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}