package com.example.project_1_menu_maker.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.parceler.Parcel;

import java.util.Objects;

@Parcel
@Entity(tableName = AppDatabase.RECIPE_TABLE)
public class Recipes {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int recipeId;

    private int userId;
    private int mealId;
    private String title;
    private String category;
    private String mealThumb;
    private String area;
    private String ingredients;
    private String instruction;

    public Recipes(){}

    public Recipes(int userId, int mealId, String title, String category, String mealThumb, String area, String ingredients, String instruction) {
        this.userId = userId;
        this.mealId = mealId;
        this.title = title;
        this.category = category;
        this.mealThumb = mealThumb;
        this.area = area;
        this.ingredients = ingredients;
        this.instruction = instruction;
    }

    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMealId() {
        return mealId;
    }
    public void setMealId(int menuId) {
        this.mealId = menuId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getMealThumb() {
        return mealThumb;
    }
    public void setMealThumb(String mealThumb) {
        this.mealThumb = mealThumb;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public String getIngredients() {
        return ingredients;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipes recipes = (Recipes) o;
        return userId == recipes.userId &&
                mealId == recipes.mealId &&
                Objects.equals(title, recipes.title) &&
                Objects.equals(category, recipes.category) &&
                Objects.equals(mealThumb, recipes.mealThumb) &&
                Objects.equals(area, recipes.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, mealId, title, category, mealThumb, area);
    }
}
