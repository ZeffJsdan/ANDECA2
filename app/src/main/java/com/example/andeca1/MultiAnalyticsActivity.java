package com.example.andeca1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MultiAnalyticsActivity extends AppCompatActivity {

    private CardView todayCardView, weeklyCardView, monthCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_analytics);

        todayCardView = findViewById(R.id.todayCardView);
        weeklyCardView = findViewById(R.id.weeklyCardView);
        monthCardView = findViewById(R.id.monthCardView);

    }
    public void onClickTodayCardView(View v){
        Intent intent = new Intent(MultiAnalyticsActivity.this,DailyAnalyticActivity.class);
        startActivity(intent);
    }
    public void onClickWeeklyCardView(View v){
        Intent intent = new Intent(MultiAnalyticsActivity.this,WeeklyAnalyticActivity.class);
        startActivity(intent);
    }
    public void onClickMonthlyCardView(View v){
        Intent intent = new Intent(MultiAnalyticsActivity.this,MonthlyAnalyticActivity.class);
        startActivity(intent);
    }
}