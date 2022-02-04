package com.example.andeca1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DailyItemAdapter extends RecyclerView.Adapter<DailyItemAdapter.ViewHolder> {


    private Context mContext;
    private List<Data> myDataList;
    private String post_key = "";
    private String item = "";
    private String description = "";
    private int amount = 0;

    public DailyItemAdapter(Context mContext, List<Data> myDataList) {
        this.mContext = mContext;
        this.myDataList = myDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.retrieve_layout, parent,false);
        return new DailyItemAdapter.ViewHolder(view);
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

        holder.itemView.setOnClickListener(v -> {
            post_key = data.getId();
            item = data.getItem();
            amount = data.getAmount();
            description = data.getDescription();
            updateData();
        });

    }

    private void updateData() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mView = inflater.inflate(R.layout.update_layout,null);

        myDialog.setView(mView);
        final AlertDialog dialog = myDialog.create();

        final TextView mItem =mView.findViewById(R.id.itemName);
        final EditText mAmount =mView.findViewById(R.id.amount);
        final EditText mDescription =mView.findViewById(R.id.description);

        mItem.setText(item);

        mDescription.setText(description);
        mDescription.setSelection(description.length());

        mAmount.setText(String.valueOf(amount));
        mAmount.setSelection(String.valueOf(amount).length());

        Button delBtn = mView.findViewById(R.id.btnDelete);
        Button btnUpdate = mView.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(v -> {
            amount = Integer.parseInt(mAmount.getText().toString());

            description = mDescription.getText().toString();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());

            MutableDateTime epoch = new MutableDateTime();
            epoch.setDate(0);
            DateTime now = new DateTime();
            Weeks weeks = Weeks.weeksBetween(epoch,now);
            Months months = Months.monthsBetween(epoch,now);

            Data data = new Data(item, date, post_key, description, amount, months.getMonths(),weeks.getWeeks());

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            reference.child(post_key).setValue(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(mContext,"Updated successfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext,task.getException().toString(),Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        delBtn.setOnClickListener(v -> {
            reference.child(post_key).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(mContext,"Deleted",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext,task.getException().toString(),Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });
        dialog.show();
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
