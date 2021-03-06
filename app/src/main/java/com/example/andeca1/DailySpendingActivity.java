package com.example.andeca1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DailySpendingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView totalAmountSpent;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private String onlineUserId = "";
    private DatabaseReference expensesRef;

    private DailyItemAdapter dailyItemAdapter;
    private List<Data> myDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_spending);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Today's Spending");
        totalAmountSpent = findViewById(R.id.totalAmountSpent);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        fab = findViewById(R.id.fabDailyAdd);
        loader = new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        expensesRef = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        myDataList = new ArrayList<>();
        dailyItemAdapter = new DailyItemAdapter(DailySpendingActivity.this,myDataList);
        recyclerView.setAdapter(dailyItemAdapter);

        readItem();

    }

    private void readItem(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myDataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Data data = dataSnapshot.getValue(Data.class);
                    myDataList.add(data);
                }

                dailyItemAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                int totalAmount = 0;
                for(DataSnapshot ds : snapshot.getChildren()){
                    Map<String,Object> map = (Map<String,Object>)ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;

                    totalAmountSpent.setText("Today's Spending: $"+totalAmount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickDailySpend(View view){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.input_layout,null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final Spinner itemSpinner = myView.findViewById(R.id.itemspinner);
        final EditText amount = myView.findViewById(R.id.amount);
        final EditText description = myView.findViewById(R.id.description);
        final Button cancel = myView.findViewById(R.id.cancel);
        final Button save = myView.findViewById(R.id.save);

        description.setVisibility(View.VISIBLE);

        save.setOnClickListener(v -> {
            String Amount = amount.getText().toString();
            String Item = itemSpinner.getSelectedItem().toString();
            String desciptions = description.getText().toString();

            if (TextUtils.isEmpty(Amount)){
                amount.setError("Amount is required!");
                return;
            }
            if (Item.equals("Select item")){
                Toast.makeText(DailySpendingActivity.this, "Select a valid item", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(desciptions)){
                description.setError("Description is required");
                return;
            }
            else {
                loader.setMessage("adding a item");
                loader.setCanceledOnTouchOutside(false);
                loader.show();

                String id = expensesRef.push().getKey();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();
                String date = dateFormat.format(cal.getTime());

                MutableDateTime epoch = new MutableDateTime();
                epoch.setDate(0);
                DateTime now = new DateTime();
                Weeks weeks = Weeks.weeksBetween(epoch,now);
                Months months = Months.monthsBetween(epoch,now);

                Data data = new Data(Item, date, id, desciptions, Integer.parseInt(Amount), months.getMonths(),weeks.getWeeks());

                expensesRef.child(id).setValue(data).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(DailySpendingActivity.this,"Budget item added successfully",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DailySpendingActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                    }
                    loader.dismiss();
                });
            }
            dialog.dismiss();
        });
        cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}