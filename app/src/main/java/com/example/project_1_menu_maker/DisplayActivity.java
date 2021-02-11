package com.example.project_1_menu_maker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.RecipeDAO;
import com.example.project_1_menu_maker.models.Recipe;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

public class DisplayActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";

    TextView tvMealTitle;
    ImageView ivImage;
    TextView tvInstruction;
    TextView tvMeasurement;
    TextView tvIngredient;

    private Recipe mRecipe;
    private RecipeDAO mRecipeDAO;

    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        wireUp();

    }


    private void wireUp(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        if(mUserId == -1) mUserId = 1;
    }

    private void addRecipeToUser(){
        // here convert JSON Recipe to DB Safe Recipe class[Recipes] (grab all data types of JSON and create a new object of Recipes
    }

    private JSONObject getRecipeAPI(){
        JSONObject jsonObject = new JSONObject();

        return jsonObject;
    }

    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutLoginActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    private void getDatabase(){
        mRecipeDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getRecipeDAO();
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("com.example.project_1_menu_maker.db.userIdKey", userId);
        return intent;
    }

}