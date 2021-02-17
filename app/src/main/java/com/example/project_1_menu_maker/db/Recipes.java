package com.example.project_1_menu_maker.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = AppDatabase.RECIPE_TABLE)
public class Recipes {

    @PrimaryKey
    private int userId;

    private int menuId;
    private String title;
    private String category;
    private String mealThumb;
    private String area;
    private String ingredients;
    private String instruction;

    public Recipes(int userId, int menuId, String title, String category, String mealThumb, String area, String ingredients, String instruction) {
        this.userId = userId;
        this.menuId = menuId;
        this.title = title;
        this.category = category;
        this.mealThumb = mealThumb;
        this.area = area;
        this.ingredients = ingredients;
        this.instruction = instruction;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMenuId() {
        return menuId;
    }
    public void setMenuId(int menuId) {
        this.menuId = menuId;
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
                menuId == recipes.menuId &&
                Objects.equals(title, recipes.title) &&
                Objects.equals(category, recipes.category) &&
                Objects.equals(mealThumb, recipes.mealThumb) &&
                Objects.equals(area, recipes.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, menuId, title, category, mealThumb, area);
    }
}
