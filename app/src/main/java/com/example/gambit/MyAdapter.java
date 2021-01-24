package com.example.gambit;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    ArrayList<Foods> mFoods;
    SharedPreferences mPrefs;

    public MyAdapter(SharedPreferences mPrefs, ArrayList<Foods> foods) {
        this.mFoods = foods;
        this.mPrefs = mPrefs;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bind(mFoods.get(position), MyAdapter.this);
        holder.ivFavImage.setImageResource(mFoods.get(position).isFavorite()
                ? R.drawable.ic_favfull : R.drawable.ic_fav);
    }

    public void updateValue(MyHolder holder ,int newValue,String id){
        mPrefs.edit().putInt(id, newValue).apply();
        holder.mColichestvo.setText(String.valueOf(newValue));
        if (holder.mColichestvo.getText().toString().equals("0")){
            holder.mButKorz.setVisibility(View.VISIBLE);
            holder.mButs.setVisibility(View.INVISIBLE);
        }
    }
    public int getValue(String id){
        return mPrefs.getInt(id, 0);
    }


//    public void updateValue(String id, boolean isChecked) {
//        mPrefs.edit().putBoolean(id, isChecked).apply();
//    }
//
//    public boolean getValue(String id) {
//        return mPrefs.getBoolean(id, false);
//    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public void addToFav(int position, boolean flag) {
        mFoods.get(position).setFavorite(flag);
        notifyDataSetChanged();
    }
}
