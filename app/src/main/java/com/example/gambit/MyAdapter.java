package com.example.gambit;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.rambler.libs.swipe_layout.SwipeLayout;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    ArrayList<Foods> mFoods;
    SharedPreferences mPrefs, mePrefs;
    Foods food = new Foods();
    private final int COUNT = 30;
    int[] itemsOffset = new int[COUNT];

    public MyAdapter(SharedPreferences mPrefs, SharedPreferences mePrefs, ArrayList<Foods> foods) {
        this.mFoods = foods;
        this.mPrefs = mPrefs;
        this.mePrefs = mePrefs;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        final MyHolder viewHolder = new MyHolder(view);

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.swipeLayout.animateReset();
            }
        };
        if (viewHolder.rightView != null) {
            viewHolder.rightView.setClickable(true);
            viewHolder.rightView.setOnClickListener(onClick);
        }

        viewHolder.swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
            @Override
            public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {

            }

            @Override
            public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {

            }

            @Override
            public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
            }

            @Override
            public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
                moveToRight = false;
                if (viewHolder.swipeLayout.isRightSwipeEnabled()) {
                    if (!viewHolder.mFavorite.isChecked()) {
                        viewHolder.mFavorite.setChecked(true);
//                        viewHolder.mFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                            updateValue(food.getId(), isChecked);
//                        });
                        //updateValue(food.getId(), moveToRight);
                        viewHolder.swipeLayout.animateReset();
                    } else if (viewHolder.mFavorite.isChecked()) {
                        viewHolder.mFavorite.setChecked(false);
                        // updateValue(food.getId(), moveToRight);
//                        viewHolder.mFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                            updateValue(food.getId(), isChecked);
//                        });
                        viewHolder.swipeLayout.animateReset();
                    }
                }
            }

        });

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bind(mFoods.get(position), MyAdapter.this);


//        holder.mFavorite.setChecked(mFoods.get(position).isFavorite()
//                ? R.drawable.ic_favfull : R.drawable.ic_fav);
    }

    @Override
    public void onViewDetachedFromWindow(MyHolder holder) {
        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
            itemsOffset[holder.getAdapterPosition()] = holder.swipeLayout.getOffset();
        }
    }

    @Override
    public void onViewRecycled(MyHolder holder) {
        super.onViewRecycled(holder);
    }

    public void updateValue(String id, MyHolder holder, int newValue) {
        mPrefs.edit().putInt(id, newValue).apply();
        holder.mColichestvo.setText(String.valueOf(newValue));
        if (holder.mColichestvo.getText().toString().equals("0")) {
            holder.mButKorz.setVisibility(View.VISIBLE);
            holder.mButs.setVisibility(View.INVISIBLE);
        }

    }

    public int getValue(String id) {
        return mPrefs.getInt(id, 0);
    }


    @Override
    public int getItemCount() {
        return mFoods.size();
    }

//    public void updateVal() {
//        //mePrefs.edit().putBoolean(id, isChecked).apply();
//        mPrefs.edit().putBoolean(id, isChecked).apply();
//    }

//    public int getVal(String id) {
////        return mePrefs.getBoolean(id, false);return mPrefs.getBoolean(id, false);
//
//    }


}
