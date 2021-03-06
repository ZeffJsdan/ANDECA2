package com.example.andeca1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BudgetActivity extends AppCompatActivity {

    private TextView totalAmountTextView;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private FloatingActionButton fabBudgetAdd;

    private DatabaseReference budgetRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loader;

    private String post_key = "";
    private String item = "";
    private int amount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        mAuth = FirebaseAuth.getInstance();
        budgetRef = FirebaseDatabase.getInstance().getReference().child("budget").child(mAuth.getCurrentUser().getUid());
        loader = new ProgressDialog(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Budgeting");
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        recyclerView = findViewById(R.id.recyclerView);

        budgetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalAmount = 0;

                for (DataSnapshot snap :snapshot.getChildren()){
                    Data data = snap.getValue(Data.class);
                    totalAmount += data.getAmount();
                    String stringTotal = String.valueOf("Month Budget: $"+totalAmount);
                    totalAmountTextView.setText(stringTotal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        fabBudgetAdd = findViewById(R.id.fabBudgetAdd);
    }

    public void onClickAddBudget(View view){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.input_layout,null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final Spinner itemSpinner = myView.findViewById(R.id.itemspinner);
        final EditText amount = myView.findViewById(R.id.amount);
        final Button cancel = myView.findViewById(R.id.cancel);
        final Button save = myView.findViewById(R.id.save);

        save.setOnClickListener(v -> {
            String budgetAmount = amount.getText().toString();
            String budgetItem = itemSpinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(budgetAmount)){
                amount.setError("Amount is required!");
                return;
            }
            if (budgetItem.equals("Select item")){
                Toast.makeText(BudgetActivity.this, "Select a valid item", Toast.LENGTH_SHORT).show();
            }
            else {
                loader.setMessage("adding a item");
                loader.setCanceledOnTouchOutside(false);
                loader.show();

                String id = budgetRef.push().getKey();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();
                String date = dateFormat.format(cal.getTime());

                MutableDateTime epoch = new MutableDateTime();
                epoch.setDate(0);
                DateTime now = new DateTime();
                Weeks weeks = Weeks.weeksBetween(epoch,now);
                Months months = Months.monthsBetween(epoch,now);

                Data data = new Data(budgetItem, date, id, null, Integer.parseInt(budgetAmount), months.getMonths(), weeks.getWeeks());

                budgetRef.child(id).setValue(data).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(BudgetActivity.this,"Budget item added successfully",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(BudgetActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                    }
                    loader.dismiss();
                });
            }
            dialog.dismiss();
        });
        cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(budgetRef,Data.class)
                .build();
        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {
                holder.setItemAmount("Budget: $"+ model.getAmount());
                holder.setDate("On: "+ model.getDate());
                holder.setItemName("Item: "+ model.getItem());

                holder.description.setVisibility(View.GONE);

                switch (model.getItem()){
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

                holder.mView.setOnClickListener(v -> {
                    post_key = getRef(position).getKey();
                    item = model.getItem();
                    amount = model.getAmount();
                    updateData();
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieve_layout, parent, false);
                return new MyViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ImageView imageView;
        public TextView description, date;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            mView = itemView;
            imageView = itemView.findViewById(R.id.imageView);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }

        public void setItemName (String itemName){
            TextView item = mView.findViewById(R.id.item);
            item.setText(itemName);
        }
        public void setItemAmount (String itemAmount){
            TextView item = mView.findViewById(R.id.amount);
            item.setText(itemAmount);
        }
        public void setDate (String itemDate){
            TextView item = mView.findViewById(R.id.date);
            item.setText(itemDate);
        }
    }

    private void updateData(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View mView = inflater.inflate(R.layout.update_layout,null);

        myDialog.setView(mView);
        final AlertDialog dialog = myDialog.create();

        final TextView mItem =mView.findViewById(R.id.itemName);
        final EditText mAmount =mView.findViewById(R.id.amount);
        final EditText mDescription =mView.findViewById(R.id.description);

        mDescription.setVisibility(View.GONE);

        mItem.setText(item);

        mAmount.setText(String.valueOf(amount));
        mAmount.setSelection(String.valueOf(amount).length());

        Button delBtn = mView.findViewById(R.id.btnDelete);
        Button btnUpdate = mView.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(v -> {
            amount = Integer.parseInt(mAmount.getText().toString());

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());

            MutableDateTime epoch = new MutableDateTime();
            epoch.setDate(0);
            DateTime now = new DateTime();
            Months months = Months.monthsBetween(epoch,now);
            Weeks weeks = Weeks.weeksBetween(epoch,now);

            Data data = new Data(item, date, post_key, null, amount, months.getMonths(),weeks.getWeeks());

            budgetRef.child(post_key).setValue(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(BudgetActivity.this,"Updated successfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BudgetActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });
        delBtn.setOnClickListener(v -> {
            budgetRef.child(post_key).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(BudgetActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BudgetActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });
        dialog.show();
    }
}