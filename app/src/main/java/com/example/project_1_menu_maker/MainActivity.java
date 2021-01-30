package com.example.project_1_menu_maker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btLogin;
    private Button btSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogin = findViewById(R.id.btLogin);
        btSignUp = findViewById(R.id.btSignup);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });
    }

    private void checkForUsers(){
        /* something like
        //do we have any users at all?
        List<User> users = mUsersDAO.getAllUsers();
        if(users.size() <= 0){
            User defaultUser = new User("default", "default");
            User altUser = new User("alt", "alt");
            mUsersDAO.insert(defaultUser, altUser);
        }
         */
    }

    private void getDatabase(){
        /* something like
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).allowMainThreadQueries().build().getUserDAO();
         */
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }


}