package com.example.project_1_menu_maker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

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
import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.RecipeDAO;
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

public class SearchActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    public static final String RANDOM_URL = "https://www.themealdb.com/api/json/v1/1/random.php/";
    public static final String LETTER_URL = "https://www.themealdb.com/api/json/v1/1/search.php?f=";
    public static final String TAG = "SearchActivity";

    List<Recipe> recipes;
    Button btSearch;
    Button btRandom;
    EditText etKeyword;
    Button btA, btB, btC, btD, btE, btF, btG, btH, btI, btJ, btK, btL, btM, btN, btO, btP, btQ, btR, btS, btT, btU, btV, btW, btX, btY, btZ;
    String[] a = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private int mUserId;
    private UserDAO mUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        checkForUser();

        RecyclerView rmRecipes = findViewById(R.id.rvRecipes);
        recipes = new ArrayList<>();

        final RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipes, mUserId);

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

                    recipes.addAll(Recipe.fromJsonArray(results));

                    recipeAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Recipes: " + recipes.size());
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

        buttonWireUp(recipeAdapter, client); // Replace this with the internals of this function if we need to put all buttons back into onCreate

    }

    /**
     * buttonWireUp
     * @param recipeAdapter
     * @param client
     *
     */
    private void buttonWireUp(RecipeAdapter recipeAdapter, AsyncHttpClient client){

        btSearch = findViewById(R.id.btSearch);
        etKeyword = findViewById(R.id.etKeyword);
        btRandom = findViewById(R.id.btRandom);
        // Pull down menu possible replacement
        // Make a class that inherits button class, then this specific button class then has an override based on button creation
        // ie a class that is button letter and is set up as such
        btA = findViewById(R.id.btA);
        btB = findViewById(R.id.btB);
        btC = findViewById(R.id.btC);
        btD = findViewById(R.id.btD);
        btE = findViewById(R.id.btE);
        btF = findViewById(R.id.btF);
        btG = findViewById(R.id.btG);
        btH = findViewById(R.id.btH);
        btI = findViewById(R.id.btI);
        btJ = findViewById(R.id.btJ);
        btK = findViewById(R.id.btK);
        btL = findViewById(R.id.btL);
        btM = findViewById(R.id.btM);
        btN = findViewById(R.id.btN);
        btO = findViewById(R.id.btO);
        btP = findViewById(R.id.btP);
        btQ = findViewById(R.id.btQ);
        btR = findViewById(R.id.btR);
        btS = findViewById(R.id.btS);
        btT = findViewById(R.id.btT);
        btU = findViewById(R.id.btU);
        btV = findViewById(R.id.btV);
        btW = findViewById(R.id.btW);
        btX = findViewById(R.id.btX);
        btY = findViewById(R.id.btY);
        btZ = findViewById(R.id.btZ);


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
                            Log.i(TAG, "Recipes: " + recipes.size());
                        } catch (JSONException e) {
                            Log.e(TAG, "Hit json exception" + e);
                            Toast.makeText(SearchActivity.this, "No meals with the keyword: " + etKeyword.getText(), Toast.LENGTH_SHORT).show();
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

        btRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
//                Intent i = new Intent(SearchActivity.this, DetailsActivity.class);

                client.get(RANDOM_URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("meals");
                            Log.i(TAG, "Result: " + results.toString());
                            recipes.addAll(Recipe.fromJsonArray(results));
//                            i.putExtra("recipe", Parcels.wrap(recipes.get(0)));
                            Intent i = DetailsActivity.intentFactory(getApplicationContext(), mUserId, recipes.get(0));
                            startActivity(i);
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
            }
        });

        buttonWireUp(client, recipeAdapter, btA, "A");
        buttonWireUp(client, recipeAdapter, btB, "B");
        buttonWireUp(client, recipeAdapter, btC, "C");
        buttonWireUp(client, recipeAdapter, btD, "D");
        buttonWireUp(client, recipeAdapter, btE, "E");
        buttonWireUp(client, recipeAdapter, btF, "F");
        buttonWireUp(client, recipeAdapter, btG, "G");
        buttonWireUp(client, recipeAdapter, btH, "H");
        buttonWireUp(client, recipeAdapter, btI, "I");
        buttonWireUp(client, recipeAdapter, btJ, "J");
        buttonWireUp(client, recipeAdapter, btK, "K");
        buttonWireUp(client, recipeAdapter, btL, "L");
        buttonWireUp(client, recipeAdapter, btM, "M");
        buttonWireUp(client, recipeAdapter, btN, "N");
        buttonWireUp(client, recipeAdapter, btO, "O");
        buttonWireUp(client, recipeAdapter, btP, "P");
        buttonWireUp(client, recipeAdapter, btQ, "Q");
        buttonWireUp(client, recipeAdapter, btR, "R");
        buttonWireUp(client, recipeAdapter, btS, "S");
        buttonWireUp(client, recipeAdapter, btT, "T");
        buttonWireUp(client, recipeAdapter, btU, "U");
        buttonWireUp(client, recipeAdapter, btV, "V");
        buttonWireUp(client, recipeAdapter, btW, "W");
        buttonWireUp(client, recipeAdapter, btX, "X");
        buttonWireUp(client, recipeAdapter, btY, "Y");
        buttonWireUp(client, recipeAdapter, btZ, "Z");
    }

    /**
     * buttonWireUp
     * @param client
     * @param recipeAdapter
     * @param button
     * @param string
     *
     */
    private void buttonWireUp(AsyncHttpClient client, RecipeAdapter recipeAdapter, Button button, String string) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + string, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("meals");
                            Log.i(TAG, "Result: " + results.toString());
                            recipes.addAll(Recipe.fromJsonArray(results));
                            recipeAdapter.notifyDataSetChanged();
                            Log.i(TAG, "Recipes: " + recipes.size());
                        } catch (JSONException e) {
                            Log.e(TAG, "Hit json exception" + e);
                            Toast.makeText(SearchActivity.this, "No meals with the letter: " + string, Toast.LENGTH_SHORT).show();
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

    /**
     * checkForUser
     * Does as function says and checks for user in intent
     */
    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if (mUserId != -1) {
            return;
        }

        if (mUserId != -1) {
            return;
        } else {
            Log.e("Not Passing in any user in intent", String.valueOf(mUserId));
        }
    }

    /**
     * snackMaker
     * @param message
     */
    private void snackMaker (String message){
        Snackbar snackBar = Snackbar.make(findViewById(R.id.layoutSearchActivity),
                message,
                Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    /**
     * intentFactory
     * @param context
     * @param userId
     * @return
     */
    public static Intent intentFactory (Context context, int userId){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

}