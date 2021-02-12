package com.example.project_1_menu_maker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project_1_menu_maker.db.Recipes;
import com.example.project_1_menu_maker.db.UserDAO;
import com.example.project_1_menu_maker.models.Recipe;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.RecipeDAO;
import com.example.project_1_menu_maker.models.Recipe;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

public class DisplayActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.project_1_menu_maker.db.PREFERENCES_KEY";

    TextView tvMealTitle;
    ImageView ivImage;
    TextView tvInstruction;
    TextView tvIngredient;
    Context context;

//    private Recipe mRecipe;
    private RecipeDAO mRecipeDAO;

    private UserDAO mUserDAO;

    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        getDatabase();

        wireUp();

        tvMealTitle = findViewById(R.id.tvMealTitle);
        tvIngredient = findViewById(R.id.tvIngredient);
        tvInstruction = findViewById(R.id.tvInstruction);
        tvInstruction.setMovementMethod(new ScrollingMovementMethod());
        tvIngredient.setMovementMethod(new ScrollingMovementMethod());
        ivImage = findViewById(R.id.ivImage);

        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

        tvMealTitle.setText(recipe.getTitle());
        String ImageUrl = recipe.getMealThumb();

        Log.d("Image", "bind: "+ ImageUrl);
        Picasso.get().load(ImageUrl).into(ivImage);
        tvIngredient.setText(recipe.getIngredients());
        tvInstruction.setText(recipe.getInstruction());

    }


    private void wireUp(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
//        Log.e("")
    }

    private void addRecipeToUser(Recipe recipe){
        // here convert JSON Recipe to DB Safe Recipe class[Recipes] (grab all data types of JSON and create a new object of Recipe
        // add a check here to make sure we're not adding in 2 of the same recipes to a user
        //  (ie if recipesId is in table and if this recipes userId == this.userId)
        //
//        Recipes recipes = new Recipes(mUserId, recipe.getMealId(), recipe.getTitle(), recipe.getCategory()....);
//        mRecipeDAO.insert(recipes);
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

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    public static Intent intentFactory(Context context, int userId, Recipe recipe){
        Intent intent = new Intent(context, DisplayActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        intent.putExtra("recipe", Parcels.wrap(recipe));
        return intent;
    }

}