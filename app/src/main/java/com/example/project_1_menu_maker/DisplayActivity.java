package com.example.project_1_menu_maker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    TextView tvMealTitle;
    ImageView ivImage;
    TextView tvInstruction;
    TextView tvMeasurement;
    TextView tvIngredient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
    }
}