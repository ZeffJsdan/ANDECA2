package com.example.andeca1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.ViewHolder>{

    private Context mContext;
    private List<Data> myDataList;

    public WeeklyAdapter(Context mContext, List<Data> myDataList) {
        this.mContext = mContext;
        this.myDataList = myDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.retrieve_layout, parent,false);
        return new WeeklyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Data data = myDataList.get(position);
        holder.item.setText("Item: "+data.getItem());
        holder.amount.setText("Amount: $"+data.getAmount());
        holder.date.setText("Date: "+data.getDate());
        holder.description.setText("Description: "+data.getDescription());

        switch (data.getItem()){
            case "Transport":
                holder.imageView.setImageResource(R.drawable.transportation);
                break;
            case "Food":
                holder.imageView.setImageResource(R.drawable.food);
                break;
            case "House":
                holder.imageView.setImageResource(R.drawable.house);
                break;
            case "Entertainment":
                holder.imageView.setImageResource(R.drawable.entertainment);
                break;
            case "Education":
                holder.imageView.setImageResource(R.drawable.education);
                break;
            case "Clothes":
                holder.imageView.setImageResource(R.drawable.clothes);
                break;
            case "Personal":
                holder.imageView.setImageResource(R.drawable.personal);
                break;
            case "Health":
                holder.imageView.setImageResource(R.drawable.health);
                break;
            case "Other":
                holder.imageView.setImageResource(R.drawable.question_mark);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView item, amount, date, description;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
