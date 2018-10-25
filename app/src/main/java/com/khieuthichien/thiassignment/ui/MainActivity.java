package com.khieuthichien.thiassignment.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.khieuthichien.thiassignment.R;
import com.khieuthichien.thiassignment.adapter.FoodAdapter;
import com.khieuthichien.thiassignment.database.DatabaseHelper;
import com.khieuthichien.thiassignment.model.Food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtidfood;
    private EditText edttenfood;
    private EditText edtpricefood;
    private Button btnadd;
    private Button btnsua;
    private RecyclerView recyclerview;

    private List<Food> foodList;
    private FoodAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtidfood = findViewById(R.id.edtidfood);
        edttenfood = findViewById(R.id.edttenfood);
        edtpricefood = findViewById(R.id.edtpricefood);
        btnadd = findViewById(R.id.btnadd);
        btnsua = findViewById(R.id.btnsua);
        recyclerview = findViewById(R.id.recyclerview);

        databaseHelper = new DatabaseHelper(this);
        foodList = new ArrayList<>();

        foodList = databaseHelper.getAllFood();

        adapter = new FoodAdapter(this, foodList, databaseHelper);
        recyclerview.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edttenfood.getText().toString().trim();

                if (edtidfood.getText().toString().isEmpty() || name.isEmpty() || edtpricefood.getText().toString().isEmpty()){
                    if (edtidfood.getText().toString().isEmpty()){
                        edtidfood.setError(getString(R.string.notify_name));
                        return;
                    }
                    if (name.isEmpty()){
                        edttenfood.setError(getString(R.string.notify_name));
                        return;
                    }
                    if (edtpricefood.getText().toString().isEmpty()){
                        edtpricefood.setError(getString(R.string.notify_name));
                        return;
                    }
                }else {
                    if (edtidfood.getText().toString().length() > 5 || name.length() < 5 || name.length() > 10 || edtpricefood.getText().toString().length() < 0 ){
                        if (edtidfood.getText().toString().length() > 5){
                            edtidfood.setError(getString(R.string.notify_do_dai));
                            return;
                        }
                        if (name.length() < 5 || name.length() > 10 ){
                            edttenfood.setError(getString(R.string.notify_name_dodai));
                            return;
                        }
                        if (edtpricefood.getText().toString().length() < 0){
                            edtpricefood.setError(getString(R.string.notify_giatien));
                            return;
                        }
                    }else {
                        int id = Integer.parseInt(edtidfood.getText().toString().trim());
                        long price = Long.parseLong(edtpricefood.getText().toString().trim());

                        Food food2 = new Food();
                        foodList = new ArrayList<>();
                        for (int i = 0; i < edtidfood.length(); i++) {
                            if (edtidfood.getText().toString().equalsIgnoreCase("")){
                                edtidfood.setError(getString(R.string.notify_trung_mhau));
                                return;
                            }else {
                                Food food = new Food();
                                food.setIdfood(id);
                                food.setNamefood(name);
                                food.setPrice(price);

                                databaseHelper.insertFood(food);
                                databaseHelper.getAllFood();

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }

                    }
                }
            }

        });

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edttenfood.setText(edttenfood.getText().toString().trim());
                edtpricefood.setText(String.valueOf(edtpricefood.getText().toString().trim()));

                String name = edttenfood.getText().toString().trim();

                if (edtidfood.getText().toString().isEmpty() || name.isEmpty() || edtpricefood.getText().toString().isEmpty()){
                    if (edtidfood.getText().toString().equals("")){
                        edtidfood.setError(getString(R.string.notify_name));
                        return;
                    }
                    if (name.equals("")){
                        edttenfood.setError(getString(R.string.notify_name));
                        return;
                    }
                    if (edtpricefood.getText().toString().equals("")){
                        edtpricefood.setError(getString(R.string.notify_name));
                        return;
                    }
                }else {
                    if (edtidfood.getText().toString().length() > 5 || name.length() < 5 && name.length() > 10 || edtpricefood.getText().toString().length() < 0 ){
                        if (edtidfood.getText().toString().length() > 5){
                            edtidfood.setError(getString(R.string.notify_do_dai));
                            return;
                        }
                        if (name.length() < 5 && name.length() > 10){
                            edttenfood.setError(getString(R.string.notify_name_dodai));
                            return;
                        }
                        if (edtpricefood.getText().toString().length() < 0){
                            edtpricefood.setError(getString(R.string.notify_giatien));
                            return;
                        }
                    }else {

                        int id = Integer.parseInt(edtidfood.getText().toString().trim());
                        long price = Long.parseLong(edtpricefood.getText().toString().trim());

                        Food food1 = new Food();
                        food1.setNamefood(edttenfood.getText().toString().trim());
                        food1.setPrice(Long.parseLong(edtpricefood.getText().toString().trim()));

                        databaseHelper.updateFood(Integer.parseInt(edtidfood.getText().toString()),food1);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
            }

        });

    }
}
