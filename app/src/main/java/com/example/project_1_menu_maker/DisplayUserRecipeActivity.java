package com.example.project_1_menu_maker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.project_1_menu_maker.adapters.UserRecipeAdapter;
import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.RecipeDAO;
import com.example.project_1_menu_maker.db.Recipes;
import com.example.project_1_menu_maker.db.User;
import com.example.project_1_menu_maker.db.UserDAO;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DisplayUserRecipeActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";

    private int mUserId;
    private User mUser;
    private UserDAO mUserDAO;
    List<Recipes> recipes;

    private RecipeDAO mRecipeDAO;

    private Button mHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_recipe);

        wireUp();

        getDatabase();

        checkForUser();

        Log.e("User Id coming into DisplayUserRecipes is ", String.valueOf(mUserId));

        RecyclerView rmRecipe = findViewById(R.id.rvRecipe);
        recipes = new ArrayList<>();


        final UserRecipeAdapter recipeAdapter = new UserRecipeAdapter(this, recipes, mUserId);

        rmRecipe.setAdapter(recipeAdapter);

        rmRecipe.setLayoutManager(new LinearLayoutManager(this));

        recipes.addAll(mRecipeDAO.getAllUserRecipes(mUserId));

        recipeAdapter.notifyDataSetChanged();

    }

    /**
     * wireUp
     * Basic wireUp function to wire up all buttons
     */
    private void wireUp(){
        mHomeBtn = findViewById(R.id.homeBtnDisplayUser);
        mHomeBtn.setOnClickListener(v -> {
            Intent intent = HomeActivity.intentFactory(getApplicationContext(), mUserId);
            startActivity(intent);
        });

    }

    /**
     * loginUser
     * @param userId
     * Login user by grabbing them from the DB
     */
    private void loginUser(int userId) {
        mUser = mUserDAO.getUserByUserId(userId);
    }

    /**
     * checkForUser
     * Checks for userId in intent, if it's there we call loginUser else we go to mainActivity
     */
    private void checkForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1){
            loginUser(mUserId);
            return;
        }

        snackMaker("Error, no user logged in");
        Intent intent = MainActivity.intentFactory(this);
        startActivity(intent);

    }

    /**
     * snackMaker
     * @param message
     * Takes in String, creates short snack with String
     */
    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutDisplayUserActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    /**
     * getDatabase
     * Gets database by the name DB_NAME in AppDatabase.class for User and Recipe DBs
     */
    private void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        mRecipeDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getRecipeDAO();
    }

    /**
     * intentFactory
     * @param context
     * @param userId
     * @return
     * Takes in userId and wraps it into the intent, returns intent to this class
     */
    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, DisplayUserRecipeActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}