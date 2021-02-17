package com.example.project_1_menu_maker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_1_menu_maker.db.Recipes;
import com.example.project_1_menu_maker.models.Recipe;

import org.parceler.Parcels;

import java.util.List;

public class RecipeAdapter2 extends RecyclerView.Adapter<RecipeAdapter2.ViewHolder> {
    private static final String USER_ID_KEY = "com.example.project_1_menu_maker.db.userIdKey";

    Context context;
    List<Recipes> recipes;
    private int userId;

    public RecipeAdapter2(Context context, List<Recipes> recipes, int userId){
        this.context = context;
        this.recipes = recipes;
        this.userId = userId;
    }

    @NonNull
    @Override
    public RecipeAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("RecipeAdapter", "onCreateViewHolder");
        View recipeView = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter2.ViewHolder holder, int position) {
        Log.d("RecipeAdapter", "onBilndViewHolder " + position);
        Recipes recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvCategory;
        TextView tvArea;
        ImageView ivThumb;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvArea = itemView.findViewById(R.id.tvArea);
            ivThumb = itemView.findViewById(R.id.ivThumb);
            container = itemView.findViewById(R.id.container);
        }


        public void bind(final Recipes recipe) {
            tvTitle.setText(recipe.getTitle());
            tvCategory.setText(recipe.getCategory());
            tvArea.setText(recipe.getArea());
            String ImageUrl = recipe.getMealThumb();

            Log.d("Image", "bind: "+ ImageUrl);
            Glide.with(context).load(ImageUrl).into(ivThumb);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = DisplayActivity2.intentFactory(context, userId, recipe);
                    intent.putExtra("recipe", Parcels.wrap(recipe));
                    context.startActivity(intent);
//                    Intent i = new Intent(context, DisplayActivity.class);
//
//                    i.putExtra(USER_ID_KEY, userId);

//
//                    context.startActivity(i);

                }
            });


        }
    }
}
