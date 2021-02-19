package com.example.project_1_menu_maker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.UserDAO;
import com.example.project_1_menu_maker.db.User;
import com.google.android.material.snackbar.Snackbar;

public class SignupActivity extends AppCompatActivity {

    private EditText mUsernameField;
    private String mUsernameString;
    private EditText mPasswordField;
    private String mPasswordString;

    private Button mSignUpButton;

    private User mUser;
    private UserDAO mUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getDatabase();

        wireUp();

    }

    /**
     * wireUp
     * Basic function to wire up buttons and text fields
     */
    private void wireUp(){
        mUsernameField = findViewById(R.id.usernameInput);
        mPasswordField = findViewById(R.id.passwordInput);

        mSignUpButton = findViewById(R.id.button);
        mSignUpButton.setOnClickListener(v -> {
            getValuesFromDisplay();
            if(checkForUser()){ // user does exist thus head down registration path
                createUser();
                snackMaker("User Created, Go To Login");
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            } else { // username is already taken, go to login possibly
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(findViewById(R.id.layoutSignUpActivity).getContext());
                alertBuilder.setMessage("Username already in use, Login?");
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
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

        });

    }

    /**
     * checkForUser
     * @return
     * Checks if a user already exists in the DB, else returns false
     */
    private boolean checkForUser(){
        mUser = mUserDAO.getUserByUsername(mUsernameString);
        return (mUser == null);
    }

    /**
     * createUser
     * Creates user and inserts it into the DB
     */
    private void createUser(){
        // maybe add something like "username needs x y z and/or password needs x y z"
        mUser = new User(mUsernameString, mPasswordString);
        mUserDAO.insert(mUser);
    }

    /**
     * getValuesFromDisplay
     */
    private void getValuesFromDisplay(){
        mPasswordString = mPasswordField.getText().toString();
        mUsernameString = mUsernameField.getText().toString();
    }

    /**
     * getDatabase
     * Gets database by the name DB_NAME in AppDatabase.class
     */
    private void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    /**
     * snackMaker
     * @param message
     * Takes String, puts out long snack of String
     */
    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutSignUpActivity), message, Snackbar.LENGTH_LONG);
        snackBar.show();
    }

    /**
     * intentFactory
     * @param context
     * @return
     * Generates intent from this activity
     */
    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, SignupActivity.class);
        return intent;
    }

}