package com.example.project_1_menu_maker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project_1_menu_maker.models.Recipe;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class DisplayActivity extends AppCompatActivity {

    TextView tvMealTitle;
    ImageView ivImage;
    TextView tvInstruction;
    TextView tvIngredient;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        tvMealTitle = findViewById(R.id.tvMealTitle);
        tvIngredient = findViewById(R.id.tvIngredient);
        tvInstruction = findViewById(R.id.tvInstruction);
        tvInstruction.setMovementMethod(new ScrollingMovementMethod());
        tvIngredient.setMovementMethod(new ScrollingMovementMethod());
        ivImage = findViewById(R.id.ivImage);

        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        tvMealTitle.setText(recipe.getTitle());
        String ImageUrl = recipe.getMealThumb();

        Log.d("Image", "bind: "+ ImageUrl);
        Picasso.get().load(ImageUrl).into(ivImage);
        tvIngredient.setText(recipe.getIngredients());
        tvInstruction.setText(recipe.getInstruction());

    }
}