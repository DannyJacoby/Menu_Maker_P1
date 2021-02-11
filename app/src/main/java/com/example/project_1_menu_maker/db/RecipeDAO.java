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
    void insert(Recipe... recipes);

    @Update
    void update(Recipe... recipes);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM " + AppDatabase.RECIPE_TABLE)
    List<Recipe> getAllRecipesInDB();

    @Query("SELECT * FROM " + AppDatabase.RECIPE_TABLE + " WHERE mUserId = :userId")
    List<Recipe> getAllUserRecipes(int userId);

}
