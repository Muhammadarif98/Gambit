package com.example.gambit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    SharedPreferences mPrefs ;
    private ArrayList<Foods> foods =new ArrayList<>();

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
                            foods = new ArrayList<>(response.body());
                            myAdapter=new MyAdapter(mPrefs,foods);
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
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                // when user swipe thr recyclerview item to right remove item from avorite list
                if (direction == ItemTouchHelper.RIGHT) {
                    myAdapter.addToFav(viewHolder.getAdapterPosition(), false);
                }
                // when user swipe thr recyclerview item to left remove item from avorite list
                else if (direction == ItemTouchHelper.LEFT) {
                    myAdapter.addToFav(viewHolder.getAdapterPosition(), true);
                }

            }
        }).attachToRecyclerView(mRecyclerView);

//        myAdapter = new MyAdapter(mPrefs,foods);
//        swipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onRightClicked(int position) {
//                myAdapter.mFoods.remove(position);
//                myAdapter.notifyItemRemoved(position);
//                myAdapter.notifyItemRangeChanged(position, myAdapter.getItemCount());
//            }
//        });
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(mRecyclerView);
//
//        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                swipeController.onDraw(c);
//            }
//        });

    }
}