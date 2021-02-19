package com.example.project_1_menu_maker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.RecipeDAO;
import com.example.project_1_menu_maker.db.Recipes;
import com.example.project_1_menu_maker.db.UserDAO;
import com.example.project_1_menu_maker.models.Recipe;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

public class UserDetailsActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";

    TextView tvMealTitle;
    ImageView ivImage;
    TextView tvInstruction;
    TextView tvIngredient;
    Context context;
    boolean userDisplay = false;

    private ImageButton mFavoriteBtn;
    private boolean hasBeenSaved = false;

    private Recipe mRecipe;
    private Recipes myRecipes;
    private RecipeDAO mRecipeDAO;

    private UserDAO mUserDAO;
    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        getDatabase();

        loginUser();

        wireUp();

        loadRecipe();

    }

    /**
     * wireUp
     * Basic wire up functions, wires up all fields and buttons as well as unwraps recipe from intent
     */
    private void wireUp(){
        myRecipes = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        tvMealTitle = findViewById(R.id.tvMealTitle);
        tvIngredient = findViewById(R.id.tvIngredient);
        tvInstruction = findViewById(R.id.tvInstruction);
        tvInstruction.setMovementMethod(new ScrollingMovementMethod());
        tvIngredient.setMovementMethod(new ScrollingMovementMethod());
        ivImage = findViewById(R.id.ivImage);

        mFavoriteBtn = findViewById(R.id.favoriteButtonDisplay);

        //set this with the "hasBeenSaved" variable (set that with a checkForRecipeInDB call or smth)
        mFavoriteBtn.setImageResource(android.R.drawable.btn_star_big_off);

        mFavoriteBtn.setOnClickListener(v -> {
            if(!hasBeenSaved) {
                snackMaker("You saved this recipe!");
                mFavoriteBtn.setImageResource(android.R.drawable.star_big_on);
                hasBeenSaved = true;
                // insert recipes to db
                addRecipeToUser();
            } else {
                snackMaker("You unsaved this recipe!");
                mFavoriteBtn.setImageResource(android.R.drawable.star_big_off);
                hasBeenSaved = false;
                // delete recipes from db
                Log.e("DisplayActivity current recipe brought in", String.valueOf(myRecipes.getMealId()));
                deleteRecipeFromUser();
            }
        });
    }

    /**
     * loginUser
     * Does as function says and unwraps userId from intent
     */
    private void loginUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        if(mUserId == -1){
            Log.e("Big Fuckin error with", "DisplayActivity, Login Error (no user in intent)");
        }
    }

    /**
     * addRecipeToUser
     * Inserts recipe
     */
    private void addRecipeToUser(){
        mRecipeDAO.insert(myRecipes);
    }

    /**
     * deleteRecipeFromUser
     * Deletes user and mealId specific recipe
     */
    private void deleteRecipeFromUser(){
        mRecipeDAO.deleteUserSpecificRecipeInDB(mUserId, myRecipes.getMealId());
    }

    /**
     * checkForRecipeInDB
     * @return
     * Returns true/false depending on if recipe is already in DB for this user
     */
    private boolean checkForRecipeInDB(){
        // use mRecipe and mUserId to check through DB
        List<Recipes> myRecipeA = mRecipeDAO.getAllUserRecipes(mUserId);
        for(Recipes myRecipe : myRecipeA){
            if(myRecipe.getMealId() == myRecipes.getMealId()){
                return true;
            }
        }
        return false;
    }

    /**
     * loadRecipe
     * Loads recipe into global variable, also loads it into activity
     */
    private void loadRecipe(){
        myRecipes = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        if(myRecipes == null){
            Log.e("Big Fuckin error with", "DisplayActivity, Load Recipe (recipe is not in extra)");
            return;
        }

        // if there is a recipe with this meal id in the db with this user id, then BOOM do the thing earlier
        if(checkForRecipeInDB()){
            hasBeenSaved = true;
            mFavoriteBtn.setImageResource(android.R.drawable.star_big_on);
        }


        tvMealTitle.setText(myRecipes.getTitle());
        String ImageUrl = myRecipes.getMealThumb();

        Log.d("Image", "bind: "+ ImageUrl);
        Picasso.get().load(ImageUrl).into(ivImage);
        tvIngredient.setText("Ingredients:\n" + myRecipes.getIngredients());
        tvInstruction.setText(myRecipes.getInstruction());
    }

    /**
     * snackMaker
     * @param message
     * Makes short snack based on message
     */
    private void snackMaker(String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutDisplayActivity), message, Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    /**
     * getDatabase
     * Gets User and Recipe DB
     */
    private void getDatabase(){
        mRecipeDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getRecipeDAO();

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    /**
     * intentFactory
     * @param context
     * @param userId
     * @param recipe
     * @return
     * Gets intent directed at this class and wraps returned intent with userId and recipe
     */
    public static Intent intentFactory(Context context, int userId, Recipes recipe){
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        intent.putExtra("recipe", Parcels.wrap(recipe));
        return intent;
    }


}