package com.example.project_1_menu_maker.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.project_1_menu_maker.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "db-menu";
    public static final String USER_TABLE = "user_table";
    public static final String RECIPE_TABLE = "recipe_table";
    public abstract UserDAO getUserDAO();

}
