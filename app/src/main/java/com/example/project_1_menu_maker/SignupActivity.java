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

public class SignupActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;


    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUsername = findViewById(R.id.usernameInput);
        mPassword = findViewById(R.id.passwordInput);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkForUser()){ // user does not exist thus head down registration path
                    createUser();
                    snackMaker("User Created");
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

            }
        });

    }

    // waiting on account dao/db
    private boolean checkForUser(){


        return true;
    }

    //waiting on account dao/db
    private void createUser(){

    }

    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutSignUpActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, SignupActivity.class);
        return intent;
    }

}