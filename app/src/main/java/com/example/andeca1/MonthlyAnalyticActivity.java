package com.example.andeca1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anychart.AnyChartView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MonthlyAnalyticActivity extends AppCompatActivity {

    private Toolbar toolbar;


    private FirebaseAuth mAuth;
    private String onlineUserId = "";
    private DatabaseReference expensesRef,personalRef;

    private TextView totalAmountSpent,dataTransportAmount,dataEntertainmentAmount;
    private TextView dataEducationAmount;

    private RelativeLayout layoutOther,layoutHealth,layoutPersonal,layoutTransport,layoutFood, layoutHouse, layoutEntertainment, layoutEducation;
    private RelativeLayout layoutClothes;
    private LinearLayout linearLayoutOther,linearLayoutHealth,linearLayoutPersonal,
            linearLayoutClothes, linearLayout, linearLayoutFood, linearLayoutHouse, linearLayoutEntertainment, linearLayoutEducation;;
    private AnyChartView anyChartView;
    private TextView dataOtherAmount,dataHealthAmount,dataClothesAmount,dataPersonalAmount;
    private ImageView Other_status,Health_status,Personal_status,Clothes_status,Education_status, transport_status, Food_status,
            House_status, Entertainment_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_analytic);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Monthly Analytics");

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        expensesRef = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        personalRef = FirebaseDatabase.getInstance().getReference("personal").child(onlineUserId);


        totalAmountSpent = findViewById(R.id.totalAmountSpent);
        dataTransportAmount = findViewById(R.id.dataTransportAmount);
        dataEntertainmentAmount = findViewById(R.id.dataEntertainmentAmount);
        dataEducationAmount = findViewById(R.id.dataEducationAmount);
        layoutOther = findViewById(R.id.layoutOther);
        layoutHealth = findViewById(R.id.layoutHealth);
        linearLayoutPersonal = findViewById(R.id.linearLayoutPersonal);
        layoutPersonal = findViewById(R.id.layoutPersonal);
        layoutTransport = findViewById(R.id.layoutTransport);
        layoutFood = findViewById(R.id.layoutFood);
        layoutHouse = findViewById(R.id.layoutHouse);


        layoutEntertainment = findViewById(R.id.layoutEntertainment);
        layoutEducation = findViewById(R.id.layoutEducation);
        layoutClothes = findViewById(R.id.layoutClothes);
        linearLayoutOther = findViewById(R.id.linearLayoutOther);
        linearLayoutHealth = findViewById(R.id.linearLayoutHealth);
        linearLayoutClothes = findViewById(R.id.linearLayoutClothes);
        linearLayout = findViewById(R.id.linearLayout);
        linearLayoutFood = findViewById(R.id.linearLayoutFood);
        linearLayoutHouse = findViewById(R.id.linearLayoutHouse);

        anyChartView = findViewById(R.id.anyChartView);

        linearLayoutEntertainment = findViewById(R.id.linearLayoutEntertainment);
        dataOtherAmount = findViewById(R.id.dataOtherAmount);
        dataHealthAmount = findViewById(R.id.dataHealthAmount);
        dataClothesAmount = findViewById(R.id.dataClothesAmount);
        dataPersonalAmount = findViewById(R.id.dataPersonalAmount);
        Other_status = findViewById(R.id.Other_status);
        Health_status = findViewById(R.id.Health_status);
        Personal_status = findViewById(R.id.Personal_status);
        Clothes_status = findViewById(R.id.Clothes_status);
        Education_status = findViewById(R.id.Education_status);

        transport_status = findViewById(R.id.transport_status);
        Food_status = findViewById(R.id.Food_status);
        House_status = findViewById(R.id.House_status);
        Entertainment_status = findViewById(R.id.Entertainment_status);
        linearLayoutEducation = findViewById(R.id.linearLayoutEducation);

    }
}