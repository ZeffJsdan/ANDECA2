package com.example.andeca1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CardView budgetCardView,dailyCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        budgetCardView = findViewById(R.id.budgetCardView);
        dailyCardView = findViewById(R.id.dailyCardView);
    }

    public void onClickBudget(View v){
        Intent intent = new Intent(MainActivity.this,BudgetActivity.class);
        startActivity(intent);
    }
    public void onClickDaily(View v){
        Intent intent = new Intent(MainActivity.this,DailySpendingActivity.class);
        startActivity(intent);
    }
}