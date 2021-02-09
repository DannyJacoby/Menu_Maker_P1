package com.example.project_1_menu_maker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.UserDAO;
import com.example.project_1_menu_maker.models.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.account.db.userIdKey";

    private EditText mUsernameField;
    private String mUsernameString;
    private EditText mPasswordField;
    private String mPasswordString;

    private User mUser;
    private UserDAO mUserDAO;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getDatabase();

        wireUp();

    }

    private void wireUp(){
        mUsernameField = findViewById(R.id.usernameInput);
        mPasswordField = findViewById(R.id.passwordInput);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            getValuesFromDisplay();
            if(checkForUser()){ // user exists
                if(validatePassword()){ // password is correct
                    // passing current user id to home activity, must be passed along to search and display activities when clicked from home
                    Intent intent = HomeActivity.intentFactory(getApplicationContext(), mUser.getUserId() );
                    startActivity(intent);

                } else { // password is incorrect
                    snackMaker("Invalid Password");
                }
            } else { // user doesn't exist
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(findViewById(R.id.layoutLoginActivity).getContext());
                alertBuilder.setMessage("No user found, create an account?");

                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = SignupActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
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

    private void getValuesFromDisplay(){
        mPasswordString = mPasswordField.getText().toString();
        mUsernameString = mUsernameField.getText().toString();
        Log.d("User Attempt ", mUsernameString);
        Log.d("User Pass ", mPasswordString);
    }

    private boolean checkForUser(){
        mUser = mUserDAO.getUserByUsername(mUsernameString);

        if(mUser == null){
            mUsernameField.setError("Invalid Username");
            Log.e("Wrong User/Null User", "Dumb error");
            return false;
        }
        return true;
    }

    private boolean validatePassword(){ return mUser.getPassword().equals(mPasswordString); }

    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutLoginActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    private void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    // to send the userId to the menu adder section so we can add recipes to an account db
    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}