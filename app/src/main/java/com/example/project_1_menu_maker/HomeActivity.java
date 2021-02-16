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

import com.example.project_1_menu_maker.R;
import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.UserDAO;
import com.example.project_1_menu_maker.db.User;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";
//    private static final String PREFERENCES_KEY = "com.example.project_1_menu_maker.db.PREFERENCES_KEY";

    private Button mSearchBtn;
    private Button mDisplayUserBtn;
    private Button mLogoutBtn;

    private User mUser;
    private int mUserId = -1;
    private UserDAO mUserDAO;

    private SharedPreferences mPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getDatabase();

        wireUp();

        Log.e("User ID Coming into Home", String.valueOf(getIntent().getIntExtra(USER_ID_KEY, -1)));

    }

    private void wireUp(){

        mSearchBtn = findViewById(R.id.searchHomeBtn);
        mDisplayUserBtn = findViewById(R.id.displayHomeBtn);
        mLogoutBtn = findViewById(R.id.logoutHomeBtn);

        checkForUser();

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

    private void loginUser(int userId) { mUser = mUserDAO.getUserByUserId(userId); }

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

    private void checkForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        // taken from GymLog
        // do we have a user in preferences?
        if(mUserId != -1){
            loginUser(mUserId);
            return;
        }

//        if(mPreferences == null){
//            getPrefs();
//        }
//
//        mUserId = mPreferences.getInt(USER_ID_KEY, -1);
        if(mUserId != -1){
            loginUser(mUserId);
            return;
        }

        Intent intent = MainActivity.intentFactory(this);
        startActivity(intent);

    }

    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutHomeActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

//    private void getPrefs(){
//        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
//    }

//    private void addUserToPreferences(int userId) {
//        if(mPreferences == null){
//            getPrefs();
//        }
//        SharedPreferences.Editor editor = mPreferences.edit();
//        editor.putInt(USER_ID_KEY, userId);
//        //This might be needed?
//        editor.apply();
//    }

//    private void clearUserFromPref() {
//        addUserToPreferences(-1);
//    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).allowMainThreadQueries().build().getUserDAO();

    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}