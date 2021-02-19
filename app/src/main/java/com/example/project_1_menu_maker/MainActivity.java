package com.example.project_1_menu_maker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.RecipeDAO;
import com.example.project_1_menu_maker.db.UserDAO;
import com.example.project_1_menu_maker.db.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";

    private Button btLogin;
    private Button btSignUp;

    private UserDAO mUserDAO;
    private int mUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();
        checkForUser();

        btLogin = findViewById(R.id.btLogin);
        btSignUp = findViewById(R.id.btSignup);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });
    }

    /**
     * checkForUser
     * unwraps user id from intent, if no user is in intent then we add in default users
     */
    private void checkForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1){ return; }

        List<User> users = mUserDAO.getAllUsers();

        if(users.size() <= 0){
            User defaultUser = new User("default", "default");
            User altDeftUser = new User("altdeft", "altdeft");
            mUserDAO.insert(defaultUser, altDeftUser);
        }
    }

    /**
     * snackMaker
     * @param message
     * Makes short snack for this activity
     */
    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutActivityMain),
                message,
                Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    /**
     * getDatabase
     * Does as function states, but only grabs user DB
     */
    private void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).allowMainThreadQueries().build().getUserDAO();
    }

    /**
     * intentFactory
     * @param context
     * @return
     * Returns intent directed towards this class
     */
    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }


}