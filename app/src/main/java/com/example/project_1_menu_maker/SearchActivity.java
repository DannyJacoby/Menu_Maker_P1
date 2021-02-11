package com.example.project_1_menu_maker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.project_1_menu_maker.adapters.RecipeAdapter;
import com.example.project_1_menu_maker.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class SearchActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    public static final String TAG = "SearchActivity";

    List<Recipe> recipes;
    Button btSearch;
    EditText etKeyword;

//    private int mUserId = getIntent().getIntExtra("com.example.project_1_menu_maker.db.userIdKey", -1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        RecyclerView rmRecipes = findViewById(R.id.rvRecipes);
        recipes = new ArrayList<>();
        btSearch = findViewById(R.id.btSearch);
        etKeyword = findViewById(R.id.etKeyword);
        final RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipes);

        rmRecipes.setAdapter(recipeAdapter);

        rmRecipes.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("meals");
                    Log.i(TAG, "Result: " + results.toString());
//                    if(mUserId == -1) mUserId = 1;

                    recipes.addAll(Recipe.fromJsonArray(results));

                    recipeAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Recipes: "+ recipes.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception" + e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(BASE_URL + etKeyword.getText(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("meals");
                            Log.i(TAG, "Result: " + results.toString());
                            recipes.addAll(Recipe.fromJsonArray(results));
                            recipeAdapter.notifyDataSetChanged();
                            Log.i(TAG, "Recipes: "+ recipes.size());
                        } catch (JSONException e) {
                            Log.e(TAG, "Hit json exception" + e);
                            Toast.makeText(SearchActivity.this, "No meals with the keyword: "+ etKeyword.getText(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.d(TAG, "onFailure");
                    }
                });
            }
        });




    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("com.example.project_1_menu_maker.db.userIdKey", userId);
        return intent;
    }
  
}