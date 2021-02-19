package com.example.project_1_menu_maker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_1_menu_maker.R;
import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.UserDAO;
import com.example.project_1_menu_maker.db.User;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";

    private Button mSearchBtn;
    private Button mDisplayUserBtn;
    private Button mLogoutBtn;

    private User mUser;
    private int mUserId = -1;
    private UserDAO mUserDAO;

    private SharedPreferences mPreferences = null;

    private TextView mWelcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getDatabase();

        wireUp();

        Log.e("User ID Coming into Home", String.valueOf(getIntent().getIntExtra(USER_ID_KEY, -1)));

    }

    /**
     * wireUp
     * Basic wire up function that wires up all buttons
     */
    private void wireUp(){

        mSearchBtn = findViewById(R.id.searchHomeBtn);
        mDisplayUserBtn = findViewById(R.id.displayHomeBtn);
        mLogoutBtn = findViewById(R.id.logoutHomeBtn);

        mWelcomeMsg = findViewById(R.id.welcomeMsgHomeActivity);

        checkForUser();

        addUserToWelcome();

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NOTE not actually putting int into intent here FOR SOME REASON
                Intent intent = SearchActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        mDisplayUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DisplayUserRecipeActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();

            }
        });
    }

    /**
     * addUserToWelcome
     * Adds username to welcome message
     */
    private void addUserToWelcome(){
        mWelcomeMsg.setText("Welcome to " + mUser.getUsername() + "'s Menu");
    }

    /**
     * loginUser
     * @param userId
     * Does as function says, grabs user from DB
     */
    private void loginUser(int userId) { mUser = mUserDAO.getUserByUserId(userId); }

    /**
     * logoutUser
     * removes user from intent using an alert dialog
     */
    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearUserFromIntent();
//                clearUserFromPref();
                mUserId = -1;
                checkForUser();
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

    /**
     * checkForUser
     * Checks for user in intent, and then logs them in. Otherwise will send back to mainActivity
     */
    private void checkForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1){
            loginUser(mUserId);
            return;
        }

        Intent intent = MainActivity.intentFactory(this);
        startActivity(intent);

    }

    /**
     * snackMaker
     * @param message
     * Makes a snack with variable message string for short time
     */
    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutHomeActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    /**
     * clearUserFromIntent
     * Does as function says
     */
    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    /**
     * getDatabase
     * Gets user DB
     */
    private void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).allowMainThreadQueries().build().getUserDAO();

    }

    /**
     * intentFactory
     * @param context
     * @param userId
     * @return
     * Wraps userId into intent directed towards this class
     */
    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}