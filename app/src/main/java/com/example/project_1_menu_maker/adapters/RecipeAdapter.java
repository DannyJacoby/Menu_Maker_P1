package com.example.project_1_menu_maker.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
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
import com.example.project_1_menu_maker.DisplayActivity;
import com.example.project_1_menu_maker.R;
import com.example.project_1_menu_maker.models.Recipe;

import org.parceler.Parcels;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    Context context;
    List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes){
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("RecipeAdapter", "onCreateViewHolder");
        View recipeView = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        Log.d("RecipeAdapter", "onBilndViewHolder " + position);
        Recipe recipe = recipes.get(position);
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


        public void bind(final Recipe recipe) {
            tvTitle.setText(recipe.getTitle());
            tvCategory.setText(recipe.getCategory());
            tvArea.setText(recipe.getArea());
            String ImageUrl = recipe.getMealThumb();

            Log.d("Image", "bind: "+ ImageUrl);
            Glide.with(context).load(ImageUrl).into(ivThumb);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DisplayActivity.class);
                    i.putExtra("recipe", Parcels.wrap(recipe));
                    context.startActivity(i);

                    
                }
            });


        }
    }
}
