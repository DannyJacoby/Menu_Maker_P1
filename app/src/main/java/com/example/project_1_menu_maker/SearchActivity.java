package com.example.project_1_menu_maker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private static final String PREFERENCES_KEY = "com.example.project_1_menu_maker.db.PREFERENCES_KEY";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    public static final String RANDOM_URL = "https://www.themealdb.com/api/json/v1/1/random.php/";
    public static final String LETTER_URL = "https://www.themealdb.com/api/json/v1/1/search.php?f=";
    public static final String TAG = "SearchActivity";

    List<Recipe> recipes;
    Button btSearch;
    Button btRandom;
    EditText etKeyword;
    Button btA, btB, btC, btD, btE, btF, btG, btH, btI, btJ, btK, btL, btM, btN, btO, btP, btQ, btR, btS, btT, btU, btV, btW, btX, btY, btZ;
    String[] a = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    private int mUserId;// = getIntent().getIntExtra("com.example.project_1_menu_maker.db.userIdKey", -1);
    private UserDAO mUserDAO;
    private RecipeDAO mRecipeDAO;

    private SharedPreferences mPreferences = null;

    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.e("User ID Coming into Search", String.valueOf(getIntent().getIntExtra(USER_ID_KEY, -1)));

        checkForUser();

        RecyclerView rmRecipes = findViewById(R.id.rvRecipes);
        recipes = new ArrayList<>();
        btSearch = findViewById(R.id.btSearch);
        etKeyword = findViewById(R.id.etKeyword);
        btRandom = findViewById(R.id.btRandom);
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

        btRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                Intent i = new Intent(SearchActivity.this, DisplayActivity.class);

                client.get(RANDOM_URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("meals");
                            Log.i(TAG, "Result: " + results.toString());
                            recipes.addAll(Recipe.fromJsonArray(results));
                            i.putExtra("recipe", Parcels.wrap(recipes.get(0)));
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

        btA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "A", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "A", Toast.LENGTH_SHORT).show();
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

        btB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "B", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "B", Toast.LENGTH_SHORT).show();
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

        btC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "C", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "C", Toast.LENGTH_SHORT).show();
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

        btD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "D", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "D", Toast.LENGTH_SHORT).show();
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

        btE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "E", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "E", Toast.LENGTH_SHORT).show();
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

        btF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "F", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "F", Toast.LENGTH_SHORT).show();
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

        btG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "G", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "G", Toast.LENGTH_SHORT).show();
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

        btH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "H", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "H", Toast.LENGTH_SHORT).show();
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

        btI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "I", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "I", Toast.LENGTH_SHORT).show();
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

        btJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "J", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "J", Toast.LENGTH_SHORT).show();
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

        btK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "K", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "K", Toast.LENGTH_SHORT).show();
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

        btL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "L", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "L", Toast.LENGTH_SHORT).show();
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

        btM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "M", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "M", Toast.LENGTH_SHORT).show();
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

        btN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "N", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "N", Toast.LENGTH_SHORT).show();
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

        btO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "O", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "O", Toast.LENGTH_SHORT).show();
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

        btP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "P", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "P", Toast.LENGTH_SHORT).show();
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

        btQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "Q", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "Q", Toast.LENGTH_SHORT).show();
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

        btR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "R", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "R", Toast.LENGTH_SHORT).show();
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

        btS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "S", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "S", Toast.LENGTH_SHORT).show();
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

        btT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "T", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "T", Toast.LENGTH_SHORT).show();
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

        btU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "U", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "U", Toast.LENGTH_SHORT).show();
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

        btV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "V", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "V", Toast.LENGTH_SHORT).show();
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

        btW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "W", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "W", Toast.LENGTH_SHORT).show();
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

        btX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "X", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "X", Toast.LENGTH_SHORT).show();
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

        btY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "Y", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "Y", Toast.LENGTH_SHORT).show();
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

        btZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                client.get(LETTER_URL + "Z", new JsonHttpResponseHandler() {
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
                            Toast.makeText(SearchActivity.this, "No meals with the letter: "+ "Z", Toast.LENGTH_SHORT).show();
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
  
    private void checkForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1){ return; }

        if(mPreferences == null){ getPrefs(); }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if(mUserId != -1){ return; }
        else {
            Log.e("Not Passing in any user in intent", String.valueOf(mUserId));
        }
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
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
  
}