package com.example.gambit;


import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;


public class MyHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    CheckBox mFavorite;
    TextView mTitle,mBrand,mPrice;
    TextView mColichestvo;
    LinearLayout mPlus,mMinus,mButKorz,mButs;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.image_2);
        //this.mFavorite = itemView.findViewById(R.id.favorite);
        this.mTitle = itemView.findViewById(R.id.titlet);
        this.mBrand = itemView.findViewById(R.id.brand);
        this.mPrice = itemView.findViewById(R.id.price);
        this.mColichestvo = itemView.findViewById(R.id.colichestvo);
        this.mPlus = itemView.findViewById(R.id.butplus);
        this.mMinus = itemView.findViewById(R.id.butminus);
        this.mButKorz = itemView.findViewById(R.id.butkorz);
        this.mButs = itemView.findViewById(R.id.buts);


    }

    public void bind(Foods model,MyAdapter adapter){
        mBrand.setText(model.getName());
        mTitle.setText(model.getDescription());
        Picasso.get().load(model.getImage()).fit().into(mImageView);
        mPrice.setText(mPrice.getContext().getString(R.string.ruble, model.getPrice()));
        String id = model.getId();

        adapter.updateValue(MyHolder.this,adapter.getValue(id),id);
        mButKorz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButs.setVisibility(View.VISIBLE);
                mButKorz.setVisibility(View.INVISIBLE);
                adapter.updateValue(MyHolder.this,adapter.getValue(id) + 1,id);
            }
        });

        mPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.updateValue(MyHolder.this,adapter.getValue(id) + 1,id);
            }
        });
        mMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mColichestvo.getText().toString().equals("0")){
                    mButs.setVisibility(View.INVISIBLE);
                    mButKorz.setVisibility(View.VISIBLE);
                    adapter.updateValue(MyHolder.this,adapter.getValue(id) - adapter.getValue(id), id);
                }else {
                    adapter.updateValue(MyHolder.this,adapter.getValue(id)-1,id);
                }

            }
        });


//        mFavorite.setChecked(adapter.getValue(model.getId()));
//        mFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            adapter.updateValue(id, isChecked);
//        });

    }
}