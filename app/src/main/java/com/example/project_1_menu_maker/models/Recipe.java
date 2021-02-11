package com.example.project_1_menu_maker.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Parcel
public class Recipe {

    int MealId;
    String Title;
    String Category;
    String MealThumb;
    String Area;
    String Ingredients = "";
    String Instruction;

    public Recipe(){}

    public Recipe( JSONObject jsonObject ) throws JSONException {
        MealId = jsonObject.getInt("idMeal");
        Title = jsonObject.getString("strMeal");
        Category = jsonObject.getString("strCategory");
        Area = jsonObject.getString("strArea");
        MealThumb = jsonObject.getString("strMealThumb");
        for (int i = 1; i < 21; i++){
            if (jsonObject.getString("strIngredient"+String.valueOf(i)).equals("")){
                break;
            }
            Ingredients += jsonObject.getString("strIngredient"+String.valueOf(i)) + "   " +  jsonObject.getString("strMeasure"+String.valueOf(i)) + "\n";
        }
        Instruction = jsonObject.getString("strInstructions");

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

    public String getIngredients() {
        return Ingredients;
    }

    public String getInstruction() { return Instruction; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return MealId == recipe.MealId &&
                Objects.equals(Title, recipe.Title) &&
                Objects.equals(Category, recipe.Category) &&
                Objects.equals(MealThumb, recipe.MealThumb) &&
                Objects.equals(Area, recipe.Area) &&
                Objects.equals(Ingredients, recipe.Ingredients) &&
                Objects.equals(Instruction, recipe.Instruction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MealId, Title, Category, MealThumb, Area, Ingredients, Instruction);
    }
}
