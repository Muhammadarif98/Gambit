package com.example.gambit;


import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import ru.rambler.libs.swipe_layout.SwipeLayout;


public class MyHolder extends RecyclerView.ViewHolder {
    SwipeLayout swipeLayout;
    ImageView mImageView;
    CheckBox mFavorite;
    TextView mTitle,mBrand,mPrice;
    TextView mColichestvo;
    LinearLayout mPlus,mMinus,mButKorz,mButs;
    LinearLayoutCompat rightView;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.image_2);
        this.mTitle = itemView.findViewById(R.id.titlet);
        this.mBrand = itemView.findViewById(R.id.brand);
        this.mPrice = itemView.findViewById(R.id.price);
        this.mColichestvo = itemView.findViewById(R.id.colichestvo);
        this.mPlus = itemView.findViewById(R.id.butplus);
        this.mMinus = itemView.findViewById(R.id.butminus);
        this.mButKorz = itemView.findViewById(R.id.butkorz);
        this.mButs = itemView.findViewById(R.id.buts);
        this.swipeLayout = itemView.findViewById(R.id.swipe);
        this.mFavorite = itemView.findViewById(R.id.heart);
        this.rightView = itemView.findViewById(R.id.right_view);


    }



    public void bind(Foods food,MyAdapter adapter){
        mBrand.setText(food.getName());
        mTitle.setText(food.getDescription());
        Picasso.get().load(food.getImage()).fit().into(mImageView);
        mPrice.setText(mPrice.getContext().getString(R.string.ruble, food.getPrice()));
        String id = food.getId();


        adapter.updateValue(id, MyHolder.this, adapter.getValue(id));
        mButKorz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButs.setVisibility(View.VISIBLE);
                mButKorz.setVisibility(View.INVISIBLE);
                adapter.updateValue(id,MyHolder.this,adapter.getValue(id) + 1);
            }
        });
        mPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.updateValue(id,MyHolder.this,adapter.getValue(id) + 1);
            }
        });
        mMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mColichestvo.getText().toString().equals("0")){
                    mButs.setVisibility(View.INVISIBLE);
                    mButKorz.setVisibility(View.VISIBLE);
                    adapter.updateValue( id,MyHolder.this,adapter.getValue(id) - adapter.getValue(id));
                }else {
                    adapter.updateValue(id,MyHolder.this,adapter.getValue(id)-1);
                }

            }
        });
  }
}