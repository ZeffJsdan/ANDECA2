package com.example.andeca1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CardView budgetCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        budgetCardView = findViewById(R.id.budgetCardView);
    }

    public void onClickBudget(View v){
        Intent intent = new Intent(MainActivity.this,BudgetActivity.class);
        startActivity(intent);
    }
}