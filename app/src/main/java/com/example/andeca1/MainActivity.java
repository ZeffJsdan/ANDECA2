package com.example.andeca1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView weekBtnImageView,budgetBtnImageView,dailyBtnImageView, monthBtnImageView,
            todayAnalyticImageView,weekAnalyticImageView,monthAnalyticImageView,statisticCardView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        budgetBtnImageView = findViewById(R.id.budgetBtnImageView);
        dailyBtnImageView = findViewById(R.id.dailyBtnImageView);
        monthBtnImageView = findViewById(R.id.monthBtnImageView);
        todayAnalyticImageView = findViewById(R.id.todayAnalyticImageView);
        weekAnalyticImageView = findViewById(R.id.weekAnalyticImageView);
        monthAnalyticImageView = findViewById(R.id.monthAnalyticImageView);
        statisticCardView = requireViewById(R.id.statisticCardView);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Spenditure");

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
        intent.putExtra("type","week");
        startActivity(intent);
    }

    public void onClickMonthly(View v){
        Intent intent = new Intent(MainActivity.this,WeeklyActivity.class);
        intent.putExtra("type","month");
        startActivity(intent);
    }
    public void onClickAnalytic(View v){
        Intent intent = new Intent(MainActivity.this,MultiAnalyticsActivity.class);

        startActivity(intent);
    }
    public void onClickHistory(View v){
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.account){
            Intent intent = new Intent(MainActivity.this,AccountActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}