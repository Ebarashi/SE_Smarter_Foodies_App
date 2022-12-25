package com.example.smarter_foodies;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class MyLikedAdapter extends RecyclerView.Adapter<LikedFoodViewHolder> {

    private Activity activity;
    private Context mContext;
    private List<recipe> myFoodList;

    public MyLikedAdapter(Context mContext, List<recipe> myFoodList, Activity activity) {
        this.mContext = mContext;
        this.myFoodList = myFoodList;
        this.activity = activity;
    }

    @Override
    public LikedFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_raw_liked_item, parent, false);
        return new LikedFoodViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull LikedFoodViewHolder foodViewHolder, int i) {
        recipe recipe = myFoodList.get(i);
        if (!myFoodList.get(i).getImages().isEmpty()) {
            List<String> images = recipe.getImages();
//            setBestImage(foodViewHolder, i);
            Picasso.get().load(images.get(images.size() - 1)).resize(220, 220).into(foodViewHolder.imageView);
        }
        foodViewHolder.mTitle.setText(recipe.getTitle());
        foodViewHolder.mAdditional.setText(recipe.getMain_category() + "- " + recipe.getCategory());
        foodViewHolder.mHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog dialog = new AlertDialog();
//                dialog.setContentView(R.layout.yes_no_dialog_layout);
//                dialog.setCancelable(false);
//                dialog.setTitle("Remove from liked recipes?");
//                dialog.getLayoutInflater().inflate()
//                dialog.show();
                setDialogApproval(foodViewHolder, recipe);
            }
        });

        foodViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipePage.class);
                List<String> images = myFoodList.get(foodViewHolder.getAdapterPosition()).getImages();
                intent.putExtra("recipeImage", images.get(images.size() - 1));
                intent.putExtra("CategoryAndSub", myFoodList.get(foodViewHolder.getAdapterPosition()).getCategory());
                intent.putExtra("name", myFoodList.get(foodViewHolder.getAdapterPosition()).getTitle());
                intent.putExtra("copyRights", myFoodList.get(foodViewHolder.getAdapterPosition()).getCopy_rights());
                intent.putExtra("carbs", myFoodList.get(foodViewHolder.getAdapterPosition()).getCarbs());
                intent.putExtra("protein", myFoodList.get(foodViewHolder.getAdapterPosition()).getProtein());
                intent.putExtra("fats", myFoodList.get(foodViewHolder.getAdapterPosition()).getFat());
                intent.putExtra("calories", myFoodList.get(foodViewHolder.getAdapterPosition()).getCalories());
                intent.putExtra("ingardiants", myFoodList.get(foodViewHolder.getAdapterPosition()).getIngredients().toString());
                intent.putExtra("HowToMake", myFoodList.get(foodViewHolder.getAdapterPosition()).getDirections().toString());
                intent.putExtra("prepTime", myFoodList.get(foodViewHolder.getAdapterPosition()).getPrepTime());
                intent.putExtra("cookTime", myFoodList.get(foodViewHolder.getAdapterPosition()).getCookingTime());
                intent.putExtra("totalTime", myFoodList.get(foodViewHolder.getAdapterPosition()).getTotalTime());
                intent.putExtra("servings", myFoodList.get(foodViewHolder.getAdapterPosition()).getServings());

                mContext.startActivity(intent);
            }
        });
    }

    private void setDialogApproval(LikedFoodViewHolder foodViewHolder, recipe r) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, androidx.appcompat.R.style.Base_V7_Theme_AppCompat_Dialog);
        final View customLayout = activity.getLayoutInflater().inflate(R.layout.yes_no_dialog_layout, null);
        builder.setView(customLayout);
        builder.setCancelable(false);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                List<String> singleValueList = foodViewHolder.CRUD.getSingleValueList(r.getTitle());
                foodViewHolder.CRUD.removeFromUserLists(singleValueList, "liked");
                myFoodList.remove(r);
                MyLikedAdapter.super.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("no", (dialogInterface, which) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    private void setBestImage(FoodViewHolder foodViewHolder, int i) {
//        int width = 0;
//        int height = 0;
//        int bestImage = 0;
//        List<String> images = myFoodList.get(i).getImages();
//        for (int j = 0; j < images.size(); j++){
//            Picasso.get().load(images.get(j)).into(foodViewHolder.imageView);
//            // Get the dimensions of the image
//            int currWidth = foodViewHolder.imageView.getWidth();
//            int currHeight = foodViewHolder.imageView.getHeight();
//            // Check if the image is too small
//            if (currHeight*currWidth > width*height) {
//                // Image is likely to be blurry when displayed at a larger size
//                width = currWidth;
//                height = currHeight;
//                bestImage = j;
//            }
//        }
//        Picasso.get().load(images.get(bestImage)).into(foodViewHolder.imageView);
//    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }

}


class LikedFoodViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView mTitle, mAdditional;
    CardView mCardView;
    ImageButton mHeart;
    CRUD_RealTimeDatabaseData CRUD;

    public LikedFoodViewHolder(View itemView) {
        super(itemView);
        CRUD = new CRUD_RealTimeDatabaseData();
        imageView = itemView.findViewById(R.id.iv_liked_image);
        mTitle = itemView.findViewById(R.id.tv_liked_title);
        mAdditional = itemView.findViewById(R.id.tv_liked_addition_data);
        mCardView = itemView.findViewById(R.id.myCardLikedView);
        mHeart = itemView.findViewById(R.id.ib_like_recycler);


    }
}