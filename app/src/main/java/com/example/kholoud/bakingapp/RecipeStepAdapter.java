package com.example.kholoud.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.example.kholoud.bakingapp.DetailActivity;
import com.example.kholoud.bakingapp.R;
import com.example.kholoud.bakingapp.RecipeStep;

import java.util.ArrayList;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder> {
//DEF ********************************************************
    private Context context;
    private String Name;
    private ArrayList<RecipeStep> StepList;
//************************************************************************
    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout StepContainer;
        public ImageView MidView;
        public TextView StepText;
        public RecipeStepAdapterViewHolder(LinearLayout view) {
            super(view);
            StepContainer = view;
            MidView = new ImageView(context);
            StepText = new TextView(context);

            StepContainer.addView(MidView);
            StepContainer.addView(StepText);
        }
    }

    ///Constractor ****************************************
    public RecipeStepAdapter(Context context, String recipeName,
                             ArrayList<RecipeStep> recipeStepList) {
        this.context = context;
        Name = recipeName;
        StepList = recipeStepList;
    }

    @NonNull
    @Override
    public RecipeStepAdapterViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        LinearLayout stepContainer = new LinearLayout(context);
        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(20, 20, 20, 20);
        stepContainer.setLayoutParams(cardParams);
        stepContainer.setBackgroundResource(R.color.cardBack);

        RecipeStepAdapterViewHolder recipeStepCard
                = new RecipeStepAdapterViewHolder(stepContainer);
        return recipeStepCard;
    }

    @Override
    public int getItemCount() {
        return StepList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterViewHolder holder, final int position) {
        final RecipeStep recipeStepAt = StepList.get(position);

        holder.StepContainer.setContentDescription(recipeStepAt.getSmallDescription());

        LinearLayout.LayoutParams ParamRecipe =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        ParamRecipe.weight = 3f;
        ParamRecipe.gravity = Gravity.CENTER;
        holder.MidView.setLayoutParams(ParamRecipe);
        Picasso.get()
                .load(Uri.parse(recipeStepAt.getImageUrl()))
                .placeholder(R.drawable.cake)
                .error(R.drawable.cake)
                .into(holder.MidView);
//**************************************************************************************
        LinearLayout.LayoutParams textParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1f;
        textParams.gravity = Gravity.CENTER;
        holder.StepText.setLayoutParams(textParams);
        holder.StepText.setText(recipeStepAt.getSmallDescription());
        holder.StepText.setTextAppearance(context, R.style.StepCardText);
//*******************************************************************************************
        holder.StepContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity currentActivity = (DetailActivity) context;
                currentActivity.setCurrentPosition(position);
                currentActivity.openFragment();

            }
        });
    }

}
