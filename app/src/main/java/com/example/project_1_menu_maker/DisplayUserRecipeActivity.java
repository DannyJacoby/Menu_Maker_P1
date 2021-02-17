package com.example.project_1_menu_maker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.project_1_menu_maker.adapters.RecipeAdapter;
import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.RecipeDAO;
import com.example.project_1_menu_maker.db.Recipes;
import com.example.project_1_menu_maker.db.User;
import com.example.project_1_menu_maker.db.UserDAO;
import com.example.project_1_menu_maker.models.Recipe;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class DisplayUserRecipeActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";

    private int mUserId;
    private User mUser;
    private UserDAO mUserDAO;
    List<Recipes> recipes;

    private RecipeDAO mRecipeDAO;

    private Button mHomeBtn;

    // note, use mRecipeDAO.getAllUserRecipes(mUserId); to get a List<Recipes> that then you can iterate through

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


        final RecipeAdapter2 recipeAdapter = new RecipeAdapter2(this, recipes, mUserId);

        rmRecipe.setAdapter(recipeAdapter);

        rmRecipe.setLayoutManager(new LinearLayoutManager(this));

        recipes.addAll(mRecipeDAO.getAllUserRecipes(mUserId));

        recipeAdapter.notifyDataSetChanged();



    }

    private void wireUp(){
        mHomeBtn = findViewById(R.id.homeBtnDisplayUser);
        mHomeBtn.setOnClickListener(v -> {
            Intent intent = HomeActivity.intentFactory(getApplicationContext(), mUserId);
            startActivity(intent);
        });

    }

    private void loginUser(int userId) {
        mUser = mUserDAO.getUserByUserId(userId);
    }

    private void checkForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1){
            loginUser(mUserId);
            return;
        }
//        if(mPreferences == null){
//            getPrefs();
//        }
//        mUserId = mPreferences.getInt(USER_ID_KEY, -1);
//        if(mUserId != -1){
//            loginUser(mUserId);
//            return;
//        }

        snackMaker("Error, no user logged in");
        Intent intent = MainActivity.intentFactory(this);
        startActivity(intent);

    }



    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutDisplayUserActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

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

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, DisplayUserRecipeActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}