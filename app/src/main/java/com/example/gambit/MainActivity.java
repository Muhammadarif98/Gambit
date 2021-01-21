package com.example.gambit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    SharedPreferences mPrefs ;
    private ArrayList<Foods> clothes =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecyclerView = findViewById(R.id.recyclerViews);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPrefs= PreferenceManager
                .getDefaultSharedPreferences(this);

        NetworkService.getInstance()
                .getJsonApi()
                .getFoods()
                .enqueue(new Callback<List<Foods>>() {
                    @Override
                    public void onResponse(Call<List<Foods>> call, Response<List<Foods>> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            clothes = new ArrayList<>(response.body());
                            myAdapter=new MyAdapter(mPrefs,clothes);
                            mRecyclerView.setAdapter(myAdapter);

                        }
                        Log.d("TAG2", "onResponse"+ (response.body()));
                    }

                    @Override
                    public void onFailure(Call<List<Foods>> call, Throwable throwable) {
                        Log.d("TAG", "Response Failure =" + throwable.toString());
                        Toast.makeText(MainActivity.this,"Упс! Что то пошло не так", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}