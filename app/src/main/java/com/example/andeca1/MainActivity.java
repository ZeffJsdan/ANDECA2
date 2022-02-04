package com.example.andeca1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

//    private CardView budgetCardView,dailyCardView;
    private ImageView weekBtnImageView,budgetBtnImageView,dailyBtnImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        budgetBtnImageView = findViewById(R.id.budgetBtnImageView);
        dailyBtnImageView = findViewById(R.id.dailyBtnImageView);
    }

    public void onClickBudget(View v){
        Intent intent = new Intent(MainActivity.this,BudgetActivity.class);
        startActivity(intent);
    }
    public void onClickDaily(View v){
        Intent intent = new Intent(MainActivity.this,DailySpendingActivity.class);
        startActivity(intent);
    }
    public void onClickWeekly(View v){
        Intent intent = new Intent(MainActivity.this,WeeklyActivity.class);
        startActivity(intent);
    }
}