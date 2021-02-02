package com.example.project_1_menu_maker.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    int MealId;
    String Title;
    String Category;
    String MealThumb;
    String Area;

    public Recipe() {}


    public Recipe(JSONObject jsonObject) throws JSONException {
        MealId = jsonObject.getInt("idMeal");
        Title = jsonObject.getString("strMeal");
        Category = jsonObject.getString("strCategory");
        Area = jsonObject.getString("strArea");
        MealThumb = jsonObject.getString("strMealThumb");
    }

    public static List<Recipe> fromJsonArray(JSONArray recipeJsonArray) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
        for(int i = 0; i < recipeJsonArray.length(); i++){
            recipes.add(new Recipe(recipeJsonArray.getJSONObject(i)));
        }
        return recipes;
    }

    public int getMealId() {
        return MealId;
    }

    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getMealThumb() {
        return MealThumb;
    }

    public String getArea() {
        return Area;
    }
}
