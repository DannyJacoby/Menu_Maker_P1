package com.example.project_1_menu_maker;

import android.content.Context;
import android.content.Intent;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project_1_menu_maker.db.AppDatabase;
import com.example.project_1_menu_maker.db.RecipeDAO;
import com.example.project_1_menu_maker.db.Recipes;
import com.example.project_1_menu_maker.db.User;
import com.example.project_1_menu_maker.db.UserDAO;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void factoryHomeCreationTest(){
        Context testContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = HomeActivity.intentFactory(testContext, 5);

        assertEquals(5, intent.getIntExtra("com.example.project_1_menu_maker.db.userIdKey", -1));
    }

    @Test
    public void userInsertTest(){
        Context testContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDAO myTestDB = Room.databaseBuilder(testContext, AppDatabase.class, AppDatabase.USER_TABLE).allowMainThreadQueries().build().getUserDAO();

        User testUser = new User("bitch", "lasanga");

        if(myTestDB.getUserByUsername("bitch") != null){
            myTestDB.delete(testUser);
        }
        myTestDB.insert(testUser);
        User goalUser = myTestDB.getUserByUsername("bitch");
        assertEquals(testUser, goalUser);
    }

    @Test
    public void recipeInsertTest(){
        Context testContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RecipeDAO myTestDB = Room.databaseBuilder(testContext, AppDatabase.class, AppDatabase.RECIPE_TABLE).allowMainThreadQueries().build().getRecipeDAO();

        //int userId, int menuId, String title, String category, String mealThumb, String area, String ingredients, String instruction
        Recipes testRecipe = new Recipes(10, 10, "fake", "garbage", "fakeImage", "fakeWords","trash","throw me away");

        // so we don't get a bunch of duplicates
        if(myTestDB.getUserSpecificRecipeInDB(10, 10) != null){
            myTestDB.delete(testRecipe);
        }
        myTestDB.insert(testRecipe);
        Recipes goalRecipe = myTestDB.getUserSpecificRecipeInDB(10,10);
        assertEquals(testRecipe, goalRecipe);
    }

    @Test
    public void recipeDeletionTest(){
        Context testContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RecipeDAO myTestDB = Room.databaseBuilder(testContext, AppDatabase.class, AppDatabase.RECIPE_TABLE).allowMainThreadQueries().build().getRecipeDAO();

        //int userId, int menuId, String title, String category, String mealThumb, String area, String ingredients, String instruction
        Recipes testRecipe = new Recipes(10, 10, "fake", "garbage", "fakeImage", "fakeWords","trash","throw me away");

        // so we don't get a bunch of duplicates
        if(myTestDB.getUserSpecificRecipeInDB(10, 10) != null){
            myTestDB.delete(testRecipe);
        }
        myTestDB.insert(testRecipe);
        myTestDB.delete(testRecipe);
        Recipes goalRecipe = myTestDB.getUserSpecificRecipeInDB(10,10);

        assertEquals(goalRecipe, null);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.project_1_menu_maker", appContext.getPackageName());

    }
}