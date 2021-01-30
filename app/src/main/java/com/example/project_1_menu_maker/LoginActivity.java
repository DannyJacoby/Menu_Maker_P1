package com.example.project_1_menu_maker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.account.db.userIdKey";

    private EditText mUsername;
    private String mUsernameString;
    private EditText mPassword;
    private String mPasswordString;

    // private User mUser;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = findViewById(R.id.usernameInput);
        mPassword = findViewById(R.id.passwordInput);

        button = findViewById(R.id.button);

        getDatabase();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkForUser()){ // user exists
                    if(validatePassword()){ // password is correct
                        // passing current user id to home activity, must be passed along to search and display activities when clicked from home
//                        Intent intent = HomeActivity.intentFactory(getApplicationContext(), -1/* mUser.getUserId() */);
//                        startActivity(intent);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

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
            }
        });

    }

    private boolean checkForUser(){
        mUsernameString = mUsername.getText().toString();
        /* something like
        mUser = mUsersDAO.getUserByUsername(mUsernameString);
         */

        return false;
    }

    private boolean validatePassword(){
        /* something like
        return mUser.getUserPassword().equals(mPasswordString);
         */

        return false;
    }

    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutLoginActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    private void getDatabase(){
        /* something like
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).allowMainThreadQueries().build().getUserDAO();
         */
    }

    // to send the userId to the menu adder section so we can add recipes to an account db
    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}