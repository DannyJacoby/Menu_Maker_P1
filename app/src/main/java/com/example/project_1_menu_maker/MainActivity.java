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
    private static final String PREFERENCES_KEY = "com.example.project_1_menu_maker.db.PREFERENCES_KEY";

    private Button btLogin;
    private Button btSignUp;

    private User mUser;
    private UserDAO mUserDAO;
    private int mUserId = -1;

    private RecipeDAO mRecipeDAO;

    private SharedPreferences mPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();
        getPrefs();
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

    private void checkForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1){ return; }

        if(mPreferences == null){ getPrefs(); }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if(mUserId != -1){ return; }

        List<User> users = mUserDAO.getAllUsers();

        if(users.size() <= 0){
            User defaultUser = new User("default", "default");
            User altDeftUser = new User("altdeft", "altdeft");
            mUserDAO.insert(defaultUser, altDeftUser);
        }

//        // Go to Login Screen // don't need this since we have a login/sign up btn
//        Intent intent = LoginActivity.intentFactory(this);
//        startActivity(intent);
    }

    private void loginUser(int userId) { mUser = mUserDAO.getUserByUserId(userId); }

    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Yes", (dialog, which) -> {
            clearUserFromIntent();
            clearUserFromPrefs();
            mUserId = -1;
            checkForUser();
        });

        alertBuilder.setNegativeButton("No", (dialog, which) -> {
            //Don't need to do anything here
            snackMaker("You clicked NO");
        });

        alertBuilder.create().show();
    }

    private void getPrefs(){ mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE); }

    private void addUserToPrefs(int userId){
        if(mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void clearUserFromPrefs(){ addUserToPrefs(-1); }

    private void clearUserFromIntent(){ getIntent().putExtra(USER_ID_KEY, -1); }

    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutActivityMain),
                message,
                Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    private void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).allowMainThreadQueries().build().getUserDAO();
//        mRecipeDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).allowMainThreadQueries().build().getRecipeDAO();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }


}