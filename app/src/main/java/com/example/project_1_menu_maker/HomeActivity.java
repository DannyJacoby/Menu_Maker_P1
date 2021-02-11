package com.example.project_1_menu_maker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project_1_menu_maker.R;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    private Button mSearchBtn;
    private Button mDisplayBtn;
    private Button mLogoutBtn;

    // private User mUser;
    private int mUserId = -1;
    private static final String USER_ID_KEY = "com.example.users.db.userIdKey";

    private SharedPreferences mPreferences = null;
    private static final String PREFERENCES_KEY = "com.example.users.db.preferences_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        wireUp();

        getDatabase();

    }

    private void wireUp(){

        mSearchBtn = findViewById(R.id.searchHomeBtn);
        mDisplayBtn = findViewById(R.id.displayHomeBtn);
        mLogoutBtn = findViewById(R.id.logoutHomeBtn);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        mDisplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();

            }
        });
    }

    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearUserFromIntent();
                clearUserFromPref();
                mUserId = -1;
                checkForUsers();
            }
        });

        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                snackMaker("You clicked No");
            }
        });

        alertBuilder.create().show();
    }

    private void checkForUsers(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        // taken from GymLog
        // do we have a user in preferences?
        if(mUserId != -1){
            return;
        }
        if(mPreferences == null){
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);
        if(mUserId != -1){
            return;
        }

        Intent intent = MainActivity.intentFactory(this);
        startActivity(intent);

    }

    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutLoginActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    private void getPrefs(){
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void addUserToPreferences(int userId) {
        if(mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        //This might be needed?
        editor.apply();
    }

    private void clearUserFromPref() {
        addUserToPreferences(-1);
    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void getDatabase(){
        /* something like
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).allowMainThreadQueries().build().getUserDAO();
         */
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}