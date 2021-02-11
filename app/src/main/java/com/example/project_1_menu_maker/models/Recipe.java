package com.example.project_1_menu_maker.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project_1_menu_maker.db.AppDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = AppDatabase.RECIPE_TABLE)
public class Recipe {

    int MealId;
    @PrimaryKey
    private int mUserId;
    String Title;
    String Category;
    String MealThumb;
    String Area;

//    private JSONObject mJsonObject;

    public Recipe(int mUserId, JSONObject jsonObject) throws JSONException {
        this.mUserId = mUserId;
//        this.mJsonObject = jsonObject;
        MealId = jsonObject.getInt("idMeal");
        Title = jsonObject.getString("strMeal");
        Category = jsonObject.getString("strCategory");
        Area = jsonObject.getString("strArea");
        MealThumb = jsonObject.getString("strMealThumb");
    }

    public static List<Recipe> fromJsonArray(int mUserId, JSONArray recipeJsonArray) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
        for(int i = 0; i < recipeJsonArray.length(); i++){
            recipes.add(new Recipe(mUserId, recipeJsonArray.getJSONObject(i)));
        }
        return recipes;
    }

    public int getUserId() { return mUserId; }

    public void setUserId(int userId) { this.mUserId = userId; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return MealId == recipe.MealId &&
                mUserId == recipe.mUserId &&
                Objects.equals(Title, recipe.Title) &&
                Objects.equals(Category, recipe.Category) &&
                Objects.equals(MealThumb, recipe.MealThumb) &&
                Objects.equals(Area, recipe.Area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MealId, mUserId, Title, Category, MealThumb, Area);
    }
}
