package com.example.project_1_menu_maker.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_1_menu_maker.models.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    void insert(Recipes... mRecipes);

    @Update
    void update(Recipes... mRecipes);

    @Delete
    void delete(Recipes mRecipes);

    @Query("SELECT * FROM " + AppDatabase.RECIPE_TABLE)
    List<Recipes> getAllRecipesInDB();

    @Query("SELECT * FROM " + AppDatabase.RECIPE_TABLE + " WHERE userId = :userId")
    List<Recipes> getAllUserRecipes(int userId);

    @Query("SELECT * FROM " + AppDatabase.RECIPE_TABLE + " WHERE menuId = :menuId")
    List<Recipes> getAllRecipesById(int menuId);

    @Query("SELECT * FROM " + AppDatabase.RECIPE_TABLE + " WHERE userId = :userId AND menuId = :mealId")
    Recipes getUserSpecificRecipeInDB(int userId, int mealId);

    @Query("DELETE FROM " + AppDatabase.RECIPE_TABLE + " WHERE userId = :userId AND menuid = :mealId")
    void deleteUserSpecificRecipeInDB(int userId, int mealId);
}
