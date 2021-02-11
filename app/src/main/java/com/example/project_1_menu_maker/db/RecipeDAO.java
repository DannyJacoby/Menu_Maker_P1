package com.example.project_1_menu_maker.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    void insert(Recipes... mRecipes);

    @Update
    void update(Recipes... mRecipes);

    @Delete
    void delete(Recipes mRecipe);

    @Query("SELECT * FROM " + AppDatabase.RECIPE_TABLE)
    List<Recipes> getAllRecipesInDB();

    @Query("SELECT * FROM " + AppDatabase.RECIPE_TABLE + " WHERE userId = :userId")
    List<Recipes> getAllUserRecipes(int userId);

}
