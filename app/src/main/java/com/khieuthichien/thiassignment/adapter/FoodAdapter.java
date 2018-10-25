package com.khieuthichien.thiassignment.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khieuthichien.thiassignment.R;
import com.khieuthichien.thiassignment.database.DatabaseHelper;
import com.khieuthichien.thiassignment.model.Food;

import java.text.NumberFormat;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    Context context;
    List<Food> foodList;
    DatabaseHelper databaseHelper;

    public FoodAdapter(Context context, List<Food> foodList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.foodList = foodList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public FoodAdapter.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodHolder holder, final int position) {
        NumberFormat format=NumberFormat.getCurrencyInstance();
        //databaseHelper = new DatabaseHelper(context);

        final Food food = foodList.get(position);
        holder.tvidfood.setText(String.valueOf(food.getIdfood()));
        holder.tvtenfood.setText(food.getNamefood());
        holder.tvgiafood.setText(format.format(food.getPrice()));

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    //SQLiteDatabase db = new SQLiteDatabase(this);

                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.deleteFood(foodList.get(position).getIdfood());
                        foodList.get(position);
                        foodList.remove(food);
                        notifyDataSetChanged();

                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();


            }
        });


    }

    @Override
    public int getItemCount() {
        if (foodList == null){
            return 0;
        }
        return foodList.size();
    }

    public class FoodHolder extends RecyclerView.ViewHolder {

        TextView tvidfood;
        TextView tvtenfood;
        TextView tvgiafood;
        ImageView imgdelete;

        public FoodHolder(View itemView) {
            super(itemView);

            tvidfood = itemView.findViewById(R.id.tvidfood);
            tvtenfood = itemView.findViewById(R.id.tvtenfood);
            tvgiafood = itemView.findViewById(R.id.tvgiafood);
            imgdelete = itemView.findViewById(R.id.imgdelete);

        }

    }
}
